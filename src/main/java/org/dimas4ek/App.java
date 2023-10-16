package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.SlashCommand;
import org.dimas4ek.wrapper.entities.channel.TextChannel;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.component.Button;
import org.dimas4ek.wrapper.entities.message.component.SelectMenu;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("***");

        SlashCommand slashCommand = new SlashCommand("test123", "test");
        bot.registerSlashCommands(slashCommand);

        bot.onSlashCommand(event -> {
            if (event.getCommandName().equals("test123")) {
                event.getChannel();
            }
        });

        bot.start();
    }
}
