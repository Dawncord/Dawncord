package org.dimas4ek.dawncord;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dimas4ek.dawncord.action.SlashCommandCreateAction;
import org.dimas4ek.dawncord.action.SlashCommandModifyAction;
import org.dimas4ek.dawncord.command.Command;
import org.dimas4ek.dawncord.command.SlashCommand;
import org.dimas4ek.dawncord.command.SubCommand;
import org.dimas4ek.dawncord.command.SubCommandGroup;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.event.*;
import org.dimas4ek.dawncord.event.handler.*;
import org.dimas4ek.dawncord.listeners.EventListener;
import org.dimas4ek.dawncord.listeners.InteractionListener;
import org.dimas4ek.dawncord.listeners.MainListener;
import org.dimas4ek.dawncord.types.GatewayIntent;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.dimas4ek.dawncord.utils.EventHandler.*;

public class Dawncord {
    private static final Logger logger = LoggerFactory.getLogger(Dawncord.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocket webSocket;
    //private static Map<GatewayEvent, Consumer<GatewayEvent>> eventHandlers = new HashMap<>();
    private final Map<Class<? extends Event>, Consumer<Event>> eventHandlers = new HashMap<>();
    private long intentsValue = 0;
    private final Map<String, String> commandIdMap = new HashMap<>();

    public Dawncord(String token) {
        WebSocketFactory factory = new WebSocketFactory();
        try {
            webSocket = factory.createSocket(Constants.GATEWAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        webSocket.addListener(new MainListener());
        webSocket.addListener(new InteractionListener());
        webSocket.addListener(new EventListener());

        assignConstants(token);
        initializeCommandIdMap();
    }

    private void initializeCommandIdMap() {
        List<SlashCommand> commands = getSlashCommands();
        for (SlashCommand command : commands) {
            commandIdMap.put(command.getName(), command.getId());
        }

        if (!commandIdMap.isEmpty()) {
            logger.debug("Initialized commands");
        }
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

        logger.debug("Set intents");
    }

    private void assignConstants(String token) {
        Constants.BOT_TOKEN = token;
        Constants.BOT_ID = JsonUtils.fetch(Routes.User("@me")).get("id").asText();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_URL + Routes.Application())
                .addHeader("Authorization", "Bot " + token)
                .build();

        try (Response response = client.newCall(request).execute();
             ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                JsonNode node = mapper.readTree(body.string());
                Constants.APPLICATION_ID = node.get("id").asText();
                Constants.CLIENT_KEY = node.get("verify_key").asText();

                logger.debug("Assigned constants");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Event> void on(Class<T> type, Consumer<T> handler) {
        eventHandlers.put(type, event -> handler.accept(type.cast(event)));
    }

    private void processEvent(Event event) {
        Consumer<Event> handler = eventHandlers.get(event.getClass());

        if (handler != null) {
            handler.accept(event);
        }
    }

    public void onSlashCommand(String commandName, Consumer<SlashCommandEvent> handler) {
        slashCommandEventHandlers.put(commandName, handler);
    }

    public void onSlashCommand(Consumer<SlashCommandEvent> handler) {
        defaultSlashCommandEventHandler = handler;
    }

    public void onButton(String customId, Consumer<ButtonEvent> handler) {
        buttonEventHandlers.put(customId, handler);
    }

    public void onButton(Consumer<ButtonEvent> handler) {
        defaultButtonComponentEventHandler = handler;
    }

    public void onSelectMenu(String customId, Consumer<SelectMenuEvent> handler) {
        selectMenuEventHandlers.put(customId, handler);
    }

    public void onSelectMenu(Consumer<SelectMenuEvent> handler) {
        defaultSelectMenuEventHandler = handler;
    }

    public void onModal(String customId, Consumer<ModalSubmitEvent> handler) {
        modalSubmitEventHandlers.put(customId, handler);
    }

    public void onModal(Consumer<ModalSubmitEvent> handler) {
        defaultModalSubmitEventHandler = handler;
    }

    private static void processSlashCommand(SlashCommandEvent slashCommandEvent) {
        String commandName = slashCommandEvent.getCommandName();
        Consumer<SlashCommandEvent> handler = slashCommandEventHandlers.get(commandName);
        if (handler != null) {
            handler.accept(slashCommandEvent);
        } else if (defaultSlashCommandEventHandler != null) {
            defaultSlashCommandEventHandler.accept(slashCommandEvent);
        }
    }

    private static void processButtonEvent(ButtonEvent buttonEvent) {
        String customId = buttonEvent.getButton().getCustomId();
        Consumer<ButtonEvent> handler = buttonEventHandlers.get(customId);
        if (handler != null) {
            handler.accept(buttonEvent);
        } else if (defaultButtonComponentEventHandler != null) {
            defaultButtonComponentEventHandler.accept(buttonEvent);
        }
    }

    private static void processSelectMenuEvent(SelectMenuEvent selectMenuEvent) {
        String customId = selectMenuEvent.getSelectMenu().getCustomId();
        Consumer<SelectMenuEvent> handler = selectMenuEventHandlers.get(customId);
        if (handler != null) {
            handler.accept(selectMenuEvent);
        } else if (defaultSelectMenuEventHandler != null) {
            defaultSelectMenuEventHandler.accept(selectMenuEvent);
        }
    }

    private static void processModalSubmitEvent(ModalSubmitEvent modalSubmitEvent) {
        String customId = modalSubmitEvent.getModal().getCustomId();
        Consumer<ModalSubmitEvent> handler = modalSubmitEventHandlers.get(customId);
        if (handler != null) {
            handler.accept(modalSubmitEvent);
        } else if (defaultModalSubmitEventHandler != null) {
            defaultModalSubmitEventHandler.accept(modalSubmitEvent);
        }
    }

    public void onReady(Consumer<ReadyEvent> handler) {
        readyEventHandler = handler;
    }

    public AutoModEventHandler onAutoMod() {
        return autoModEventHandler;
    }

    public MessageEventHandler onMessage() {
        return messageEventHandler;
    }

    public GuildEventHandler onGuild() {
        return guildEventHandler;
    }

    public ChannelEventHandler onChannel() {
        return channelEventHandler;
    }

    public ThreadEventHandler onThread() {
        return threadEventHandler;
    }

    public IntegrationEventHandler onIntegration() {
        return integrationEventHandler;
    }

    public InviteEventHandler onInvite() {
        return inviteEventHandler;
    }

    public StageEventHandler onStage() {
        return stageEventHandler;
    }

    public void onPresenceUpdate(Consumer<PresenceEvent> handler) {
        presenceEventHandler = handler;
    }

    public void onTyping(Consumer<TypingEvent> handler) {
        typingEventHandler = handler;
    }

    public void onBotUpdate(Consumer<BotUpdateEvent> handler) {
        botUpdateEventHandler = handler;
    }

    public void onVoiceStateUpdate(Consumer<VoiceStateUpdateEvent> handler) {
        voiceStateEventHandler = handler;
    }

    public void onWebhookUpdate(Consumer<WebhookUpdateEvent> handler) {
        webhookUpdateEventHandler = handler;
    }

    public void registerSlashCommand(String name, String description, Consumer<SlashCommandCreateAction> handler) {
        ActionExecutor.createSlashCommand(handler, name, description);
    }

    public void registerSlashCommands(SlashCommand... slashCommands) {
        for (SlashCommand slashCommand : slashCommands) {
            ObjectNode node = mapper.createObjectNode();
            node.put("type", 1);
            node.put("name", slashCommand.getName());
            node.put("description", slashCommand.getDescription());

            try {
                setOptions(slashCommand, node);
                setSubCommands(slashCommand, node);
                setSubCommandGroups(slashCommand, node);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            setLocalizations(slashCommand, node);

            ApiClient.post(node, Routes.SlashCommand.All());
        }
    }

    public SlashCommand getSlashCommand(String name) {
        return new SlashCommand(JsonUtils.fetch(Routes.SlashCommand.Get(getSlashCommandId(name))));
    }

    public List<SlashCommand> getSlashCommands() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.SlashCommand.All()), SlashCommand::new);
    }

    public void modifySlashCommand(Consumer<SlashCommandModifyAction> handler, String name) {
        ActionExecutor.modifySlashCommand(handler, getSlashCommandId(name));
    }

    public void deleteSlashCommand(String name) {
        ApiClient.delete(Routes.SlashCommand.Get(getSlashCommandId(name)));
    }

    public void deleteSlashCommands(String... names) {
        for (String name : names) {
            deleteSlashCommand(name);
        }
    }

    private String getSlashCommandId(String name) {
        String commandId = commandIdMap.get(name);
        if (commandId == null) {
            throw new RuntimeException("Command not found");
        }
        return commandId;
    }

    private void setOptions(SlashCommand slashCommand, ObjectNode node) {
        List<Option> options = slashCommand.getOptions();
        if (options != null && !options.isEmpty()) {
            if (node.has("options")) {
                if (!hasSubCommands(node)) {
                    ArrayNode optionsJson = createOptionsArray(options);
                    ((ArrayNode) node.get("options")).addAll(optionsJson);
                }
            } else {
                ArrayNode optionsJson = createOptionsArray(options);
                node.set("options", optionsJson);
            }
        }
    }

    private void setSubCommands(SlashCommand slashCommand, ObjectNode node) {
        List<SubCommand> subCommands = slashCommand.getSubCommands();
        if (subCommands != null && !subCommands.isEmpty()) {
            if (node.has("options")) {
                if (hasNonSubCommands(node)) {
                    ArrayNode subCommandsJson = createSubCommandsArray(subCommands);
                    ((ArrayNode) node.get("options")).addAll(subCommandsJson);
                } else {
                    throw new IllegalArgumentException("Cannot have options and subcommands in the same command");
                }
            } else {
                ArrayNode subCommandsJson = createSubCommandsArray(subCommands);
                node.set("options", subCommandsJson);
            }
        }
    }

    private void setSubCommandGroups(SlashCommand slashCommand, ObjectNode node) {
        List<SubCommandGroup> subCommandGroups = slashCommand.getSubCommandGroups();
        if (subCommandGroups != null && !subCommandGroups.isEmpty()) {
            if (node.has("options")) {
                if (hasNonSubCommands(node)) {
                    ArrayNode subCommandGroupsJson = createSubCommandGroupsArray(subCommandGroups);
                    ((ArrayNode) node.get("options")).addAll(subCommandGroupsJson);

                } else {
                    throw new IllegalArgumentException("Cannot have options and subcommands in the same command");
                }
            } else {
                ArrayNode subCommandGroupsJson = createSubCommandGroupsArray(subCommandGroups);
                node.set("options", subCommandGroupsJson);
            }
        }
    }

    private void setLocalizations(Command command, ObjectNode node) {
        Map<Locale, String> nameLocalizations = command.getNameLocalizations();
        if (nameLocalizations != null && !nameLocalizations.isEmpty()) {
            ObjectNode nameLocalizationsJson = mapper.createObjectNode();
            for (Map.Entry<Locale, String> name : nameLocalizations.entrySet()) {
                nameLocalizationsJson.put(name.getKey().getLocaleCode(), name.getValue());
            }
            node.set("name_localizations", nameLocalizationsJson);
        }
        Map<Locale, String> descriptionLocalizations = command.getDescriptionLocalizations();
        if (descriptionLocalizations != null && !descriptionLocalizations.isEmpty()) {
            ObjectNode descriptionLocalizationsJson = mapper.createObjectNode();
            for (Map.Entry<Locale, String> name : descriptionLocalizations.entrySet()) {
                descriptionLocalizationsJson.put(name.getKey().getLocaleCode(), name.getValue());
            }
            node.set("description_localizations", descriptionLocalizationsJson);
        }
    }

    private boolean hasNonSubCommands(ObjectNode node) {
        boolean hasNonSubCommands = false;
        for (JsonNode nodeOption : node.get("options")) {
            if (nodeOption.get("type").asInt() != OptionType.SUB_COMMAND.getValue() ||
                    nodeOption.get("type").asInt() != OptionType.SUB_COMMAND_GROUP.getValue()) {
                hasNonSubCommands = true;
                break;
            }
        }
        return hasNonSubCommands;
    }

    private boolean hasSubCommands(ObjectNode node) {
        boolean hasNonSubCommands = false;
        for (JsonNode nodeOption : node.get("options")) {
            if (nodeOption.get("type").asInt() == OptionType.SUB_COMMAND.getValue() ||
                    nodeOption.get("type").asInt() == OptionType.SUB_COMMAND_GROUP.getValue()) {
                hasNonSubCommands = true;
                break;
            }
        }
        return hasNonSubCommands;
    }

    private ArrayNode createSubCommandGroupsArray(List<SubCommandGroup> subCommandGroups) {
        ArrayNode subCommandGroupsJson = mapper.createArrayNode();
        for (SubCommandGroup subCommandGroup : subCommandGroups) {
            ObjectNode subCommandGroupJson = mapper.createObjectNode()
                    .put("type", OptionType.SUB_COMMAND_GROUP.getValue())
                    .put("name", subCommandGroup.getName())
                    .put("description", subCommandGroup.getDescription());
            List<SubCommand> subCommands = subCommandGroup.getSubCommands();
            if (subCommands != null && !subCommands.isEmpty()) {
                ArrayNode subCommandsJson = createSubCommandsArray(subCommands);
                subCommandGroupJson.set("options", subCommandsJson);
            }
            setLocalizations(subCommandGroup, subCommandGroupJson);
            subCommandGroupsJson.add(subCommandGroupJson);
        }
        return subCommandGroupsJson;
    }

    private ArrayNode createSubCommandsArray(List<SubCommand> subCommands) {
        ArrayNode subCommandsJson = mapper.createArrayNode();
        for (SubCommand subCommand : subCommands) {
            ObjectNode subCommandJson = mapper.createObjectNode()
                    .put("type", OptionType.SUB_COMMAND.getValue())
                    .put("name", subCommand.getName())
                    .put("description", subCommand.getDescription());
            SlashCommandUtils.createDefaults(subCommandJson, subCommand.getNameLocalizations(), subCommand.getDescriptionLocalizations(), subCommand.getOptions());
            subCommandsJson.add(subCommandJson);
        }
        return subCommandsJson;
    }

    private ArrayNode createOptionsArray(List<Option> options) {
        ArrayNode optionsJson = mapper.createArrayNode();
        boolean foundFalse = false;
        for (Option option : options) {
            if (option.isRequired()) {
                if (foundFalse) {
                    throw new IllegalArgumentException("Required options must be placed before non-required options");
                }
            } else {
                foundFalse = true;
            }
            optionsJson.add(createOption(option));
        }
        return optionsJson;
    }

    private ObjectNode createOption(Option option) {
        ObjectNode optionJson = mapper.createObjectNode();
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
            optionJson.set("choices", createChoices(option));
        }
        return optionJson;
    }

    private ArrayNode createChoices(Option option) {
        ArrayNode choicesJson = mapper.createArrayNode();
        for (Option.Choice choice : option.getChoices()) {
            ObjectNode choiceJson = mapper.createObjectNode();
            choiceJson.put("name", choice.getName());
            choiceJson.put("value", choice.getValue());
            choicesJson.add(choiceJson);
        }
        return choicesJson;
    }

    public void start() {
        try {
            webSocket.connect();
            logger.info("Connected to gateway");
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
