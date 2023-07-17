package org.dimas4ek.event;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.dimas4ek.utils.Constants;
import org.json.JSONObject;

import java.io.IOException;

public class SlashCommandInteractionEvent {
    private final String interactionId;
    private final String interactionToken;
    
    public SlashCommandInteractionEvent(String interactionId, String interactionToken) {
        
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }
    
    public void reply(String responseText) {
        JSONObject jsonObject = new JSONObject();
        
        RequestBody requestBody = RequestBody.create(
            jsonObject
                .put("type", 4)
                .put("data", new JSONObject()
                    .put("content", responseText))
                .toString(),
            Constants.MEDIA_TYPE_JSON
        );
        Request request = new Request.Builder()
            .url(Constants.API_URL + "/interactions/" + interactionId + "/" + interactionToken + "/callback")
            .post(requestBody)
            .build();
        
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Ответ выполнен");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

