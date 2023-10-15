package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.SlashCommand;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("***");

        SlashCommand slashCommand = new SlashCommand("test123", "test");
        bot.registerSlashCommands(slashCommand);

        bot.onSlashCommand(event -> {

        });

        bot.start();
    }
}
