package io.github.dawncord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.Dawncord;
import io.github.dawncord.api.action.emoji.EmojiAction;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.GatewayIntent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String token = "MTA3Mzk2NzU0NjY2NTAxMzMyOA.GBVNmS.9LLbM1v39Dv7L7wFQn3nDsdZCG8ufPs11PHi7E";
        Dawncord bot = new Dawncord(token);

        List<String> intents = List.of("GUILD_MEMBERS", "GUILD_PRESENCES");

        bot.setIntents(intents.stream().map(GatewayIntent::valueOf).toArray(GatewayIntent[]::new));

        bot.onSlashCommand("test", event -> {
            event.reply("ping");
        });

        bot.start();
    }
}
