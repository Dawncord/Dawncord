package org.dimas4ek.event.entities;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.enities.types.GuildChannelType;
import org.json.JSONObject;

public class GuildChannelImpl implements GuildChannel {
    private final JSONObject channel;
    
    public GuildChannelImpl(JSONObject channel) {
        this.channel = channel;
    }
    
    @Override
    public String getId() {
        return channel.getString("id");
    }
    
    @Override
    public String getName() {
        return channel.getString("name");
    }
    
    @Override
    public String getType() {
        for (GuildChannelType type : GuildChannelType.values()) {
            if (channel.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }
    
    @Override
    public void sendMessage(String message) {
        JSONObject messagePayload = new JSONObject();
        messagePayload.put("content", message);
        ApiClient.postApiRequest("/channels/" + getId() + "/messages", messagePayload);
    }
}
