package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.SlashCommand;
import org.dimas4ek.wrapper.entities.Guild;
import org.dimas4ek.wrapper.entities.GuildBan;
import org.dimas4ek.wrapper.entities.GuildBanImpl;
import org.dimas4ek.wrapper.entities.GuildMember;

import java.util.List;

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
