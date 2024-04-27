package org.dimas4ek.dawncord.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.dawncord.Dawncord;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.command.SlashCommand;
import org.dimas4ek.dawncord.entities.CustomEmojiImpl;
import org.dimas4ek.dawncord.entities.DefaultEmoji;
import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildImpl;
import org.dimas4ek.dawncord.entities.message.component.ButtonData;
import org.dimas4ek.dawncord.entities.message.component.SelectMenuData;
import org.dimas4ek.dawncord.event.ButtonEvent;
import org.dimas4ek.dawncord.event.Event;
import org.dimas4ek.dawncord.event.SelectMenuEvent;
import org.dimas4ek.dawncord.event.SlashCommandEvent;
import org.dimas4ek.dawncord.interaction.Interaction;
import org.dimas4ek.dawncord.interaction.MessageComponentInteractionData;
import org.dimas4ek.dawncord.interaction.SlashCommandInteractionData;
import org.dimas4ek.dawncord.types.ButtonStyle;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.ComponentType;
import org.dimas4ek.dawncord.types.InteractionType;
import org.dimas4ek.dawncord.utils.EnumUtils;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionListener extends WebSocketAdapter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JsonNode json = mapper.readTree(text);

        int op = json.get("op").asInt();

        if (op == 0) {
            JsonNode d = json.get("d");
            String type = json.get("t").asText();

            String guildId = d.get("guild_id").asText();
            String channelId = d.get("channel_id").asText();
            String memberId = d.get("member").get("user").get("id").asText();

            if (type.equals("INTERACTION_CREATE")) {
                int interactionType = d.get("type").asInt();

                if (interactionType == InteractionType.APPLICATION_COMMAND.getValue()) {
                    processSlashCommands(d, guildId, channelId, memberId);
                }

                if (interactionType == InteractionType.MESSAGE_COMPONENT.getValue()) {
                    processMessageComponents(d, guildId, channelId, memberId);
                }
            }
        }
    }

    private void processSlashCommands(JsonNode d, String guildId, String guildChannelId, String guildMemberId) {
        JsonNode data = d.get("data");

        JsonNode slashCommandJson = JsonUtils.fetch(Routes.SlashCommand.Get(data.get("id").asText()));
        SlashCommand slashCommand = new SlashCommand(slashCommandJson);

        String interactionId = d.get("id").asText();
        String interactionToken = d.get("token").asText();
        Interaction interaction = new Interaction(interactionId, interactionToken);

        List<Map<String, Object>> options = new ArrayList<>();
        if (data.has("options")) {
            ArrayNode optionsArray = (ArrayNode) data.get("options");

            for (JsonNode optionNode : optionsArray) {
                ObjectNode option = (ObjectNode) optionNode;
                Map<String, Object> map = new HashMap<>();

                map.put("name", option.get("name").asText());
                map.put("value", option.get("value"));

                if (data.has("resolved")) {
                    map.put("resolved", data.get("resolved"));
                }

                options.add(map);
            }
        }

        SlashCommandInteractionData interactionData = new SlashCommandInteractionData(options, slashCommand, interaction, guildId, guildChannelId, guildMemberId);

        SlashCommandEvent slashCommandEvent = new SlashCommandEvent(interactionData);

        invokeEvent("processSlashCommand", SlashCommandEvent.class, slashCommandEvent);
    }

    private void processMessageComponents(JsonNode d, String guildId, String guildChannelId, String guildMemberId) {
        JsonNode data = d.get("data");
        String customId = data.get("custom_id").asText();
        int componentType = data.get("component_type").asInt();

        String interactionId = d.get("id").asText();
        String interactionToken = d.get("token").asText();
        Interaction interaction = new Interaction(interactionId, interactionToken);
        String id = d.get("message").get("id").asText();

        MessageComponentInteractionData interactionData = new MessageComponentInteractionData(
                interaction, customId, id, guildId, guildChannelId, guildMemberId
        );

        JsonNode components = d.get("message").get("components");
        for (JsonNode component : components) {
            if (component.has("components")) {
                for (JsonNode subComponent : component.get("components")) {
                    if (subComponent.has("custom_id")) {
                        if (customId.equals(subComponent.get("custom_id").asText())) {
                            if (componentType == ComponentType.BUTTON.getValue()) {
                                processButtonEvent(subComponent, interactionData, guildId);
                            }
                            if (componentType != ComponentType.ACTION.getValue()
                                    || componentType != ComponentType.BUTTON.getValue()
                                    || componentType != ComponentType.TEXT_INPUT.getValue()) {
                                processSelectMenuEvent(subComponent, interactionData, data, guildId);
                            }
                        }
                    }
                }
            }
        }
    }

    private void processSelectMenuEvent(JsonNode subComponent, MessageComponentInteractionData interactionData, JsonNode data, String guildId) {
        Guild guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)));
        SelectMenuData selectMenuData = new SelectMenuData(
                subComponent.get("custom_id").asText(),
                subComponent.has("placeholder") ? subComponent.get("placeholder").asText() : null,
                subComponent.has("min_values") ? subComponent.get("min_values").asInt() : 1,
                subComponent.has("max_values") ? subComponent.get("max_values").asInt() : 1,
                subComponent.has("options") ? subComponent.get("options") : null,
                subComponent.has("disabled") && subComponent.get("disabled").asBoolean(),
                EnumUtils.getEnumList(subComponent.get("channel_types"), ChannelType.class),
                guild
        );
        JsonNode resolved = data.get("resolved");
        List<String> values = new ArrayList<>();
        data.get("values").forEach(value -> values.add(value.asText()));
        SelectMenuEvent selectMenuEvent = new SelectMenuEvent(
                interactionData, selectMenuData, resolved, values
        );

        invokeEvent("processSelectMenuEvent", SelectMenuEvent.class, selectMenuEvent);
    }

    private void processButtonEvent(JsonNode subComponent, MessageComponentInteractionData interactionData, String guildId) {
        Guild guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(guildId)));
        Emoji emoji = null;
        if (subComponent.has("emoji")) {
            if (subComponent.get("emoji").get("id") != null) {
                emoji = new CustomEmojiImpl(subComponent, guild);
            } else {
                emoji = new DefaultEmoji(subComponent.get("emoji").get("name").asText());
            }
        }
        ButtonData buttonData = new ButtonData(
                subComponent.has("custom_id") ? subComponent.get("custom_id").asText() : null,
                subComponent.has("url") ? subComponent.get("url").asText() : null,
                EnumUtils.getEnumObject(subComponent, "style", ButtonStyle.class),
                subComponent.get("label").asText(),
                subComponent.has("disabled") && subComponent.get("disabled").asBoolean(),
                emoji
        );
        ButtonEvent buttonEvent = new ButtonEvent(
                interactionData, buttonData
        );

        invokeEvent("processButtonEvent", ButtonEvent.class, buttonEvent);
    }

    private void invokeEvent(String name, Class<? extends Event> clazz, Event event) {
        try {
            Method method = Dawncord.class.getDeclaredMethod(name, clazz);
            method.setAccessible(true);
            method.invoke(null, event);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                 IllegalArgumentException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        System.out.println("Closed: " + serverCloseFrame.getCloseReason());
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) {
        System.err.println("Error: " + cause.getMessage());
    }
}
