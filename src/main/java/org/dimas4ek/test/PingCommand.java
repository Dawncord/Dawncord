package org.dimas4ek.test;

import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.SlashCommandInteractionEvent;

public class PingCommand extends SlashCommand {
    @Override
    public String name() {
        return "ping1";
    }
    
    @Override
    public String description() {
        return "ping";
    }
    
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        event.reply("pong1");
    }
}
