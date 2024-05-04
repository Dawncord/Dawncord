package io.github.dawncord.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import io.github.dawncord.api.action.SlashCommandCreateAction;
import io.github.dawncord.api.action.SlashCommandModifyAction;
import io.github.dawncord.api.command.Command;
import io.github.dawncord.api.command.SlashCommand;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.event.*;
import io.github.dawncord.api.event.handler.*;
import io.github.dawncord.api.listeners.EventListener;
import io.github.dawncord.api.listeners.InteractionListener;
import io.github.dawncord.api.listeners.MainListener;
import io.github.dawncord.api.types.GatewayIntent;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.SlashCommandUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static io.github.dawncord.api.utils.EventProcessor.*;

/**
 * Dawncord is a class representing a connection to the Discord API.
 * <p>
 * It provides functionalities for interacting with the Discord API through a WebSocket.
 */
public class Dawncord {
    private static final Logger logger = LoggerFactory.getLogger(Dawncord.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocket webSocket;
    //private static Map<GatewayEvent, Consumer<GatewayEvent>> eventHandlers = new HashMap<>();
    private final Map<Class<? extends Event>, Consumer<Event>> eventHandlers = new HashMap<>();
    private long intentsValue = 0;
    private final Map<String, String> commandIdMap = new HashMap<>();

    /**
     * creates a new {@link Dawncord} instance
     *
     * @param token the bot token
     * @see #setIntents(GatewayIntent...)
     */
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

    private void assignConstants(String token) {
        Constants.BOT_TOKEN = token;
        Constants.BOT_ID = JsonUtils.fetch(Routes.User("@me")).get("id").asText();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_URL + Routes.Application())
                .addHeader("Authorization", "Bot " + Constants.BOT_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute();
             ResponseBody body = response.body()) {
            if (response.isSuccessful() && body != null) {
                JsonNode node = mapper.readTree(body.string());
                Constants.APPLICATION_ID = node.get("id").asText();
                Constants.CLIENT_KEY = node.get("verify_key").asText();

                logger.debug("Assigning constants");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeCommandIdMap() {
        List<SlashCommand> commands = getSlashCommands();
        for (SlashCommand command : commands) {
            commandIdMap.put(command.getName(), command.getId());
        }

        if (!commandIdMap.isEmpty()) {
            logger.debug("Initializing commands");
        }
    }

    /**
     * Sets the intents for the gateway connection.
     *
     * @param intents the gateway intents to set
     */
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

        logger.debug("Setting intents");
    }

    /**
     * Registers an event handler for a specific type of event.
     * When an event of the specified type is received, the handler function will be invoked.
     *
     * @param type    The class representing the type of event to handle.
     * @param handler The consumer function that handles the event.
     * @param <T>     The type of event to handle.
     */
    public <T extends Event> void on(Class<T> type, Consumer<T> handler) {
        eventHandlers.put(type, event -> handler.accept(type.cast(event)));
    }

    private void processEvent(Event event) {
        Consumer<Event> handler = eventHandlers.get(event.getClass());

        if (handler != null) {
            handler.accept(event);
        }
    }

    /**
     * Sets the handler for a specific slash command event.
     *
     * @param commandName the name of the slash command
     * @param handler     the handler for the slash command event
     */
    public void onSlashCommand(String commandName, Consumer<SlashCommandEvent> handler) {
        slashCommandEventHandlers.put(commandName, handler);
    }

    /**
     * Sets the default handler for slash command events.
     *
     * @param handler the handler for slash command events
     */
    public void onSlashCommand(Consumer<SlashCommandEvent> handler) {
        defaultSlashCommandEventHandler = handler;
    }

    /**
     * Sets the handler for a button event with a specific custom ID.
     *
     * @param customId the custom ID of the button event
     * @param handler  the handler for the button event
     */
    public void onButton(String customId, Consumer<ButtonEvent> handler) {
        buttonEventHandlers.put(customId, handler);
    }

    /**
     * Sets the default handler for button events.
     *
     * @param handler the handler for button events
     */
    public void onButton(Consumer<ButtonEvent> handler) {
        defaultButtonComponentEventHandler = handler;
    }

    /**
     * Sets the handler for a select menu event with a specific custom ID.
     *
     * @param customId the custom ID of the select menu event
     * @param handler  the handler for the select menu event
     */
    public void onSelectMenu(String customId, Consumer<SelectMenuEvent> handler) {
        selectMenuEventHandlers.put(customId, handler);
    }

    /**
     * Sets the default handler for select menu events.
     *
     * @param handler the handler for select menu events
     */
    public void onSelectMenu(Consumer<SelectMenuEvent> handler) {
        defaultSelectMenuEventHandler = handler;
    }

    /**
     * Sets the handler for a modal submit event with a specific custom ID.
     *
     * @param customId the custom ID of the modal submit event
     * @param handler  the handler for the modal submit event
     */
    public void onModal(String customId, Consumer<ModalSubmitEvent> handler) {
        modalSubmitEventHandlers.put(customId, handler);
    }

    /**
     * Sets the default handler for modal submit events.
     *
     * @param handler the handler for modal submit events
     */
    public void onModal(Consumer<ModalSubmitEvent> handler) {
        defaultModalSubmitEventHandler = handler;
    }

    private static void processSlashCommand(SlashCommandEvent slashCommandEvent) {
        String commandName = slashCommandEvent.getFullCommandName();
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

    /**
     * Sets the handler for when the bot is ready.
     *
     * @param handler the handler for the ready event
     */
    public void onReady(Consumer<ReadyEvent> handler) {
        readyEventHandler = handler;
    }

    /**
     * Retrieves the AutoModEventHandler.
     *
     * @return the AutoModEventHandler
     */
    public AutoModEventHandler onAutoMod() {
        return autoModEventHandler;
    }

    /**
     * Retrieves the MessageEventHandler.
     *
     * @return the MessageEventHandler
     */
    public MessageEventHandler onMessage() {
        return messageEventHandler;
    }

    /**
     * Retrieves the GuildEventHandler.
     *
     * @return the GuildEventHandler
     */
    public GuildEventHandler onGuild() {
        return guildEventHandler;
    }

    /**
     * Retrieves the ChannelEventHandler.
     *
     * @return the ChannelEventHandler
     */
    public ChannelEventHandler onChannel() {
        return channelEventHandler;
    }

    /**
     * Retrieves the ThreadEventHandler.
     *
     * @return the ThreadEventHandler
     */
    public ThreadEventHandler onThread() {
        return threadEventHandler;
    }

    /**
     * Retrieves the IntegrationEventHandler.
     *
     * @return the IntegrationEventHandler
     */
    public IntegrationEventHandler onIntegration() {
        return integrationEventHandler;
    }

    /**
     * Retrieves the InviteEventHandler.
     *
     * @return the InviteEventHandler
     */
    public InviteEventHandler onInvite() {
        return inviteEventHandler;
    }

    /**
     * Retrieves the StageEventHandler.
     *
     * @return the StageEventHandler
     */
    public StageEventHandler onStage() {
        return stageEventHandler;
    }

    /**
     * Sets the handler for the onPresenceUpdate event.
     *
     * @param handler the handler for the onPresenceUpdate event
     */
    public void onPresenceUpdate(Consumer<PresenceEvent> handler) {
        presenceEventHandler = handler;
    }

    /**
     * Sets the handler for the onTyping event.
     *
     * @param handler the handler for the onTyping event
     */
    public void onTyping(Consumer<TypingEvent> handler) {
        typingEventHandler = handler;
    }

    /**
     * Sets the handler for the onBotUpdate event.
     *
     * @param handler the handler for the onBotUpdate event
     */
    public void onBotUpdate(Consumer<BotUpdateEvent> handler) {
        botUpdateEventHandler = handler;
    }

    /**
     * Sets the handler for the onVoiceStateUpdate event.
     *
     * @param handler the handler for the onVoiceStateUpdate event
     */
    public void onVoiceStateUpdate(Consumer<VoiceStateUpdateEvent> handler) {
        voiceStateEventHandler = handler;
    }

    /**
     * Sets the handler for the onWebhookUpdate event.
     *
     * @param handler the handler for the onWebhookUpdate event
     */
    public void onWebhookUpdate(Consumer<WebhookUpdateEvent> handler) {
        webhookUpdateEventHandler = handler;
    }

    /**
     * Registers a new slash command.
     *
     * @param name        the name of the slash command
     * @param description the description of the slash command
     * @param handler     the handler for the slash command
     */
    public void registerSlashCommand(String name, String description, Consumer<SlashCommandCreateAction> handler) {
        ActionExecutor.createSlashCommand(handler, name, description);
    }

    /**
     * Registers multiple slash commands.
     *
     * @param slashCommands the slash commands to register
     */
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

    /**
     * Retrieves a specific slash command by name.
     *
     * @param name the name of the slash command to retrieve
     * @return the retrieved slash command
     */
    public SlashCommand getSlashCommand(String name) {
        return new SlashCommand(JsonUtils.fetch(Routes.SlashCommand.Get(getSlashCommandId(name))));
    }

    /**
     * Retrieves a list of slash commands.
     *
     * @return a list of SlashCommand objects representing the retrieved slash commands
     */
    public List<SlashCommand> getSlashCommands() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.SlashCommand.All()), SlashCommand::new);
    }

    /**
     * Modifies a slash command with the provided handler and name.
     *
     * @param name    the name of the slash command to modify
     * @param handler the handler for the slash command modification
     */
    public void modifySlashCommand(String name, Consumer<SlashCommandModifyAction> handler) {
        ActionExecutor.modifySlashCommand(handler, getSlashCommandId(name));
    }

    /**
     * Deletes a slash command with the specified name.
     *
     * @param name the name of the slash command to delete
     */
    public void deleteSlashCommand(String name) {
        ApiClient.delete(Routes.SlashCommand.Get(getSlashCommandId(name)));
    }

    /**
     * Deletes multiple slash commands based on their names.
     *
     * @param names the names of the slash commands to delete
     */
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

    /**
     * Connects to the gateway
     * <p>
     * Starting the gateway will start the event loop for the gateway
     */
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
