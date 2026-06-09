package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRole;

/**
 * Represents an event related to a role in a guild.
 */
public record GuildRoleEvent(Guild guild, GuildRole role) implements Event {
    /**
     * Constructs a GuildRoleEvent with the specified guild and role.
     *
     * @param guild The guild associated with the event.
     * @param role  The role involved in the event.
     */
    public GuildRoleEvent {
    }

    /**
     * Retrieves the role involved in the event.
     *
     * @return The role involved in the event.
     */
    @Override
    public GuildRole role() {
        return role;
    }
}
