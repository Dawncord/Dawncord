package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ForumTag;
import io.github.dawncord.api.entities.channel.Channel;
import io.github.dawncord.api.entities.channel.GuildForum;
import io.github.dawncord.api.entities.channel.thread.ThreadMessage;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.utils.MessageUtils;

import java.util.Arrays;

/**
 * Represents an action for creating a thread.
 *
 * @see io.github.dawncord.api.entities.channel.thread.Thread
 */
public class ThreadCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private Channel channel;
    private Message message;
    private String createdId;

    /**
     * Create a new {@link ThreadCreateAction}
     *
     * @param message The message to start the thread from.
     * @param name    The name of the thread.
     */
    public ThreadCreateAction(Message message, String name) {
        this.message = message;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
    }

    /**
     * Create a new {@link ThreadCreateAction}
     *
     * @param channel The channel where the thread will be created.
     */
    public ThreadCreateAction(Channel channel) {
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    /**
     * Create a new {@link ThreadCreateAction}
     *
     * @param forum The guild forum where the thread will be created.
     * @param name  The name of the thread.
     */
    public ThreadCreateAction(GuildForum forum, String name) {
        this.channel = forum;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
    }

    /**
     * Create a new {@link ThreadCreateAction}
     *
     * @param message The message to start the thread from.
     * @param channel The channel where the thread will be created.
     */
    public ThreadCreateAction(Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    private ThreadCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        return this;
    }

    /**
     * Sets the name for the thread.
     *
     * @param name the name to be set
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction setName(String name) {
        return setProperty("name", name);

    }

    /**
     * Sets the duration for the thread.
     *
     * @param minutes the duration in minutes to be set
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction setDuration(int minutes) {
        return setProperty("auto_archive_duration", minutes);

    }

    /**
     * Sets the rate limit per user for the thread.
     *
     * @param rateLimit the rate limit to be set per user
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction setRateLimit(int rateLimit) {
        return setProperty("rate_limit_per_user", rateLimit);

    }

    /**
     * Sets the invitable for the thread.
     *
     * @param invitable the invitable property to be set
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction setInvitable(boolean invitable) {
        if (message == null) {
            setProperty("invitable", invitable);
        }
        return this;
    }

    /**
     * Sends a thread message if the channel type is GUILD_FORUM.
     *
     * @param message the thread message to be sent
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction sendMessage(ThreadMessage message) {
        if (channel.getType() == ChannelType.GUILD_FORUM) {
            setProperty("message", MessageUtils.createThreadMessage(message));
        }
        return this;
    }

    /**
     * Sets the tags for the thread.
     *
     * @param tags the tags to be set
     * @return the modified ThreadCreateAction object
     */
    public ThreadCreateAction setTags(ForumTag... tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM) {
            ArrayNode tagsArray = mapper.createArrayNode();
            tagsArray.add(mapper.valueToTree(Arrays.stream(tags).map(ForumTag::getId).toArray()));
            setProperty("applied_tags", tagsArray);
        }
        return this;
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        JsonNode jsonNode;
        if (message == null) {
            jsonNode = ApiClient.post(jsonObject, Routes.Channel.Thread.All(channel.getId()));
        } else {
            jsonNode = ApiClient.post(jsonObject, Routes.Channel.Message.Threads(message.getChannel().getId(), message.getId()));
        }
        if (jsonNode != null && jsonNode.has("id")) {
            createdId = jsonNode.get("id").asText();
        }
        jsonObject.removeAll();
    }
}
