package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRole;

/**
 * Represents an event related to a role in a guild.
 */
public class GuildRoleEvent implements Event {
    private final Guild guild;
    private final GuildRole role;

    /**
     * Constructs a GuildRoleEvent with the specified guild and role.
     *
     * @param guild The guild associated with the event.
     * @param role  The role involved in the event.
     */
    public GuildRoleEvent(Guild guild, GuildRole role) {
        this.guild = guild;
        this.role = role;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the role involved in the event.
     *
     * @return The role involved in the event.
     */
    public GuildRole getRole() {
        return role;
    }
}
