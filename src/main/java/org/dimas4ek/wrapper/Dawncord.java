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
import org.dimas4ek.wrapper.slashcommand.Option;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

            setOptions(slashCommand, jsonObject);

            setLocalizations(slashCommand, jsonObject);

            System.out.println(jsonObject.toString(4));

            String url = "/applications/" + Constants.APPLICATION_ID + "/commands";

            ApiClient.post(jsonObject, url);
        }
    }

    private static void setLocalizations(SlashCommand slashCommand, JSONObject jsonObject) {
        if (!slashCommand.getLocalizedNameList().isEmpty()) {
            JSONObject nameLocalizations = new JSONObject();
            for (Map.Entry<Locale, String> name : slashCommand.getLocalizedNameList().entrySet()) {
                nameLocalizations.put(name.getKey().getLocaleCode(), name.getValue());
            }
            jsonObject.put("name_localizations", nameLocalizations);
        }
        if (!slashCommand.getLocalizedDescriptionList().isEmpty()) {
            JSONObject descriptionLocalizations = new JSONObject();
            for (Map.Entry<Locale, String> name : slashCommand.getLocalizedDescriptionList().entrySet()) {
                descriptionLocalizations.put(name.getKey().getLocaleCode(), name.getValue());
            }
            jsonObject.put("description_localizations", descriptionLocalizations);
        }
    }

    private static void setOptions(SlashCommand slashCommand, JSONObject jsonObject) {
        if (!slashCommand.getOptionList().isEmpty()) {
            JSONArray optionsJson = new JSONArray();
            for (Option option : slashCommand.getOptionList()) {
                optionsJson.put(setOption(option));
            }
            jsonObject.put("options", optionsJson);
        }
    }

    @NotNull
    private static JSONObject setOption(Option option) {
        JSONObject optionJson = new JSONObject();
        optionJson.put("type", option.getType().getValue());
        optionJson.put("name", option.getName());
        optionJson.put("description", option.getDescription());
        if (option.isRequired()) {
            optionJson.put("required", true);
        }
        if (option.isAutocomplete()) {
            optionJson.put("autocomplete", true);
        }
        if (!option.getChoicesList().isEmpty()) {
            setChoices(option, optionJson);
        }
        return optionJson;
    }

    private static void setChoices(Option option, JSONObject optionJson) {
        JSONArray choicesJson = new JSONArray();
        for (Option.Choice choice : option.getChoicesList()) {
            JSONObject choiceJson = new JSONObject();
            choiceJson.put("name", choice.getName());
            choiceJson.put("value", choice.getValue());
            choicesJson.put(choiceJson);
        }
        optionJson.put("choices", choicesJson);
    }
}
