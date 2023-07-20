package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;

public interface Interaction {
    String getCommandName();
    Guild getGuild();
    GuildChannel getChannel();
}
