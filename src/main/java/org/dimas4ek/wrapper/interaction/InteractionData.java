package org.dimas4ek.wrapper.interaction;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildMember;

public interface InteractionData {
    Interaction getInteraction();

    Guild getGuild();

    GuildChannel getGuildChannel();

    GuildMember getGuildMember();
}
