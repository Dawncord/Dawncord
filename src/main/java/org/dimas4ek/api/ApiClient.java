package org.dimas4ek.api;

import okhttp3.*;
import org.dimas4ek.api.exceptions.InteractionAlreadyAcknowledgedException;
import org.dimas4ek.api.exceptions.InvalidOptionOrderException;
import org.dimas4ek.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiClient {
    public static void sendResponse(JSONObject jsonObject, String interactionId, String interactionToken, OkHttpClient client) {
        RequestBody requestBody = RequestBody.create(
            jsonObject.toString(),
            Constants.MEDIA_TYPE_JSON
        );
        
        Request request = new Request.Builder()
            .url(Constants.API_URL + "/interactions/" + interactionId + "/" + interactionToken + "/callback")
            .post(requestBody)
            .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response executed successfully");
            } else {
                System.out.println("API request failed with status code: " + response.code());
                try (ResponseBody body = response.body()) {
                    if (new JSONObject(body.string()).getInt("code") == 40060) {
                        throw new InteractionAlreadyAcknowledgedException("Interaction has already been acknowledged");
                    }
                }
            }
        } catch (InteractionAlreadyAcknowledgedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
        }
    }
    
    public static void postApiRequest(String url, JSONObject jsonPayload) {
        RequestBody body = RequestBody.create(jsonPayload.toString(), Constants.MEDIA_TYPE_JSON);
        
        Request request = new Request.Builder()
            .url(Constants.API_URL + url)
            .post(body)
            .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
            .build();
        
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response executed successfully");
            } else {
                System.out.println("API request failed with status code: " + response.code());
                try (ResponseBody responseBody = response.body()) {
                    if (new JSONObject(responseBody.string()).getInt("code") == 50035) {
                        throw new InvalidOptionOrderException("Required options must be placed before non-required options");
                    }
                }
            }
        } catch (InvalidOptionOrderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
        }
    }
    public static JSONObject getApiResponseObject(String url)  {
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
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new IOException("Unexpected response code: " + response.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JSONArray getApiResponseArray(String url) {
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new IOException("Unexpected response code: " + response.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
