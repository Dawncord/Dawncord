package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to a thread.
 */
public class ThreadEvent implements Event {
    private final Guild guild;
    private final Thread thread;

    /**
     * Constructs a ThreadEvent with the specified guild and thread.
     *
     * @param guild  The guild associated with the thread event.
     * @param thread The thread involved in the event.
     */
    public ThreadEvent(Guild guild, Thread thread) {
        this.guild = guild;
        this.thread = thread;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the thread involved in the event.
     *
     * @return The thread involved in the event.
     */
    public Thread getThread() {
        return thread;
    }
}
