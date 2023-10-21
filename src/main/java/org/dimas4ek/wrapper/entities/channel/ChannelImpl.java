package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class ChannelImpl implements Channel {
    private final JSONObject channel;

    public ChannelImpl(JSONObject channel) {
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
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + channel.getString("guild_id")));
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
    public boolean isNsfw() {
        return channel.getBoolean("nsfw");
    }

    @Override
    public String getAsMention() {
        return "<#" + getId() + ">";
    }
}
