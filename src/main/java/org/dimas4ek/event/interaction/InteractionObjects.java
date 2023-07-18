package org.dimas4ek.event.interaction;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.Guild;
import org.dimas4ek.enities.GuildChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InteractionObjects implements Interaction {
    private final String guildId;
    private final String channelId;
    
    public InteractionObjects(String guildId, String channelId) {
        this.guildId = guildId;
        this.channelId = channelId;
    }
    
    @Override
    public Guild getGuild() {
        try {
            final JSONObject guildData = ApiClient.getApiResponseObject("/guilds/" + guildId);
            return new GuildInteraction(guildData);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public GuildChannel getChannel() {
        try {
            final JSONArray channelData = ApiClient.getApiResponseArray("/guilds/" + guildId + "/channels");
            
            for (int i = 0; i < channelData.length(); i++) {
                JSONObject channel = channelData.getJSONObject(i);
                if (channel.getString("id").equals(channelId)) {
                    return new GuildChannelInteraction(channel);
                }
            }
            
            return null;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
