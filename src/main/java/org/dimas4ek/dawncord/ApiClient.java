package org.dimas4ek.dawncord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.dimas4ek.dawncord.utils.JsonError;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode post(JsonNode jsonObject, String url) {
        return doAction("post", jsonObject, url);
    }

    public static JsonNode patch(JsonNode jsonObject, String url) {
        return doAction("patch", jsonObject, url);
    }

    public static void put(JsonNode jsonObject, String url) {
        doAction("put", jsonObject, url);
    }

    public static void delete(String url) {
        doAction("delete", null, url);
    }

    private static JsonNode doAction(String action, JsonNode jsonNode, String url) {
        Request.Builder builder = new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN);

        RequestBody requestBody = null;
        if (jsonNode != null) {
            try {
                requestBody = RequestBody.create(mapper.writeValueAsString(jsonNode), Constants.MEDIA_TYPE_JSON);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        requestBody = Objects.requireNonNullElseGet(requestBody, () -> RequestBody.create(new byte[0]));
        switch (action) {
            case "post" -> builder.post(requestBody);
            case "patch" -> builder.patch(requestBody);
            case "put" -> builder.put(requestBody);
            case "delete" -> builder.delete();
        }

        return performRequest(builder.build());
    }

    public static JsonNode getJson(String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

        //logger.debug("[GET] -> {}", url);

        return performRequestAndGetJson(request);
    }

    public static JsonNode getJsonParams(String url, Map<String, String> params) {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
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

    public static JsonNode postAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .post(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        logger.debug("[POST] -> {}", url);

        return performRequest(request);
    }

    public static JsonNode patchAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .patch(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        logger.debug("[PATCH] -> {}", url);

        return performRequest(request);
    }

    @Nullable
    public static JsonNode performRequestAndGetJson(Request request) {
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

    public static JsonNode performRequest(Request request) {
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

    public static void logError(Request request, ResponseBody body) throws IOException {
        JsonNode errorJson = mapper.readTree(body.string());
        String code = errorJson.get("code").asText();
        String message = errorJson.get("message").asText();
        String url = request.url().url().toString().substring(Constants.API_URL.length());
        String method = request.method();
        JsonError error = new JsonError(code, message, url, method);
        error.log();
    }

    public static void logInfo(Request request) {
        String url = request.url().url().toString().substring(Constants.API_URL.length());
        String method = request.method();
        logger.debug("[{}] -> {}", method.toUpperCase(), url);
    }
}



