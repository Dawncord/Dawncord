package org.dimas4ek.event.interaction.response;

import okhttp3.OkHttpClient;
import org.dimas4ek.api.ApiClient;
import org.json.JSONObject;

public class InteractionResponseImpl implements InteractionResponse{
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private final JSONObject jsonObject;
    private final String interactionId;
    private final String interactionToken;
    
    public InteractionResponseImpl(JSONObject jsonObject, String interactionId, String interactionToken) {
        this.jsonObject = jsonObject;
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }
    
    @Override
    public void send() {
        ApiClient.sendResponse(jsonObject, interactionId, interactionToken, CLIENT);
    }
}
