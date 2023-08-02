package org.dimas4ek.api;

import okhttp3.*;
import org.dimas4ek.api.exceptions.InteractionAlreadyAcknowledgedException;
import org.dimas4ek.api.exceptions.InvalidOptionOrderException;
import org.dimas4ek.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ApiClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    
    private static Response handleApiRequest(String url, JSONObject payload, String method, Map<String, String> params) {
        RequestBody requestBody = null;
        if (payload != null) {
            requestBody = RequestBody.create(payload.toString(), Constants.MEDIA_TYPE_JSON);
        }
        
        url = Constants.API_URL + url;
        
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        
        // Request builder
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build().toString());
        
        requestBuilder.addHeader("Authorization", "Bot " + Constants.BOT_TOKEN);
        
        if (method.equalsIgnoreCase("POST") && payload != null) {
            requestBuilder.post(requestBody);
        } else if (method.equalsIgnoreCase("GET")) {
            requestBuilder.get();
        }
        
        // Build the request
        Request request = requestBuilder.build();
        
        // Execute the request and return the response
        try {
            return CLIENT.newCall(request).execute();
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
            return null;
        }
    }
    
    /*private static Response handleApiRequest(String url, JSONObject payload, String method, boolean authNeeded) {
        RequestBody requestBody = null;
        if (payload != null) {
            requestBody = RequestBody.create(payload.toString(), Constants.MEDIA_TYPE_JSON);
        }
        
        url = Constants.API_URL + url;
        
        // Request builder
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        // Optional headers and body
        if (authNeeded) {
            requestBuilder.addHeader("Authorization", "Bot " + Constants.BOT_TOKEN);
        }
        
        if (method.equalsIgnoreCase("POST") && payload != null) {
            requestBuilder.post(requestBody);
        } else if (method.equalsIgnoreCase("GET")) {
            requestBuilder.get();
        }
        
        // Build the request
        Request request = requestBuilder.build();
        
        // Execute the request and return the response
        try {
            return CLIENT.newCall(request).execute();
        } catch (IOException e) {
            System.out.println("Encountered IOException: " + e.getMessage());
            return null;
        }
    }*/
    
    private static void processResponse(Response response, int exCode, String exceptionMessage, Class<? extends RuntimeException> exceptionClass) throws
        IOException {
        try (ResponseBody body = response.body()) {
            if (response.isSuccessful()) {
                System.out.println("Response executed successfully");
            } else {
                System.out.println("API request failed with status code: " + response.code());
                if (new JSONObject(body.string()).getInt("code") == exCode) {
                    try {
                        throw exceptionClass.getDeclaredConstructor(String.class).newInstance(exceptionMessage);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    
    public static void sendInteractionResponse(JSONObject jsonObject, String interactionId, String interactionToken) {
        String url = "/interactions/" + interactionId + "/" + interactionToken + "/callback";
        try (Response response = handleApiRequest(url, jsonObject, "POST", null)) {
            if (response != null) {
                try {
                    processResponse(
                        response,
                        40060,
                        "Interaction has already been acknowledged",
                        InteractionAlreadyAcknowledgedException.class
                    );
                } catch (IOException e) {
                    System.out.println("Failed to process response: " + e.getMessage());
                }
            }
        }
    }
    
    public static void postApiRequest(String url, JSONObject jsonPayload) {
        try (Response response = handleApiRequest(url, jsonPayload, "POST", null)) {
            if (response != null) {
                try {
                    processResponse(
                        response,
                        50035,
                        "Required options must be placed before non-required options",
                        InvalidOptionOrderException.class
                    );
                } catch (IOException e) {
                    System.out.println("Failed to process response: " + e.getMessage());
                }
            }
        }
    }
    
    public static JSONObject getApiResponseObject(String url) {
        try (Response response = handleApiRequest(url, null, "GET", null)) {
            
            if (response != null) {
                try (ResponseBody body = response.body()) {
                    if (response.isSuccessful() && body != null) {
                        return new JSONObject(body.string());
                    }
                    throw new RuntimeException("Unexpected response code: " + response.code());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("Response not available");
        }
    }
    
    public static JSONArray getApiResponseArray(String url) {
        try (Response response = handleApiRequest(url, null, "GET", null)) {
            
            if (response != null) {
                try (ResponseBody body = response.body()) {
                    if (response.isSuccessful() && body != null) {
                        return new JSONArray(body.string());
                    }
                    throw new RuntimeException("Unexpected response code: " + response.code());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("Response not available");
        }
    }
    
    public static JSONArray getApiResponseArray(String url, Map<String, String> params) {
        try (Response response = handleApiRequest(url, null, "GET", params)) {
            
            if (response != null) {
                try (ResponseBody body = response.body()) {
                    if (response.isSuccessful() && body != null) {
                        return new JSONArray(body.string());
                    }
                    throw new RuntimeException("Unexpected response code: " + response.code());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new RuntimeException("Response not available");
        }
    }
}
