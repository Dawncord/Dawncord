package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event associated with a guild channel.
 */
public class ChannelEvent implements Event {
    private final Guild guild;
    private final GuildChannel channel;

    /**
     * Constructs a ChannelEvent with the specified guild and channel.
     *
     * @param guild   The guild associated with the event.
     * @param channel The channel associated with the event.
     */
    public ChannelEvent(Guild guild, GuildChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the channel associated with the event.
     *
     * @return The channel associated with the event.
     */
    public GuildChannel getChannel() {
        return channel;
    }
}
