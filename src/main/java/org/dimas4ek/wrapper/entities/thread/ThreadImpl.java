package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.MessageImpl;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ThreadImpl implements Thread {
    private final JSONObject thread;

    public ThreadImpl(JSONObject thread) {
        this.thread = thread;
    }

    @Override
    public String getId() {
        return thread.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return thread.getString("name");
    }

    @Override
    public String getType() {
        for (ChannelType type : ChannelType.values()) {
            if (thread.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    @Override
    public Message getLastMessage() {
        return new MessageImpl(JsonUtils.fetchEntity("/channels/" + getId() + "/messages/" + thread.getString("last_message_id")));
    }

    @Override
    public List<String> getFlags() {
        if (thread.has("flags") || thread.getInt("flags") != 0) {
            List<String> flags = new ArrayList<>();
            long flagsFromJson = Long.parseLong(String.valueOf(thread.getInt("flags")));
            for (GuildMemberFlag flag : GuildMemberFlag.values()) {
                if ((flagsFromJson & flag.getValue()) != 0) {
                    flags.add(flag.name());
                }
            }
            return flags;
        }
        return Collections.emptyList();
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + thread.getString("guild_id")));
    }

    @Override
    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + thread.getString("parent_id")));
    }

    @Override
    public int getRateLimit() {
        return thread.getInt("rate_limit_per_user");
    }

    @Override
    public int getBitrate() {
        return thread.getInt("bitrate");
    }

    @Override
    public int getUserLimit() {
        return thread.getInt("user_limit");
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
    public List<Message> getMessages() {
        JSONArray messages = ApiClient.getJsonArray("/channels/" + getId() + "/messages");
        return JsonUtils.getEntityList(messages, MessageImpl::new);
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
