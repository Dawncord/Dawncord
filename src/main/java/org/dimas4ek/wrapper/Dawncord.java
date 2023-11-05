package org.dimas4ek.wrapper;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.wrapper.event.MessageEvent;
import org.dimas4ek.wrapper.event.SlashCommandEvent;
import org.dimas4ek.wrapper.listeners.MainListener;
import org.dimas4ek.wrapper.listeners.MessageListener;
import org.dimas4ek.wrapper.listeners.SlashCommandListener;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.slashcommand.option.Option;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Dawncord {
    private final WebSocket webSocket;
    private static Consumer<MessageEvent> defaultMessageHandler;
    private static Map<String, Consumer<SlashCommandEvent>> slashCommandHandlers = new HashMap<>();
    private static Consumer<SlashCommandEvent> defaultSlashCommandHandler;

    public Dawncord(String token) {
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

        JSONObject identify = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", Constants.BOT_TOKEN)
                        .put("intents", 33538)
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

            try {
                setOptions(slashCommand, jsonObject);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            setLocalizations(slashCommand, jsonObject);

            String url = "/applications/" + Constants.APPLICATION_ID + "/commands";

            ApiClient.post(jsonObject, url);
        }
    }

    public List<SlashCommand> getSlashCommands() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/applications/" + Constants.APPLICATION_ID + "/commands"), SlashCommand::new);
    }

    private static void setLocalizations(SlashCommand slashCommand, JSONObject jsonObject) {
        if (!slashCommand.getNameLocalizations().isEmpty()) {
            JSONObject nameLocalizations = new JSONObject();
            for (Map.Entry<Locale, String> name : slashCommand.getNameLocalizations().entrySet()) {
                nameLocalizations.put(name.getKey().getLocaleCode(), name.getValue());
            }
            jsonObject.put("name_localizations", nameLocalizations);
        }
        if (!slashCommand.getDescriptionLocalizations().isEmpty()) {
            JSONObject descriptionLocalizations = new JSONObject();
            for (Map.Entry<Locale, String> name : slashCommand.getDescriptionLocalizations().entrySet()) {
                descriptionLocalizations.put(name.getKey().getLocaleCode(), name.getValue());
            }
            jsonObject.put("description_localizations", descriptionLocalizations);
        }
    }

    private static void setOptions(SlashCommand slashCommand, JSONObject jsonObject) {
        List<Option> options = slashCommand.getOptions();
        if (!options.isEmpty()) {
            JSONArray optionsJson = new JSONArray();
            boolean foundFalse = false;
            for (Option option : options) {
                if (option.isRequired()) {
                    if (foundFalse) {
                        throw new IllegalArgumentException("Required options must be placed before non-required options");
                    }
                } else {
                    foundFalse = true;
                }
                optionsJson.put(setOption(option));
            }
            jsonObject.put("options", optionsJson);
        }
    }

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
        if (!option.getChoices().isEmpty()) {
            setChoices(option, optionJson);
        }
        return optionJson;
    }

    private static void setChoices(Option option, JSONObject optionJson) {
        JSONArray choicesJson = new JSONArray();
        for (Option.Choice choice : option.getChoices()) {
            JSONObject choiceJson = new JSONObject();
            choiceJson.put("name", choice.getName());
            choiceJson.put("value", choice.getValue());
            choicesJson.put(choiceJson);
        }
        optionJson.put("choices", choicesJson);
    }
}
