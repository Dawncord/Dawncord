package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;

/**
 * Represents an event triggered when a user starts typing in a guild channel.
 */
public record TypingEvent(Guild getGuild, GuildChannel channel, GuildMember member) implements Event {
    /**
     * Constructs a TypingEvent with the specified guild, channel, and member.
     *
     * @param getGuild The guild associated with the typing event.
     * @param channel  The channel in which the typing event occurred.
     * @param member   The member who started typing.
     */
    public TypingEvent {
    }

    /**
     * Retrieves the channel in which the typing event occurred.
     *
     * @return The channel in which the typing event occurred.
     */
    @Override
    public GuildChannel channel() {
        return channel;
    }

    /**
     * Retrieves the member who started typing.
     *
     * @return The member who started typing.
     */
    @Override
    public GuildMember member() {
        return member;
    }
}
