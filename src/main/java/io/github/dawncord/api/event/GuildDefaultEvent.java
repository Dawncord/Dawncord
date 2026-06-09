package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents a default event associated with a guild.
 */
public record GuildDefaultEvent(Guild guild) implements Event {
    /**
     * Constructs a GuildDefaultEvent with the specified guild.
     *
     * @param guild The guild associated with the event.
     */
    public GuildDefaultEvent {
    }
}


