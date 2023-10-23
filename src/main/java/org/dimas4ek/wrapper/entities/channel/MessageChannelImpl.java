package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MessageChannelImpl extends ChannelImpl implements MessageChannel {
    private final JSONObject channel;

    public MessageChannelImpl(JSONObject channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public List<Message> getMessages() {
        JSONArray messages = ApiClient.getJsonArray("/channels/" + getId() + "/messages");
        return JsonUtils.getEntityList(messages, MessageImpl::new);
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
    public int getRateLimit() {
        return channel.getInt("rate_limit_per_user");
    }

    @Override
    public boolean isNsfw() {
        return channel.getBoolean("nsfw");
    }
}
