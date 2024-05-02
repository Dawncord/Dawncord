package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents a default event associated with a guild.
 */
public class GuildDefaultEvent implements Event {
    private final Guild guild;

    /**
     * Constructs a GuildDefaultEvent with the specified guild.
     *
     * @param guild The guild associated with the event.
     */
    public GuildDefaultEvent(Guild guild) {
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}


