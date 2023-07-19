package org.dimas4ek.event.interaction;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.GuildChannel;
import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel {
    private final JSONObject channel;
    
    public GuildChannelImpl(JSONObject channel) {
        this.channel = channel;
    }
    
    @Override
    public String getId() {
        return channel.getInt("type") != 4
               ? channel.getString("id")
               : null;
    }
    
    @Override
    public String getName() {
        return channel.getInt("type") != 4
               ? channel.getString("name")
               : null;
    }
    
    @Override
    public String getType() {
        return channel.getInt("type") != 4
               ? (channel.getInt("type") == 0)
                 ? "TEXT"
                 : "VOICE"
               : null;
    }
    
    @Override
    public void sendMessage(String message) {
        JSONObject messagePayload = new JSONObject();
        messagePayload.put("content", message);
        ApiClient.postApiRequest("/channels/" + getId() + "/messages", messagePayload);
    }
}
