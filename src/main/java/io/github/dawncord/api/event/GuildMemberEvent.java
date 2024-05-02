package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;

/**
 * Represents an event related to a member in a guild.
 */
public class GuildMemberEvent implements Event {
    private final Guild guild;
    private final GuildMember member;

    /**
     * Constructs a GuildMemberEvent with the specified guild and member.
     *
     * @param guild  The guild associated with the event.
     * @param member The member involved in the event.
     */
    public GuildMemberEvent(Guild guild, GuildMember member) {
        this.guild = guild;
        this.member = member;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the member involved in the event.
     *
     * @return The member involved in the event.
     */
    public GuildMember getMember() {
        return member;
    }
}
