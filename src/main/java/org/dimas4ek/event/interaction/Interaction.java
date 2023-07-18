package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.guild.Guild;
import org.dimas4ek.enities.guild.GuildChannel;

public interface Interaction {
    Guild getGuild();
    
    GuildChannel getChannel();
}
