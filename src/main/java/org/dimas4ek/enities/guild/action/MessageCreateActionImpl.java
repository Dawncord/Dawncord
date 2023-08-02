package org.dimas4ek.enities.guild.action;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.embed.Embed;
import org.json.JSONObject;

public class MessageCreateActionImpl implements MessageCreateAction {
    private final JSONObject messagePayload;
    private final String id;
    
    public MessageCreateActionImpl(JSONObject messagePayload, String id) {
        this.messagePayload = messagePayload;
        this.id = id;
    }
    
    @Override
    public void execute() {
        ApiClient.postApiRequest("/channels/" + id + "/messages", messagePayload);
    }
    
    @Override
    public MessageCreateAction withEmbeds(Embed embed) {
        return null;
    }
}
