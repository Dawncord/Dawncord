package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.slashcommand.Option;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.types.OptionType;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("***");

        SlashCommand slashCommand = new SlashCommand("test123", "test");
        Option option = new Option(OptionType.STRING, "option", "option");
        Option.Choice choice = new Option.Choice("name", "value");
        option.addChoice(choice);
        slashCommand.addOption(option);
        bot.registerSlashCommands(slashCommand);

        bot.onSlashCommand(event -> {

        });

        bot.start();
    }
}
