package org.dimas4ek.wrapper.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MessageChannelImpl extends ChannelImpl implements MessageChannel {
    private final JsonNode channel;
    private List<Message> messages;
    private Message lastMessage;
    private Integer rateLimit;
    private Boolean isNsfw;
    private List<Message> pinnedMessages;

    public MessageChannelImpl(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
    }

    @Override
    public List<Message> getMessages() {
        if (messages == null) {
            messages = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Channel.Message.All(getId())), message -> new MessageImpl(message, getGuild()));
        }
        return messages;
    }

    @Override
    public Message getLastMessage() {
        if (lastMessage == null) {
            lastMessage = channel.has("last_message_id")
                    ? new MessageImpl(JsonUtils.fetchEntity(Routes.Channel.Message.Get(getId(), channel.get("last_message_id").asText())), getGuild())
                    : null;
        }
        return lastMessage;
    }

    @Override
    public Message getMessageById(String messageId) {
        return new MessageImpl(JsonUtils.fetchEntity(Routes.Channel.Message.Get(getId(), messageId)), getGuild());
    }

    @Override
    public Message getMessageById(long messageId) {
        return getMessageById(String.valueOf(messageId));
    }

    @Override
    public int getRateLimit() {
        if (rateLimit == null && channel.has("rate_limit_per_user")) {
            rateLimit = channel.get("rate_limit_per_user").asInt();
        }
        return rateLimit != null ? rateLimit : 0;
    }

    @Override
    public boolean isNsfw() {
        if (isNsfw == null && channel.has("nsfw")) {
            isNsfw = channel.get("nsfw").asBoolean();
        }
        return isNsfw != null && isNsfw;
    }

    @Override
    public void deleteMessages(int count) {
        List<String> messagesToDelete = getMessages().stream()
                .limit(count)
                .map(Message::getId)
                .collect(Collectors.toList());

        ApiClient.postArray(new ObjectMapper().valueToTree(messagesToDelete), Routes.Channel.Message.ToDelete(getId()));
    }

    @Override
    public void setTyping() {
        ApiClient.post(null, Routes.Typing(getId()));
    }

    @Override
    public List<Message> getPinnedMessages() {
        if (pinnedMessages == null) {
            pinnedMessages = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Channel.Message.Pin.All(getId())), message -> new MessageImpl(message, getGuild()));
        }
        return pinnedMessages;
    }

    @Override
    public void pinMessage(String messageId) {
        ApiClient.put(null, Routes.Channel.Message.Pin.Get(getId(), messageId));
    }

    @Override
    public void pinMessage(long messageId) {
        pinMessage(String.valueOf(messageId));
    }

    @Override
    public void unpinMessage(String messageId) {
        ApiClient.delete(Routes.Channel.Message.Pin.Get(getId(), messageId));
    }

    @Override
    public void unpinMessage(long messageId) {
        unpinMessage(String.valueOf(messageId));
    }

    @Override
    public void startThread(String messageId, Consumer<ThreadCreateAction> handler) {
        ActionExecutor.startThread(handler, getMessageById(messageId), this);
    }

    @Override
    public void startThread(long messageId, Consumer<ThreadCreateAction> handler) {
        startThread(String.valueOf(messageId), handler);
    }

    @Override
    public void startThread(Consumer<ThreadCreateAction> handler) {
        ActionExecutor.startThread(handler, null, this);
    }

    @Override
    public void startThread() {
        startThread(null);
    }

    @Override
    public void modifyMessageById(String messageId, Consumer<MessageModifyAction> handler) {
        ActionExecutor.modifyMessage(handler, getMessageById(messageId));
    }

    @Override
    public void modifyMessageById(long messageId, Consumer<MessageModifyAction> handler) {
        modifyMessageById(String.valueOf(messageId), handler);
    }
}
