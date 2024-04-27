package org.dimas4ek.dawncord.entities.channel.thread;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.utils.MessageUtils;

import java.time.ZonedDateTime;

public class ThreadMember {
    private final JsonNode threadMember;
    private final Thread thread;
    private String userId;
    private ZonedDateTime joinedTimestamp;

    public ThreadMember(JsonNode threadMember, Thread thread) {
        this.threadMember = threadMember;
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public GuildMember asGuildMember() {
        if (userId == null) {
            userId = threadMember.get("user_id").asText();
            return getThread().getGuild().getMemberById(userId);
        }
        return null;
    }

    public ZonedDateTime getJoinedTimestamp() {
        if (joinedTimestamp == null) {
            joinedTimestamp = MessageUtils.getZonedDateTime(threadMember, "join_timestamp");
        }
        return joinedTimestamp;
    }
}
