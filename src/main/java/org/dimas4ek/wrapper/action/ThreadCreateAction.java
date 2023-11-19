package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.ForumTag;
import org.dimas4ek.wrapper.entities.channel.Channel;
import org.dimas4ek.wrapper.entities.channel.GuildForum;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.thread.ThreadMessage;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ThreadCreateAction {
    private Channel channel;
    private Message message;
    private final JSONObject jsonObject;

    public ThreadCreateAction(Message message, String name) {
        this.message = message;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(Channel channel) {
        this.channel = channel;
        this.jsonObject = new JSONObject();
    }

    public ThreadCreateAction(GuildForum forum, String name) {
        this.channel = forum;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
    }

    public ThreadCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public ThreadCreateAction setDuration(int minutes) {
        setProperty("auto_archive_duration", minutes);
        return this;
    }

    public ThreadCreateAction setRateLimit(int rateLimit) {
        setProperty("rate_limit_per_user", rateLimit);
        return this;
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
            JSONArray tagsArray = new JSONArray();
            for (ForumTag tag : tags) {
                tagsArray.put(tag.getId());
            }
            setProperty("applied_tags", tagsArray);
        }
        return this;
    }

    private void submit() {
        if (message == null) {
            ApiClient.post(jsonObject, "/channels/" + channel.getId() + "/threads");
        } else {
            ApiClient.post(jsonObject, "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/threads");
        }
        jsonObject.clear();
    }
}
