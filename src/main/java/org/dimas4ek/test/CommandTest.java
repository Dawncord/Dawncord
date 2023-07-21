package org.dimas4ek.test;

import org.dimas4ek.event.Event;
import org.dimas4ek.event.slashcommand.interaction.SlashCommandInteractionEvent;

public class CommandTest extends Event {
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        if (event.getCommandName().equals("test")) {
            event.reply("true").setEphemeral(true).execute();
        }
    }
}
