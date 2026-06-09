package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event associated with a guild channel.
 */
public record ChannelEvent(Guild getGuild, GuildChannel channel) implements Event {
    /**
     * Constructs a ChannelEvent with the specified guild and channel.
     *
     * @param getGuild The guild associated with the event.
     * @param channel  The channel associated with the event.
     */
    public ChannelEvent {
    }

    /**
     * Retrieves the channel associated with the event.
     *
     * @return The channel associated with the event.
     */
    @Override
    public GuildChannel channel() {
        return channel;
    }
}
