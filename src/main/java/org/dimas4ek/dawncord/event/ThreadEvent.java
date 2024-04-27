package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class ThreadEvent implements Event {
    private final Guild guild;
    private final Thread thread;

    public ThreadEvent(Guild guild, Thread thread) {
        this.guild = guild;
        this.thread = thread;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public Thread getThread() {
        return thread;
    }
}
