package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.channel.Channel;
import org.dimas4ek.wrapper.entities.channel.GuildForum;
import org.dimas4ek.wrapper.entities.channel.thread.ThreadMessage;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.util.Arrays;

public class ThreadCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private Channel channel;
    private Message message;

    public ThreadCreateAction(Message message, String name) {
        this.message = message;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(Channel channel) {
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    public ThreadCreateAction(GuildForum forum, String name) {
        this.channel = forum;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
        this.jsonObject = mapper.createObjectNode();
    }

    private ThreadCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        return this;
    }

    public ThreadCreateAction setName(String name) {
        return setProperty("name", name);

    }

    public ThreadCreateAction setDuration(int minutes) {
        return setProperty("auto_archive_duration", minutes);

    }

    public ThreadCreateAction setRateLimit(int rateLimit) {
        return setProperty("rate_limit_per_user", rateLimit);

    }

    public ThreadCreateAction setInvitable(boolean invitable) {
        if (message == null) {
            setProperty("invitable", invitable);
        }
        return this;
    }

    public ThreadCreateAction sendMessage(ThreadMessage message) {
        if (channel.getType() == ChannelType.GUILD_FORUM) {
            setProperty("message", MessageUtils.createThreadMessage(message));
        }
        return this;
    }

    public ThreadCreateAction setTags(ForumTag... tags) {
        if (channel.getType() == ChannelType.GUILD_FORUM) {
            ArrayNode tagsArray = mapper.createArrayNode();
            tagsArray.add(mapper.valueToTree(Arrays.stream(tags).map(ForumTag::getId).toArray()));
            setProperty("applied_tags", tagsArray);
        }
        return this;
    }

    private void submit() {
        if (message == null) {
            ApiClient.post(jsonObject, Routes.Channel.Thread.All(channel.getId()));
        } else {
            ApiClient.post(jsonObject, Routes.Channel.Message.Threads(message.getChannel().getId(), message.getId()));
        }
        jsonObject.removeAll();
    }
}
