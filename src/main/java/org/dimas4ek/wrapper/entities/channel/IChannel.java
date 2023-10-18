package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.json.JSONObject;

public class IChannel implements Channel {
    private final JSONObject channel;

    public IChannel(JSONObject channel) {
        this.channel = channel;
    }

    @Override
    public boolean isNsfw() {
        return channel.getBoolean("nsfw");
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
    public Message getLastMessage() {
        return new MessageImpl(ApiClient.getJsonObject("/channels/" + getId() + "/messages/" + channel.getString("last_message_id")));
    }

    @Override
    public Message getMessageById(String messageId) {
        return new MessageImpl(ApiClient.getJsonObject("/channels/" + getId() + "/messages/" + messageId));
    }

    @Override
    public Message getMessageById(long messageId) {
        return getMessageById(String.valueOf(messageId));
    }

    @Override
    public GuildCategory getCategory() {
        if (channel.get("parent_id") != null) {
            JSONObject category = ApiClient.getJsonObject("/channels/" + channel.getString("parent_id"));
            return new GuildCategoryImpl(category);
        }
        return null;
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
