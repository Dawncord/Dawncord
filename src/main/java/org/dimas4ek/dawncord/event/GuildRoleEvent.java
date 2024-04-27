package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;

public class GuildRoleEvent implements Event {
    private final Guild guild;
    private final GuildRole role;

    public GuildRoleEvent(Guild guild, GuildRole role) {
        this.guild = guild;
        this.role = role;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildRole getRole() {
        return role;
    }
}
