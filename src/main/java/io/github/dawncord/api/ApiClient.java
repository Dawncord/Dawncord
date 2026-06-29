package io.github.dawncord.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dawncord.api.utils.JsonError;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for making HTTP requests to an API.
 */
public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final int MAX_RETRIES = 5;          // cap on the number of 429 retries per request
    private static final long MAX_WAIT_MS = 60_000;    // cap on the total time spent sleeping per request
    private static final long DEFAULT_RETRY_MS = 1_000; // fallback when no usable rate-limit header is present
    private static final long MIN_RETRY_MS = 100;       // floor so a "Retry-After: 0" can't spin the retry loop

    /**
     * Sends a POST request to the specified URL with the provided JSON object as the request body.
     *
     * @param jsonObject The JSON object to be sent in the request body.
     * @param url        The URL to which the request is sent.
     * @return The JSON response from the server.
     */
    public static JsonNode post(JsonNode jsonObject, String url) {
        return doAction("post", jsonObject, url);
    }

    /**
     * Sends a PATCH request to the specified URL with the provided JSON object as the request body.
     *
     * @param jsonObject The JSON object to be sent in the request body.
     * @param url        The URL to which the request is sent.
     * @return The JSON response from the server.
     */
    public static JsonNode patch(JsonNode jsonObject, String url) {
        return doAction("patch", jsonObject, url);
    }

    /**
     * Sends a PUT request to the specified URL with the provided JSON object as the request body.
     *
     * @param jsonObject The JSON object to be sent in the request body.
     * @param url        The URL to which the request is sent.
     */
    public static void put(JsonNode jsonObject, String url) {
        doAction("put", jsonObject, url);
    }

    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url The URL to which the request is sent.
     */
    public static void delete(String url) {
        doAction("delete", null, url);
    }

    /**
     * Sends a GET request to the specified URL and retrieves the response as a JSON object.
     *
     * @param url The URL to send the GET request to.
     * @return A JSON object containing the response data from the specified URL.
     */
    public static JsonNode getJson(String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

        if (Constants.DEV_LOGGING) {
            logger.debug("[GET] -> {}", url);
        }

        return performRequestAndGetJson(request);
    }

    /**
     * Performs a GET request to the specified URL with the provided query parameters.
     *
     * @param url    The URL to which the request is sent.
     * @param params The query parameters to be included in the request.
     * @return The JSON response from the server.
     */
    public static JsonNode getJsonParams(String url, Map<String, String> params) {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(Constants.API_URL + url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(httpBuilder.build().toString())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

        if (Constants.DEV_LOGGING) {
            logger.debug("[GET] -> {}", url);
        }

        return performRequestAndGetJson(request);
    }

    /**
     * Sends a POST request with multipart form data to the specified URL.
     * <p>
     * The request may be retried on HTTP 429, so the parts must be re-readable (e.g. file- or
     * byte-backed); one-shot {@code InputStream}-backed parts are not safe here.
     *
     * @param multipartBuilder The builder for constructing the multipart request body.
     * @param url              The URL to which the request is sent.
     * @return The JSON response from the server.
     */
    public static JsonNode postAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .post(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        logger.debug("[POST] -> {}", url);

        return performRequest(request);
    }

    /**
     * Sends a PATCH request with multipart form data to the specified URL.
     * <p>
     * The request may be retried on HTTP 429, so the parts must be re-readable (e.g. file- or
     * byte-backed); one-shot {@code InputStream}-backed parts are not safe here.
     *
     * @param multipartBuilder The builder for constructing the multipart request body.
     * @param url              The URL to which the request is sent.
     * @return The JSON response from the server.
     */
    public static JsonNode patchAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .patch(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        logger.debug("[PATCH] -> {}", url);

        return performRequest(request);
    }

    private static JsonNode doAction(String action, JsonNode jsonNode, String url) {
        Request.Builder builder = createRequestBuilder(url);

        RequestBody requestBody = null;
        if (jsonNode != null) requestBody = createRequestBody(jsonNode);

        requestBody = createNullRequestBody(requestBody);
        switch (action) {
            case "post" -> builder.post(requestBody);
            case "patch" -> builder.patch(requestBody);
            case "put" -> builder.put(requestBody);
            case "delete" -> builder.delete();
        }

        return performRequest(builder.build());
    }

    @NotNull
    private static RequestBody createNullRequestBody(RequestBody requestBody) {
        return Objects.requireNonNullElseGet(requestBody, () -> RequestBody.create(new byte[0]));
    }

    @NotNull
    private static RequestBody createRequestBody(JsonNode jsonNode) {
        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(mapper.writeValueAsString(jsonNode), Constants.MEDIA_TYPE_JSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return requestBody;
    }

    @NotNull
    private static Request.Builder createRequestBuilder(String url) {
        return new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN);
    }

    @Nullable
    private static JsonNode performRequestAndGetJson(Request request) {
        try (Response response = executeWithRetry(request); ResponseBody body = response.body()) {
            if (body != null) {
                if (response.isSuccessful()) {
                    return mapper.readTree(body.string());
                } else {
                    logError(request, response.code(), body);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static JsonNode performRequest(Request request) {
        try (Response response = executeWithRetry(request); ResponseBody body = response.body()) {
            if (body != null) {
                if (response.isSuccessful()) {
                    logInfo(request);
                    return mapper.readTree(body.string());
                } else {
                    logError(request, response.code(), body);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Executes the request, reactively retrying on HTTP 429 (Too Many Requests). On a 429 the
     * wait is taken from the {@code Retry-After} / {@code X-RateLimit-Reset-After} header; the
     * retry stops once {@link #MAX_RETRIES} or the {@link #MAX_WAIT_MS} ceiling is reached, in
     * which case the last 429 response is returned for the caller to log. This is the single
     * place that calls {@code CLIENT.newCall(...).execute()}.
     *
     * @param request The request to execute.
     * @return The response (success, a non-429 error, or a 429 that exhausted the retry budget).
     * @throws IOException If the underlying call fails.
     */
    private static Response executeWithRetry(Request request) throws IOException {
        int attempts = 0;
        long totalWaited = 0;
        while (true) {
            Response response = CLIENT.newCall(request).execute();
            if (response.code() != 429) {
                return response;
            }

            long waitMs = Math.max(retryAfterMillis(response), MIN_RETRY_MS);
            if (attempts >= MAX_RETRIES || totalWaited + waitMs > MAX_WAIT_MS) {
                return response;
            }

            boolean global = "true".equalsIgnoreCase(response.header("X-RateLimit-Global"));
            response.close();
            attempts++;
            logger.warn("Rate limited (429{}), retrying in {} ms (attempt {}/{})",
                    global ? ", global" : "", waitMs, attempts, MAX_RETRIES);
            try {
                Thread.sleep(waitMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted during rate-limit backoff", e);
            }
            totalWaited += waitMs;
        }
    }

    /**
     * Reads the retry delay from a 429 response's {@code Retry-After} header, falling back to
     * {@code X-RateLimit-Reset-After}. Both are fractional seconds; the result is in milliseconds,
     * defaulting to {@link #DEFAULT_RETRY_MS} when no header is usable.
     *
     * @param response The 429 response.
     * @return The number of milliseconds to wait before retrying.
     */
    private static long retryAfterMillis(Response response) {
        String value = response.header("Retry-After");
        if (value == null) {
            value = response.header("X-RateLimit-Reset-After");
        }
        if (value != null) {
            try {
                return Math.max(0, (long) (Double.parseDouble(value.trim()) * 1000));
            } catch (NumberFormatException _) {
                // fall through to the default
            }
        }
        return DEFAULT_RETRY_MS;
    }

    private static void logError(Request request, int statusCode, ResponseBody body) {
        try {
            String raw = body.string();
            String url = request.url().url().toString().substring(Constants.API_URL.length());
            String method = request.method();

            JsonNode errorJson;
            try {
                errorJson = mapper.readTree(raw);
            } catch (JsonProcessingException _) {
                // Non-JSON body (e.g. Cloudflare HTML, empty response) — fall back to the raw body.
                String snippet = raw.strip();
                if (snippet.length() > 500) {
                    snippet = snippet.substring(0, 500) + "…";
                }
                new JsonError(statusCode, "", snippet, "", url, method).log();
                return;
            }

            String code = errorJson.path("code").asText("");
            String message = errorJson.path("message").asText("");
            String details = errorJson.has("errors") ? flattenErrors(errorJson.get("errors")) : "";
            new JsonError(statusCode, code, message, details, url, method).log();
        } catch (Exception e) {
            // Deliberately broad: logging an error must never break the calling request path,
            // so any failure here (I/O reading the body, parsing, flattening) is swallowed to warn.
            logger.warn("Failed to log API error response", e);
        }
    }

    /**
     * Flattens Discord's nested {@code errors} object ({@code field._errors[].message}) into a
     * single readable string such as {@code field: message; other.field: message}.
     */
    private static String flattenErrors(JsonNode errors) {
        StringBuilder sb = new StringBuilder();
        collectErrors("", errors, sb);
        return sb.toString();
    }

    private static void collectErrors(String path, JsonNode node, StringBuilder sb) {
        if (node == null || node.isNull()) return;

        JsonNode fieldErrors = node.get("_errors");
        if (fieldErrors != null && fieldErrors.isArray()) {
            for (JsonNode err : fieldErrors) {
                if (sb.length() > 0) sb.append("; ");
                if (!path.isEmpty()) sb.append(path).append(": ");
                sb.append(err.path("message").asText(""));
            }
            return;
        }

        node.properties().forEach(entry -> {
            String childPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
            collectErrors(childPath, entry.getValue(), sb);
        });
    }

    private static void logInfo(Request request) {
        String url = request.url().url().toString().substring(Constants.API_URL.length());
        String method = request.method();

        logger.debug("[{}] -> {}", method.toUpperCase(), url);
    }
}



