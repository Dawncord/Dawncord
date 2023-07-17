package org.dimas4ek.test;

import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.SlashCommandInteractionEvent;

import java.io.IOException;

public class PingCommand2 extends SlashCommand {
    @Override
    public String name() {
        return "ping";
    }
    
    @Override
    public String description() {
        return "other ping";
    }
    
    @Override
    public void onEvent(SlashCommandInteractionEvent event) throws IOException {
        event.reply("PONG");
    }
}
