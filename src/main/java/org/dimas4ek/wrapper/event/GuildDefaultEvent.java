package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.guild.Guild;

public class GuildDefaultEvent implements Event {
    private final Guild guild;

    public GuildDefaultEvent(Guild guild) {
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }
}


