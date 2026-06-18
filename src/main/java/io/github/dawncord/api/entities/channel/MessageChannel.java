package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.PollCreateAction;
import io.github.dawncord.api.action.ThreadCreateAction;
import io.github.dawncord.api.action.message.MessageModifyAction;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.poll.Poll;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a channel that can contain messages.
 */
public class MessageChannel extends Channel {
    private final LazyLoader loader;
    private final JsonNode channel;
    private List<Message> messages;
    private Message lastMessage;
    private Integer rateLimit;
    private Boolean nsfw;
    private List<Message> pinnedMessages;

    /**
     * Constructs a new MessageChannel object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public MessageChannel(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        loader = new LazyLoader(channel);
    }

    public List<Message> getMessages() {
        messages = loader.load(messages, () -> {
            messages = JsonUtils.getEntityList(
                    JsonUtils.fetch(Routes.Channel.Message.All(getId())),
                    message -> new Message(message, getGuild())
            );
            return messages;
        });
        return messages;
    }

    public Message getLastMessage() {
        lastMessage = loader.loadIfExists(lastMessage, "last_message_id",
                () -> new Message(
                        JsonUtils.fetch(Routes.Channel.Message.Get(getId(), channel.get("last_message_id").asText())),
                        getGuild()
                )
        );
        return lastMessage;
    }

    public Message getMessageById(String messageId) {
        return new Message(JsonUtils.fetch(Routes.Channel.Message.Get(getId(), messageId)), getGuild());
    }

    public Message getMessageById(long messageId) {
        return getMessageById(String.valueOf(messageId));
    }

    public int getRateLimit() {
        rateLimit = loader.loadInt(rateLimit, "rate_limit_per_user");
        return rateLimit != null ? rateLimit : 0;
    }

    public boolean isNsfw() {
        nsfw = loader.loadBoolean(nsfw, "nsfw");
        return nsfw != null && nsfw;
    }

    public void deleteMessages(int count) {
        List<String> messagesToDelete = getMessages().stream()
                .limit(count)
                .map(Message::getId)
                .collect(Collectors.toList());

        ObjectNode body = new ObjectMapper().createObjectNode();
        body.set("messages", new ObjectMapper().valueToTree(messagesToDelete));
        ApiClient.post(body, Routes.Channel.Message.ToDelete(getId()));
    }

    public void setTyping() {
        ApiClient.post(null, Routes.Typing(getId()));
    }

    public List<Message> getPinnedMessages() {
        pinnedMessages = loader.load(pinnedMessages, () -> {
            JsonNode response = JsonUtils.fetch(Routes.Channel.Message.Pin.All(getId()));
            JsonNode items = response != null ? response.get("items") : null;
            return JsonUtils.getEntityList(
                    items,
                    item -> new Message(item.get("message"), getGuild())
            );
        });
        return pinnedMessages;
    }

    public void pinMessage(String messageId) {
        ApiClient.put(null, Routes.Channel.Message.Pin.Get(getId(), messageId));
    }

    public void pinMessage(long messageId) {
        pinMessage(String.valueOf(messageId));
    }

    public void unpinMessage(String messageId) {
        ApiClient.delete(Routes.Channel.Message.Pin.Get(getId(), messageId));
    }

    public void unpinMessage(long messageId) {
        unpinMessage(String.valueOf(messageId));
    }

    public CreateEvent<Thread> startThread(String messageId, Consumer<ThreadCreateAction> handler) {
        String id = ActionExecutor.startThread(handler, getMessageById(messageId), this);
        return new CreateEvent<>(getGuild().getThreadById(id));
    }

    public CreateEvent<Thread> startThread(long messageId, Consumer<ThreadCreateAction> handler) {
        return startThread(String.valueOf(messageId), handler);
    }

    public CreateEvent<Thread> startThread(Consumer<ThreadCreateAction> handler) {
        String id = ActionExecutor.startThread(handler, null, this);
        return new CreateEvent<>(getGuild().getThreadById(id));
    }

    public CreateEvent<Thread> startThread() {
        return startThread(null);
    }

    public ModifyEvent<Message> modifyMessageById(String messageId, Consumer<MessageModifyAction> handler) {
        ActionExecutor.modifyMessage(handler, getMessageById(messageId));
        return new ModifyEvent<>(getMessageById(messageId));
    }

    public ModifyEvent<Message> modifyMessageById(long messageId, Consumer<MessageModifyAction> handler) {
        return modifyMessageById(String.valueOf(messageId), handler);
    }

    public CreateEvent<Poll> createPoll(Consumer<PollCreateAction> handler) {
        String id = ActionExecutor.createPoll(handler, getId());
        return new CreateEvent<>(getMessageById(id).getPoll());
    }

    public Poll getPoll(String messageId) {
        return getMessageById(messageId).getPoll();
    }

    public Poll getPoll(long messageId) {
        return getPoll(String.valueOf(messageId));
    }

    public List<Poll> getPolls() {
        return getMessages().stream().map(Message::getPoll).collect(Collectors.toList());
    }

    public List<Poll> getPolls(String question) {
        return getPolls().stream().filter(poll -> poll != null && poll.getQuestion().equals(question)).collect(Collectors.toList());
    }

    public List<GuildMember> getPollVoters(String messageId, String answerId) {
        return getMessageById(messageId).getPollVoters(answerId);
    }
}
