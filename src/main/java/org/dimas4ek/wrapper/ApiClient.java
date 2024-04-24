package org.dimas4ek.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class ApiClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getJson(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute();
             ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                return mapper.readTree(body.string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void post(JsonNode jsonObject, String url) {
        doAction("post", jsonObject, url);
    }

    public static void postArray(ArrayNode jsonArray, String url) {
        doActionArray("post", jsonArray, url);
    }

    public static void patch(JsonNode jsonObject, String url) {
        doAction("patch", jsonObject, url);
    }

    public static void patchArray(ArrayNode jsonArray, String url) {
        doActionArray("patch", jsonArray, url);
    }

    public static void put(JsonNode jsonObject, String url) {
        doAction("put", jsonObject, url);
    }

    public static void delete(String url) {
        doAction("delete", null, url);
    }

    private static void doAction(String action, JsonNode jsonNode, String url) {
        Request.Builder request = new Request.Builder()
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

        switch (action) {
            case "post":
                if (requestBody != null) {
                    request.post(requestBody);
                } else {
                    request.post(RequestBody.create(new byte[0]));
                }
                break;
            case "patch":
                if (requestBody != null) {
                    request.patch(requestBody);
                }
                break;
            case "put":
                if (requestBody != null) {
                    request.put(requestBody);
                } else {
                    request.put(RequestBody.create(new byte[0]));
                }
                break;
            case "delete":
                request.delete();
                break;
        }

        try (Response response = CLIENT.newCall(request.build()).execute()) {
            if (!response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(mapper.readTree(body.string()).toPrettyString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doActionArray(String action, ArrayNode jsonArray, String url) {
        RequestBody requestBody = null;
        if (jsonArray != null) {
            requestBody = RequestBody.create(jsonArray.toString(), Constants.MEDIA_TYPE_JSON);
        }

        Request.Builder request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN);

        switch (action) {
            case "post" -> {
                if (requestBody != null) {
                    request.post(requestBody);
                } else {
                    request.post(RequestBody.create(new byte[0]));
                }
            }
            case "patch" -> {
                if (requestBody != null) {
                    request.patch(requestBody);
                }
            }
            case "put" -> {
                if (requestBody != null) {
                    request.put(requestBody);
                } else {
                    request.put(RequestBody.create(new byte[0]));
                }
            }
            case "delete" -> request.delete();
        }

        try (Response response = CLIENT.newCall(request.build()).execute()) {
            if (!response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(mapper.readTree(body.string()).toPrettyString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayNode getJsonArrayParams(String url, Map<String, String> params) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
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

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        return (ArrayNode) mapper.readTree(body.string());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //todo multithreading
    public static JsonNode getJsonArray(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        return mapper.readTree(body.string());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static JsonNode getJsonParams(String url, Map<String, String> params) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
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

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        return mapper.readTree(body.string());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void postAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(mapper.readTree(body.string()).toPrettyString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void patchAttachments(MultipartBody.Builder multipartBuilder, String url) {
        Request request = new Request.Builder()
                .url(url)
                .patch(multipartBuilder.build())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(mapper.readTree(body.string()).toPrettyString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

