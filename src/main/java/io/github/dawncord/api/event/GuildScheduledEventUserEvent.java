package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;

/**
 * Represents an event related to a user's interaction with a scheduled event in a guild.
 */
public class GuildScheduledEventUserEvent implements Event {
    private final Guild guild;
    private final GuildMember member;
    private final GuildScheduledEvent event;

    /**
     * Constructs a GuildScheduledEventUserEvent with the specified guild, member, and scheduled event.
     *
     * @param guild  The guild associated with the event.
     * @param member The member associated with the event.
     * @param event  The scheduled event involved in the event.
     */
    public GuildScheduledEventUserEvent(Guild guild, GuildMember member, GuildScheduledEvent event) {
        this.guild = guild;
        this.member = member;
        this.event = event;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the member associated with the event.
     *
     * @return The member associated with the event.
     */
    public GuildMember getMember() {
        return member;
    }

    /**
     * Retrieves the scheduled event involved in the event.
     *
     * @return The scheduled event involved in the event.
     */
    public GuildScheduledEvent getEvent() {
        return event;
    }
}
