package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEvent;

public class GuildScheduledEventEvent implements Event {
    private final Guild guild;
    private final GuildScheduledEvent scheduledEvent;

    public GuildScheduledEventEvent(Guild guild, GuildScheduledEvent scheduledEvent) {
        this.guild = guild;
        this.scheduledEvent = scheduledEvent;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildScheduledEvent getScheduledEvent() {
        return scheduledEvent;
    }
}