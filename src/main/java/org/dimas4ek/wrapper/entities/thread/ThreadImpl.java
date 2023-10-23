package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.channel.MessageChannelImpl;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ThreadImpl extends MessageChannelImpl implements Thread {
    private final JSONObject thread;

    public ThreadImpl(JSONObject thread) {
        super(thread);
        this.thread = thread;
    }

    @Override
    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + thread.getString("parent_id")));
    }

    @Override
    public User getCreator() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + thread.getString("owner_id")));
    }

    @Override
    public ThreadMetaData getMetaData() {
        return new ThreadMetaData(thread.getJSONObject("thread_metadata"));
    }

    @Override
    public Message getMessageById(String messageId) {
        return getMessages().stream().filter(message -> message.getId().equals(messageId)).findAny().orElse(null);
    }

    @Override
    public Message getMessageById(long messageId) {
        return getMessageById(String.valueOf(messageId));
    }

    @Override
    public List<ThreadMember> getMembers() {
        JSONArray members = ApiClient.getJsonArrayParams(
                "/channels/" + getId() + "/thread-members",
                Map.of("with_members", "true"));
        return JsonUtils.getEntityList(members, threadMember -> new ThreadMember(threadMember, this));
    }

    @Override
    public ThreadMember getMemberById(String userId) {
        return getMembers().stream().filter(member -> member.asGuildMember().getUser().getId().equals(userId)).findAny().orElse(null);
    }

    @Override
    public ThreadMember getMemberById(long userId) {
        return getMemberById(String.valueOf(userId));
    }
}
