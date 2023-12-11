package org.dimas4ek.wrapper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.wrapper.event.MessageEvent;
import org.dimas4ek.wrapper.event.SlashCommandEvent;
import org.dimas4ek.wrapper.listeners.InteractionListener;
import org.dimas4ek.wrapper.listeners.MainListener;
import org.dimas4ek.wrapper.listeners.MessageListener;
import org.dimas4ek.wrapper.types.GatewayIntent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Dawncord {
    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocket webSocket;
    private static Consumer<MessageEvent> defaultMessageHandler;
    private static Map<String, Consumer<SlashCommandEvent>> slashCommandHandlers = new HashMap<>();
    private static Consumer<SlashCommandEvent> defaultSlashCommandHandler;
    private long intentsValue = 0;

    public Dawncord(String token) {
        WebSocketFactory factory = new WebSocketFactory();
        try {
            webSocket = factory.createSocket(Constants.GATEWAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webSocket.addListener(new MainListener());
        webSocket.addListener(new MessageListener());
        webSocket.addListener(new InteractionListener());
        //todo add GuildListener

        assignConstants(token);
    }

    public void setIntents(GatewayIntent... intents) {
        for (GatewayIntent intent : intents) {
            if (intent == GatewayIntent.ALL) {
                for (GatewayIntent i : GatewayIntent.values()) {
                    intentsValue |= i.getValue();
                }
                break;
            }
            intentsValue |= intent.getValue();
        }
    }

    private void assignConstants(String token) {
        Constants.BOT_TOKEN = token;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_URL + "/applications/@me")
                .addHeader("Authorization", "Bot " + token)
                .build();

        try (Response response = client.newCall(request).execute();
             ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                JsonNode node = mapper.readTree(body.string());
                Constants.APPLICATION_ID = node.get("id").asText();
                Constants.CLIENT_KEY = node.get("verify_key").asText();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMessage(Consumer<MessageEvent> handler) {
        defaultMessageHandler = handler;
    }

    public static void processMessage(MessageEvent messageEvent) {
        if (defaultMessageHandler != null) {
            defaultMessageHandler.accept(messageEvent);
        }
    }

    public void onSlashCommand(String commandName, Consumer<SlashCommandEvent> handler) {
        slashCommandHandlers.put(commandName, handler);
    }

    public void onSlashCommand(Consumer<SlashCommandEvent> handler) {
        defaultSlashCommandHandler = handler;
    }

    public static void processSlashCommand(SlashCommandEvent slashCommandEvent) {
        String commandName = slashCommandEvent.getCommandName();
        Consumer<SlashCommandEvent> handler = slashCommandHandlers.get(commandName);
        if (handler != null) {
            handler.accept(slashCommandEvent);
        } else if (defaultSlashCommandHandler != null) {
            defaultSlashCommandHandler.accept(slashCommandEvent);
        }
    }

    public void start() {
        try {
            webSocket.connect();
        } catch (WebSocketException e) {
            throw new RuntimeException(e);
        }

        ObjectNode identify = mapper.createObjectNode()
                .put("op", 2)
                .set("d", mapper.createObjectNode()
                        .put("token", Constants.BOT_TOKEN)
                        .put("intents", intentsValue)
                        .set("properties", mapper.createObjectNode()
                                .put("os", "linux")
                                .put("browser", "discord-java-gateway")
                                .put("device", "discord-java-gateway")
                        )
                );

        webSocket.sendText(identify.toString());
    }
}
