package org.dimas4ek.wrapper.entities.thread;

import org.dimas4ek.wrapper.entities.GuildMember;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(threadMember.getString("join_timestamp"), formatter);
    }
}
