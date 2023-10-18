package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.slashcommand.SlashCommandBuilder;
import org.dimas4ek.wrapper.types.OptionType;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("***");

        SlashCommand slashCommand = new SlashCommandBuilder("test1", "test")
                .addOption(OptionType.USER, "opt", "opt")
                .build();

        bot.registerSlashCommands(slashCommand);


        bot.onSlashCommand(event -> {
            if (event.getCommandName().equals("test1")) {
                event.reply(event.getOption("opt").getAsUser().getId());
            }
        });

        bot.start();
    }
}
