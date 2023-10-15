package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.Message;

public interface MessageEvent {
    Message getMessage();
    GuildChannel getChannel();
    GuildChannel getChannelById(String id);
    GuildChannel getChannelById(long id);
    Guild getGuild();
}
