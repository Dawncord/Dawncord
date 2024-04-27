package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.entities.guild.event.GuildScheduledEvent;

public class GuildScheduledEventUserEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final GuildScheduledEvent event;

    public GuildScheduledEventUserEvent(Guild guild, GuildMember member, GuildScheduledEvent event) {
        this.guild = guild;
        this.member = member;
        this.event = event;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildMember getMember() {
        return member;
    }

    public GuildScheduledEvent getEvent() {
        return event;
    }
}
