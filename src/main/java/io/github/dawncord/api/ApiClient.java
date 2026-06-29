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
        try (Response response = CLIENT.newCall(request).execute(); ResponseBody body = response.body()) {
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
        try (Response response = CLIENT.newCall(request).execute(); ResponseBody body = response.body()) {
            if (body != null) {
                if (!response.isSuccessful()) {
                    logError(request, response.code(), body);
                }
                if (response.isSuccessful()) {
                    logInfo(request);
                    String responseData = body.string();
                    return mapper.readTree(responseData);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
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



