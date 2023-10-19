package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.entities.GuildMember;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("NzU0Mzk0NTI2OTYwODQ0ODgx.GL2q88.vH3-ghQaKAzadlnffHrR3qxWtuyQFV1EA_k3ww");

        bot.onSlashCommand(event -> {
            if (event.getCommandName().equals("test1")) {
                for (GuildMember member : event.getGuild().getMembers()) {
                    System.out.println(member.getNickname());
                }
            }
        });

        bot.start();
    }
}
