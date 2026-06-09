package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;

/**
 * Represents an event related to a member in a guild.
 */
public record GuildMemberEvent(Guild getGuild, GuildMember member) implements Event {
    /**
     * Constructs a GuildMemberEvent with the specified guild and member.
     *
     * @param getGuild The guild associated with the event.
     * @param member   The member involved in the event.
     */
    public GuildMemberEvent {
    }

    /**
     * Retrieves the member involved in the event.
     *
     * @return The member involved in the event.
     */
    @Override
    public GuildMember member() {
        return member;
    }
}
