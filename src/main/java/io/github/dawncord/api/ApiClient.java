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

        //logger.debug("[GET] -> {}", url);

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

        //logger.debug("[GET] -> {}", url);

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
                    logError(request, body);
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
                    logError(request, body);
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

    private static void logError(Request request, ResponseBody body) throws IOException {
        JsonNode errorJson = mapper.readTree(body.string());
        String code = errorJson.get("code").asText();
        String message = errorJson.get("message").asText();
        String url = request.url().url().toString().substring(Constants.API_URL.length());
        String method = request.method();
        JsonError error = new JsonError(code, message, url, method);
        error.log();
    }

    private static void logInfo(Request request) {
        String url = request.url().url().toString().substring(Constants.API_URL.length());
        String method = request.method();

        logger.debug("[{}] -> {}", method.toUpperCase(), url);
    }
}



