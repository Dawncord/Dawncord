package io.github.dawncord.api.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;

/**
 * Represents a member of a thread.
 */
public class ThreadMember {
    private final LazyLoader loader;
    private final Thread thread;
    private String userId;
    private ZonedDateTime joinTimestamp;

    /**
     * Constructs a new ThreadMember instance.
     *
     * @param threadMember The JSON node representing the thread member.
     */
    public ThreadMember(JsonNode threadMember, Thread thread) {
        this.thread = thread;
        loader = new LazyLoader(threadMember);
    }

    /**
     * Retrieves the thread to which the member belongs.
     *
     * @return The thread.
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Retrieves the thread member as a guild member if available.
     *
     * @return The guild member, or null if not available.
     */
    public GuildMember asGuildMember() {
        userId = loader.loadString(userId, "user_id");
        return thread.getGuild().getMemberById(userId);
    }

    /**
     * Retrieves the timestamp when the member joined the thread.
     *
     * @return The joined timestamp.
     */
    public ZonedDateTime getJoinTimestamp() {
        joinTimestamp = loader.loadZonedDateTime(joinTimestamp, "join_timestamp");
        return joinTimestamp;
    }
}
