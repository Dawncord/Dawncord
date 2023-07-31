package org.dimas4ek.interaction.response;

import org.dimas4ek.api.ApiClient;
import org.json.JSONObject;

public class InteractionResponseImpl implements ResponseAction {
    private final JSONObject jsonObject;
    private final String interactionId;
    private final String interactionToken;
    
    public InteractionResponseImpl(JSONObject jsonObject, String interactionId, String interactionToken) {
        this.jsonObject = jsonObject;
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }
    
    @Override
    public void execute() {
        ApiClient.sendInteractionResponse(jsonObject, interactionId, interactionToken);
    }
}
