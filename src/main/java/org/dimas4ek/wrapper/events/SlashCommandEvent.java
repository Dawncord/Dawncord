package org.dimas4ek.wrapper.events;

import org.dimas4ek.wrapper.entities.guild.Guild;

public interface SlashCommandEvent {
    String getCommandName();
    void reply(String message);
    Guild getGuild();
}
