package org.dimas4ek;

import org.dimas4ek.wrapper.Dawncord;

public class App {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("***");
        

        bot.onSlashCommand(event -> {
            if (event.getCommandName().equals("test1")) {

            }
        });

        bot.start();
    }
}
