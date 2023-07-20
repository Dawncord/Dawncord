package org.dimas4ek.test;

import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.SlashCommandInteractionEvent;

public class CommandTest extends SlashCommand {
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        if (event.getCommandName().equals("test")) {
        
        }
    }
}
