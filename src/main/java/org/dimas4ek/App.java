package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.SlashCommand;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("NzU0Mzk0NTI2OTYwODQ0ODgx.GpH57_.381_M3xxh0yLbVxYy3tXW8l1ZZuRN-ZMsLtYpw");

        SlashCommand slashCommand = new SlashCommand("test123", "test");
        bot.registerSlashCommands(slashCommand);

        bot.onMessage(event -> {
            if (event.getMessage().getUser().isBot()) {
                return;
            }
            if (event.getMessage().getContent().equals("123")) {
                event.getChannel().sendMessage("1234");
            }
        });

        bot.onSlashCommand(event -> {
            if (event.getCommandName().equals("test123")) {
                event.reply("123");
            }
        });

        bot.start();
    }
}
