package io.github.dawncord.api.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.utils.MessageUtils;

import java.time.ZonedDateTime;

/**
 * Represents a member of a thread.
 */
public class ThreadMember {
    private final JsonNode threadMember;
    private final Thread thread;
    private String userId;
    private ZonedDateTime joinedTimestamp;

    /**
     * Constructs a new ThreadMember instance.
     *
     * @param threadMember The JSON node representing the thread member.
     * @param thread       The thread to which the member belongs.
     */
    public ThreadMember(JsonNode threadMember, Thread thread) {
        this.threadMember = threadMember;
        this.thread = thread;
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
        if (userId == null) {
            userId = threadMember.get("user_id").asText();
            return getThread().getGuild().getMemberById(userId);
        }
        return null;
    }

    /**
     * Retrieves the timestamp when the member joined the thread.
     *
     * @return The joined timestamp.
     */
    public ZonedDateTime getJoinedTimestamp() {
        if (joinedTimestamp == null) {
            joinedTimestamp = MessageUtils.getZonedDateTime(threadMember, "join_timestamp");
        }
        return joinedTimestamp;
    }
}
