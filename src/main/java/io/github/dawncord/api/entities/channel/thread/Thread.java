package io.github.dawncord.api.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.MessageChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;
import java.util.Map;

/**
 * Represents an implementation of a thread, a distinct conversation within a guild channel.
 * Threads can be either public or private, and they are a form of message channel.
 */
public class Thread extends MessageChannel {
    private final LazyLoader loader;
    private final JsonNode thread;
    private final Guild guild;
    private GuildChannel channel;
    private User creator;
    private ThreadMetadata metaData;
    private List<ThreadMember> threadMembers;
    private Integer messageCount;
    private Integer totalMessageSent;
    private Integer memberCount;

    /**
     * Constructs a new ThreadImpl instance.
     *
     * @param thread The JSON node representing the thread.
     * @param guild  The guild to which the thread belongs.
     */
    public Thread(JsonNode thread, Guild guild) {
        super(thread, guild);
        this.thread = thread;
        this.guild = guild;
        loader = new LazyLoader(thread);
    }

    public GuildChannel getChannel() {
        channel = loader.load(channel, () -> guild.getChannelById(thread.get("parent_id").asText()));
        return channel;
    }

    public User getCreator() {
        creator = loader.load(creator,
                () -> new User(JsonUtils.fetch(Routes.User(thread.get("owner_id").asText()))));
        return creator;
    }

    public ThreadMetadata getMetaData() {
        metaData = loader.loadIfExists(metaData, "thread_metadata", ThreadMetadata::new);
        return metaData;
    }

    public List<ThreadMember> getThreadMembers() {
        threadMembers = loader.load(threadMembers,
                () -> {
                    threadMembers = JsonUtils.getEntityList(
                            JsonUtils.fetchParams(
                                    Routes.Channel.Thread.Member.All(getId()),
                                    Map.of("with_members", "true")
                            ),
                            threadMember -> new ThreadMember(threadMember, this)
                    );
                    return threadMembers;
                });
        return threadMembers;
    }

    public int getMessageCount() {
        messageCount = loader.loadInt(messageCount, "message_count");
        return messageCount;
    }

    public int getTotalMessageSent() {
        totalMessageSent = loader.loadInt(totalMessageSent, "total_message_sent");
        return totalMessageSent;
    }

    public int getMemberCount() {
        memberCount = loader.loadInt(memberCount, "member_count");
        return memberCount;
    }

    public ThreadMember getThreadMemberById(String userId) {
        return getThreadMembers().stream().filter(member -> member.asGuildMember().getUser().getId().equals(userId)).findAny().orElse(null);
    }

    public ThreadMember getThreadMemberById(long userId) {
        return getThreadMemberById(String.valueOf(userId));
    }

    public void join() {
        ApiClient.put(null, Routes.Channel.Thread.Member.Get(getId(), "@me"));
    }

    public void join(String userId) {
        ApiClient.put(null, Routes.Channel.Thread.Member.Get(getId(), userId));
    }

    public void join(long userId) {
        join(String.valueOf(userId));
    }

    public void leave() {
        ApiClient.delete(Routes.Channel.Thread.Member.Get(getId(), "@me"));
    }

    public void leave(String userId) {
        ApiClient.delete(Routes.Channel.Thread.Member.Get(getId(), userId));
    }

    public void leave(long userId) {
        leave(String.valueOf(userId));
    }
}
