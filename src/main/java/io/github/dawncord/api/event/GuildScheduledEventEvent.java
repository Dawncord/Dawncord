package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;

/**
 * Represents an event related to a scheduled event in a guild.
 */
public record GuildScheduledEventEvent(Guild guild, GuildScheduledEvent scheduledEvent) implements Event {
    /**
     * Constructs a GuildScheduledEventEvent with the specified guild and scheduled event.
     *
     * @param guild          The guild associated with the event.
     * @param scheduledEvent The scheduled event involved in the event.
     */
    public GuildScheduledEventEvent {
    }

    /**
     * Retrieves the scheduled event involved in the event.
     *
     * @return The scheduled event involved in the event.
     */
    @Override
    public GuildScheduledEvent scheduledEvent() {
        return scheduledEvent;
    }
}
