package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;

/**
 * Represents an event related to a scheduled event in a guild.
 */
public class GuildScheduledEventEvent implements Event {
    private final Guild guild;
    private final GuildScheduledEvent scheduledEvent;

    /**
     * Constructs a GuildScheduledEventEvent with the specified guild and scheduled event.
     *
     * @param guild          The guild associated with the event.
     * @param scheduledEvent The scheduled event involved in the event.
     */
    public GuildScheduledEventEvent(Guild guild, GuildScheduledEvent scheduledEvent) {
        this.guild = guild;
        this.scheduledEvent = scheduledEvent;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the scheduled event involved in the event.
     *
     * @return The scheduled event involved in the event.
     */
    public GuildScheduledEvent getScheduledEvent() {
        return scheduledEvent;
    }
}
