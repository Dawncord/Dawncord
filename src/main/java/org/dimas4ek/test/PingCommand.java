package org.dimas4ek.test;

import org.dimas4ek.commands.SlashCommand;
import org.dimas4ek.event.SlashCommandInteractionEvent;

public class PingCommand extends SlashCommand {
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        try {
            event.reply("ping").execute();
        } catch (Exception e) {
            System.err.println("An error occurred while trying to reply: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
