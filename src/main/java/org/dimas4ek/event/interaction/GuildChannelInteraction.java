package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.GuildChannel;
import org.json.JSONObject;

public class GuildChannelInteraction implements GuildChannel {
    private final JSONObject channel;
    
    public GuildChannelInteraction(JSONObject channel) {
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
}
