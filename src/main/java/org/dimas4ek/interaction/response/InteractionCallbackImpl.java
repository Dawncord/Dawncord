package org.dimas4ek.interaction.response;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.component.ActionRow;
import org.dimas4ek.enities.component.MessageComponent;
import org.dimas4ek.enities.embed.Embed;
import org.dimas4ek.utils.MessageUtils;
import org.json.JSONObject;

import java.util.List;

public class InteractionCallbackImpl implements InteractionCallback {
    private final JSONObject jsonObject;
    private final String interactionId;
    private final String interactionToken;
    
    public InteractionCallbackImpl(JSONObject jsonObject, String interactionId, String interactionToken) {
        this.jsonObject = jsonObject;
        this.interactionId = interactionId;
        this.interactionToken = interactionToken;
    }
    
    @Override
    public ResponseAction setEphemeral(boolean ephemeral) {
        if (ephemeral) {
            if (jsonObject.isNull("data")) {
                jsonObject.put("data", new JSONObject());
            }
            jsonObject.getJSONObject("data")
                .put("flags", 1 << 6);
        }
        
        return this;
    }
    
    @Override
    public ResponseAction addEmbeds(Embed embed) {
        MessageUtils.createEmbed(embed, jsonObject);
        return this;
    }
    
    @Override
    public ResponseAction addComponents(MessageComponent component) {
        MessageUtils.createComponent(component, jsonObject);
        return this;
    }
    
    @Override
    public ResponseAction addComponents(ActionRow... actionRows) {
        MessageComponent component = new MessageComponent(List.of(actionRows));
        MessageUtils.createComponent(component, jsonObject);
        return this;
    }
    
    @Override
    public void execute() {
        ApiClient.sendInteractionResponse(jsonObject, interactionId, interactionToken);
    }
}
