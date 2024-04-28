package org.dimas4ek.dawncord.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.UserImpl;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.channel.MessageChannelImpl;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.List;
import java.util.Map;

public class ThreadImpl extends MessageChannelImpl implements Thread {
    private final JsonNode thread;
    private final Guild guild;
    private GuildChannel channel;
    private User creator;
    private ThreadMetadata metaData;
    private List<ThreadMember> threadMembers;

    public ThreadImpl(JsonNode thread, Guild guild) {
        super(thread, guild);
        this.thread = thread;
        this.guild = guild;
    }

    @Override
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(thread.get("parent_id").asText());
        }
        return channel;
    }

    @Override
    public User getCreator() {
        if (creator == null) {
            creator = new UserImpl(JsonUtils.fetch(Routes.User(thread.get("owner_id").asText())));
        }
        return creator;
    }

    @Override
    public ThreadMetadata getMetaData() {
        if (metaData == null) {
            metaData = new ThreadMetadata(thread.get("thread_metadata"));
        }
        return metaData;
    }

    @Override
    public List<ThreadMember> getThreadMembers() {
        if (threadMembers == null) {
            threadMembers = JsonUtils.getEntityList(
                    JsonUtils.fetchParams(
                            Routes.Channel.Thread.Member.All(getId()),
                            Map.of("with_members", "true")
                    ),
                    threadMember -> new ThreadMember(threadMember, this)
            );
        }
        return threadMembers;
    }

    @Override
    public ThreadMember getThreadMemberById(String userId) {
        return getThreadMembers().stream().filter(member -> member.asGuildMember().getUser().getId().equals(userId)).findAny().orElse(null);
    }

    @Override
    public ThreadMember getThreadMemberById(long userId) {
        return getThreadMemberById(String.valueOf(userId));
    }

    @Override
    public void join() {
        ApiClient.put(null, Routes.Channel.Thread.Member.Get(getId(), "@me"));
    }

    @Override
    public void join(String userId) {
        ApiClient.put(null, Routes.Channel.Thread.Member.Get(getId(), userId));
    }

    @Override
    public void join(long userId) {
        join(String.valueOf(userId));
    }

    @Override
    public void leave() {
        ApiClient.delete(Routes.Channel.Thread.Member.Get(getId(), "@me"));
    }

    @Override
    public void leave(String userId) {
        ApiClient.delete(Routes.Channel.Thread.Member.Get(getId(), userId));
    }

    @Override
    public void leave(long userId) {
        leave(String.valueOf(userId));
    }
}
