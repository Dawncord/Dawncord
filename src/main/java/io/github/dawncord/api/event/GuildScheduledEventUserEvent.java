package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;

/**
 * Represents an event related to a user's interaction with a scheduled event in a guild.
 */
public record GuildScheduledEventUserEvent(Guild getGuild, GuildMember member,
                                           GuildScheduledEvent event) implements Event {
    /**
     * Constructs a GuildScheduledEventUserEvent with the specified guild, member, and scheduled event.
     *
     * @param getGuild The guild associated with the event.
     * @param member   The member associated with the event.
     * @param event    The scheduled event involved in the event.
     */
    public GuildScheduledEventUserEvent {
    }

    /**
     * Retrieves the member associated with the event.
     *
     * @return The member associated with the event.
     */
    @Override
    public GuildMember member() {
        return member;
    }

    /**
     * Retrieves the scheduled event involved in the event.
     *
     * @return The scheduled event involved in the event.
     */
    @Override
    public GuildScheduledEvent event() {
        return event;
    }
}
