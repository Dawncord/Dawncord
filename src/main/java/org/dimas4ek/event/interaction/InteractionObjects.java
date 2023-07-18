package org.dimas4ek.event.interaction;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.function.Function;

public class InteractionObjects implements Interaction {
    private final String guildId;
    private final String channelId;
    
    public InteractionObjects(String guildId, String channelId) {
        this.guildId = guildId;
        this.channelId = channelId;
    }
    
    @Override
    public Guild getGuild() {
        return executeAndMap("/guilds/" + guildId, GuildInteraction::new);
    }
    
    @Override
    public GuildChannel getChannel() {
        JSONObject channel = fetchChannel();
        
        if (channel == null) {
            return null;
        }
        
        return new GuildChannelInteraction(channel);
    }
    
    private <T> T executeAndMap(String url, Function<JSONObject, T> mapper) {
        try {
            JSONObject apiResponseObject = ApiClient.getApiResponseObject(url);
            return mapper.apply(apiResponseObject);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
    private JSONObject fetchChannel() {
        String url = "/guilds/" + guildId + "/channels";
        
        try {
            JSONArray channelsData = ApiClient.getApiResponseArray(url);
            
            for (int i = 0; i < channelsData.length(); i++) {
                JSONObject channel = channelsData.getJSONObject(i);
                
                if (channel.getString("id").equals(channelId)) {
                    return channel;
                }
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
}
