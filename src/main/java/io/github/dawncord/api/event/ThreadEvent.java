package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to a thread.
 */
public record ThreadEvent(Guild guild, Thread thread) implements Event {
    /**
     * Constructs a ThreadEvent with the specified guild and thread.
     *
     * @param guild  The guild associated with the thread event.
     * @param thread The thread involved in the event.
     */
    public ThreadEvent {
    }

    /**
     * Retrieves the thread involved in the event.
     *
     * @return The thread involved in the event.
     */
    @Override
    public Thread thread() {
        return thread;
    }
}
