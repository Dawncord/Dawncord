package org.dimas4ek.dawncord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ApiClient {
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
                .url(url)
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
                .url(url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

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

        return performRequestAndGetJson(request);
    }

    public static JsonNode postAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        return performRequest(request);
    }

    public static JsonNode patchAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(url)
                .patch(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        return performRequest(request);
    }

    @Nullable
    public static JsonNode performRequestAndGetJson(Request request) {
        try (Response response = CLIENT.newCall(request).execute(); ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                return mapper.readTree(body.string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static JsonNode performRequest(Request request) {
        try (Response response = CLIENT.newCall(request).execute()) {
            /*if (!response.isSuccessful()) {
                System.out.println("Response code: " + response.code());
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(mapper.readTree(body.string()).toPrettyString());
                    }
                }
            }*/
            if (response.isSuccessful()) {
                try (ResponseBody body = response.peekBody(99999L)) {
                    String responseData = body.string();
                    return mapper.readTree(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}



