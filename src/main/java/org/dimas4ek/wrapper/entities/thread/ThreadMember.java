package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

import java.time.ZonedDateTime;

public class ThreadMember {
    private final JSONObject threadMember;
    private final Thread thread;

    public ThreadMember(JSONObject threadMember, Thread thread) {
        this.threadMember = threadMember;
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public GuildMember asGuildMember() {
        return getThread().getGuild().getMemberById(threadMember.getString("user_id"));
    }

    public ZonedDateTime getJoinedTimestamp() {
        return MessageUtils.getZonedDateTime(threadMember, "join_timestamp");
    }
}
