package org.dimas4ek.test;

import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.SlashCommandInteractionEvent;

public class CommandTest extends SlashCommand {
    @Override
    public String name() {
        return "test";
    }
    @Override
    public String description() {
        return "test command";
    }
    
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
    }
}
