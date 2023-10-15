package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.types.ChannelType;
import org.json.JSONObject;

public class IChannel implements Channel {
    private final JSONObject channel;

    public IChannel(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public String getId() {
        return channel.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return channel.getString("name");
    }

    @Override
    public String getType() {
        for (ChannelType type : ChannelType.values()) {
            if (channel.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    @Override
    public boolean isNsfw() {
        return channel.getBoolean("nsfw");
    }

    @Override
    public Message getLastMessage() {
        return null;
    }

    @Override
    public Message getMessageById(String id) {
        return null;
    }

    @Override
    public Message getMessageById(long id) {
        return null;
    }

    @Override
    public GuildCategory getCategory() {
        JSONObject category = ApiClient.getJsonObject("/channels/" + channel.getString("parent_id"));
        return new GuildCategoryImpl(category);
    }

    @Override
    public String getAsMention() {
        return null;
    }
}
