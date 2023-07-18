package org.dimas4ek.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiClient {
    public static JSONObject getApiResponseObject(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
            .url(Constants.API_URL + url)
            .get()
            .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
            .build();
        
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.code() == 200) {
                    try (ResponseBody body = response.body()) {
                        if (body != null) {
                            return new JSONObject(body.string());
                        }
                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new IOException("Unexpected response code: " + response.code());
        }
    }
    
    public static JSONArray getApiResponseArray(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
            .url(Constants.API_URL + url)
            .get()
            .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
            .build();
        
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        return new JSONArray(body.string());
                    }
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new IOException("Unexpected response code: " + response.code());
        }
    }
}
