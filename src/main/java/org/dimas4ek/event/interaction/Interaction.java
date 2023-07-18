package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.Guild;
import org.dimas4ek.enities.GuildChannel;

public interface Interaction {
    Guild getGuild();
    
    GuildChannel getChannel();
}
