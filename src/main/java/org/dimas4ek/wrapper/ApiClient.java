package org.dimas4ek.wrapper;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class ApiClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void sendResponse(String channelId, JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.API_URL + "/channels/" + channelId + "/messages")
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .post(requestBody)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("true");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void post(JSONObject jsonObject, String url) {
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .post(requestBody)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        System.out.println(new JSONObject(body).toString(4));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
