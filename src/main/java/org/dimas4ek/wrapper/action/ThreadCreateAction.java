package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.ForumTag;
import org.dimas4ek.wrapper.entities.thread.ThreadMessage;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ThreadCreateAction {
    private JSONObject channel;
    private String messageId;
    private final JSONObject jsonObject;

    public ThreadCreateAction(String messageId, String name) {
        this.messageId = messageId;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(JSONObject channel) {
        this.channel = channel;
        this.jsonObject = new JSONObject();
    }

    public ThreadCreateAction(JSONObject forum, String name) {
        this.channel = forum;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("name", name);
    }

    public ThreadCreateAction(String messageId, JSONObject channel) {
        this.messageId = messageId;
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
        if (messageId == null) {
            setProperty("invitable", invitable);
        }
        return this;
    }

    public ThreadCreateAction sendMessage(ThreadMessage message) {
        if (channel.getInt("type") == ChannelType.GUILD_FORUM.getValue()) {
            setProperty("message", MessageUtils.createThreadMessage(message));
        }
        return this;
    }

    public ThreadCreateAction setTags(ForumTag... tags) {
        if (channel.getInt("type") == ChannelType.GUILD_FORUM.getValue()) {
            JSONArray tagsArray = new JSONArray();
            for (ForumTag tag : tags) {
                tagsArray.put(tag.getId());
            }
            setProperty("applied_tags", tagsArray);
        }
        return this;
    }

    public void submit() {
        if (messageId == null) {
            ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/threads");
        } else {
            ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/messages/" + messageId + "/threads");
        }
        jsonObject.clear();
    }
}
