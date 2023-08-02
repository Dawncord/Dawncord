package org.dimas4ek.event.entities;

import org.dimas4ek.enities.guild.GuildChannelMessage;
import org.dimas4ek.enities.user.User;
import org.json.JSONObject;

public class GuildChannelMessageImpl implements GuildChannelMessage {
    private final JSONObject jsonObject;
    
    public GuildChannelMessageImpl(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    
    @Override
    public String getId() {
        return jsonObject.getString("id");
    }
    
    @Override
    public String getContent() {
        return jsonObject.getString("content");
    }
    
    @Override
    public User getAuthor() {
        return new UserImpl(jsonObject.getJSONObject("author"));
    }
}
