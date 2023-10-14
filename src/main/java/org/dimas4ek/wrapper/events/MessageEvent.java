package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.entities.Guild;
import org.dimas4ek.wrapper.entities.GuildChannel;
import org.dimas4ek.wrapper.entities.Message;

public interface MessageEvent {
    Message getMessage();
    GuildChannel getChannel();
    GuildChannel getChannelById(String id);
    GuildChannel getChannelById(long id);
    Guild getGuild();
}
