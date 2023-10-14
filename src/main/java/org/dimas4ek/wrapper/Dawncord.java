package org.dimas4ek.wrapper;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.wrapper.events.MessageEvent;
import org.dimas4ek.wrapper.events.SlashCommandEvent;
import org.dimas4ek.wrapper.listeners.MainListener;
import org.dimas4ek.wrapper.listeners.MessageListener;
import org.dimas4ek.wrapper.listeners.SlashCommandListener;
import org.json.JSONObject;

import java.io.IOException;
import java.util.function.Consumer;

public class Dawncord {
    private final WebSocket webSocket;
    private final String token;
    private static Consumer<MessageEvent> onMessageHandler;
    private static Consumer<SlashCommandEvent> onSlashCommandHandler;

    public Dawncord(String token) {
        this.token = token;

        WebSocketFactory factory = new WebSocketFactory();
        try {
            webSocket = factory.createSocket(Constants.GATEWAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webSocket.addListener(new MainListener());
        webSocket.addListener(new MessageListener());
        webSocket.addListener(new SlashCommandListener());

        assignConstants(token);
    }

    private void assignConstants(String token) {
        Constants.BOT_TOKEN = token;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_URL + "/applications/@me")
                .addHeader("Authorization", "Bot " + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        JSONObject jsonObject = new JSONObject(body.string());
                        Constants.APPLICATION_ID = jsonObject.getString("id");
                        Constants.CLIENT_KEY = jsonObject.getString("verify_key");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMessage(Consumer<MessageEvent> handler) {
        onMessageHandler = handler;
    }

    public void onSlashCommand(Consumer<SlashCommandEvent> handler) {
        onSlashCommandHandler = handler;
    }

    public static void processMessage(MessageEvent messageEvent) {
        if (onMessageHandler != null) {
            onMessageHandler.accept(messageEvent);
        }
    }

    public static void processSlashCommand(SlashCommandEvent slashCommandEvent) {
        if (onSlashCommandHandler != null) {
            onSlashCommandHandler.accept(slashCommandEvent);
        }
    }

    public void start() {
        try {
            webSocket.connect();
        } catch (WebSocketException e) {
            throw new RuntimeException(e);
        }

        JSONObject identify = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", Constants.BOT_TOKEN)
                        .put("intents", 33280)
                        .put("properties", new JSONObject()
                                .put("os", "linux")
                                .put("browser", "discord-java-gateway")
                                .put("device", "discord-java-gateway"))
                );

        webSocket.sendText(identify.toString());
    }

    public void registerSlashCommands(SlashCommand... slashCommands) {
        for (SlashCommand slashCommand : slashCommands) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 1);
            jsonObject.put("name", slashCommand.getName());
            jsonObject.put("description", slashCommand.getDescription());

            String url = "/applications/" + Constants.APPLICATION_ID + "/commands";

            ApiClient.post(jsonObject, url);
        }
    }
}
