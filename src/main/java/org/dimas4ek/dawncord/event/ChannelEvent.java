package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class ChannelEvent implements Event {
    private final Guild guild;
    private final GuildChannel channel;

    public ChannelEvent(Guild guild, GuildChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public GuildChannel getChannel() {
        return channel;
    }
}
