package org.dimas4ek.event.entities;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.GuildChannel;
import org.dimas4ek.enities.guild.GuildChannelMessage;
import org.dimas4ek.enities.types.GuildChannelType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    @Override
    public GuildChannelMessage getMessageById(String id) {
        JSONObject jsonObject = ApiClient.getApiResponseObject("/channels/" + getId() + "/messages/" + id);
        return new GuildChannelMessageImpl(jsonObject);
    }
    
    @Override
    public GuildChannelMessage getLastMessage() {
        return getMessages().get(0);
    }
    
    @Override
    public List<GuildChannelMessage> getMessages() {
        List<GuildChannelMessage> messages = new ArrayList<>();
        JSONArray jsonArray = ApiClient.getApiResponseArray("/channels/" + getId() + "/messages");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            GuildChannelMessage guildChannelMessage = new GuildChannelMessageImpl(jsonObject);
            messages.add(guildChannelMessage);
        }
        return messages;
    }
    
    @Override
    public List<GuildChannelMessage> getMessagesBefore(String beforeId) {
        List<GuildChannelMessage> messages = new ArrayList<>();
        JSONArray jsonArray = ApiClient.getApiResponseArray(
            "/channels/" + getId() + "/messages",
            Map.of("before", beforeId)
        );
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            GuildChannelMessage guildChannelMessage = new GuildChannelMessageImpl(jsonObject);
            messages.add(guildChannelMessage);
        }
        return messages;
    }
    
    @Override
    public List<GuildChannelMessage> getMessagesAfter(String afterId) {
        List<GuildChannelMessage> messages = new ArrayList<>();
        JSONArray jsonArray = ApiClient.getApiResponseArray(
            "/channels/" + getId() + "/messages",
            Map.of("after", afterId)
        );
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            GuildChannelMessage guildChannelMessage = new GuildChannelMessageImpl(jsonObject);
            messages.add(guildChannelMessage);
        }
        return messages;
    }
}
