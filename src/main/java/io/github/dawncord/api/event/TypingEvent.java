package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;

/**
 * Represents an event triggered when a user starts typing in a guild channel.
 */
public class TypingEvent implements Event {
    private final Guild guild;
    private final GuildChannel channel;
    private final GuildMember member;

    /**
     * Constructs a TypingEvent with the specified guild, channel, and member.
     *
     * @param guild   The guild associated with the typing event.
     * @param channel The channel in which the typing event occurred.
     * @param member  The member who started typing.
     */
    public TypingEvent(Guild guild, GuildChannel channel, GuildMember member) {
        this.guild = guild;
        this.channel = channel;
        this.member = member;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the channel in which the typing event occurred.
     *
     * @return The channel in which the typing event occurred.
     */
    public GuildChannel getChannel() {
        return channel;
    }

    /**
     * Retrieves the member who started typing.
     *
     * @return The member who started typing.
     */
    public GuildMember getMember() {
        return member;
    }
}
