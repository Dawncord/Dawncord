package org.dimas4ek.wrapper.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.dimas4ek.wrapper.Dawncord;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.command.SlashCommand;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.guild.GuildMember;
import org.dimas4ek.wrapper.entities.message.component.SelectMenuData;
import org.dimas4ek.wrapper.event.ButtonEvent;
import org.dimas4ek.wrapper.event.Event;
import org.dimas4ek.wrapper.event.SelectMenuEvent;
import org.dimas4ek.wrapper.event.SlashCommandEvent;
import org.dimas4ek.wrapper.interaction.Interaction;
import org.dimas4ek.wrapper.interaction.MessageComponentInteractionData;
import org.dimas4ek.wrapper.interaction.SlashCommandInteractionData;
import org.dimas4ek.wrapper.types.ButtonStyle;
import org.dimas4ek.wrapper.types.ComponentType;
import org.dimas4ek.wrapper.types.InteractionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

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

            Guild guild = new GuildImpl(JsonUtils.fetchEntity(Routes.Guild.Get(guildId)));
            GuildChannel guildChannel = guild.getChannelById(channelId);
            GuildMember guildMember = guild.getMemberById(memberId);

            if (type.equals("INTERACTION_CREATE")) {
                int interactionType = d.get("type").asInt();

                if (interactionType == InteractionType.APPLICATION_COMMAND.getValue()) {
                    processSlashCommands(d, guild, guildChannel, guildMember);
                }

                if (interactionType == InteractionType.MESSAGE_COMPONENT.getValue()) {
                    processMessageComponents(d, guild, guildChannel, guildMember);
                }
            }
        }
    }

    private void processSlashCommands(JsonNode d, Guild guild, GuildChannel guildChannel, GuildMember guildMember) {
        JsonNode data = d.get("data");

        JsonNode slashCommandJson = JsonUtils.fetchEntity(Routes.SlashCommand.Get(data.get("id").asText()));
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

        SlashCommandInteractionData interactionData = new SlashCommandInteractionData(options, slashCommand, interaction, guild, guildChannel, guildMember);

        SlashCommandEvent slashCommandEvent = new SlashCommandEvent(interactionData);

        invokeEvent("processSlashCommand", SlashCommandEvent.class, slashCommandEvent);
    }

    private void processMessageComponents(JsonNode d, Guild guild, GuildChannel guildChannel, GuildMember guildMember) {
        JsonNode data = d.get("data");
        String customId = data.get("custom_id").asText();
        int componentType = data.get("component_type").asInt();

        String interactionId = d.get("id").asText();
        String interactionToken = d.get("token").asText();
        Interaction interaction = new Interaction(interactionId, interactionToken);
        String id = d.get("id").asText();

        MessageComponentInteractionData interactionData = new MessageComponentInteractionData(
                interaction, guild, guildChannel, guildMember, customId, id
        );

        JsonNode components = d.get("message").get("components");
        for (JsonNode component : components) {
            if (component.has("components")) {
                for (JsonNode subComponent : component.get("components")) {
                    if (subComponent.has("custom_id")) {
                        if (customId.equals(subComponent.get("custom_id").asText())) {
                            if (componentType == ComponentType.BUTTON.getValue()) {
                                processButtonEvent(subComponent, interactionData);
                            }
                            if (componentType != ComponentType.ACTION.getValue()
                                    || componentType != ComponentType.BUTTON.getValue()
                                    || componentType != ComponentType.TEXT_INPUT.getValue()) {
                                processSelectMenuEvent(subComponent, interactionData, data, guild);
                            }
                        }
                    }
                }
            }
        }
    }

    private void processSelectMenuEvent(JsonNode subComponent, MessageComponentInteractionData interactionData, JsonNode data, Guild guild) {
        SelectMenuData selectMenuData = new SelectMenuData(
                subComponent.has("placeholder") ? subComponent.get("placeholder").asText() : null,
                subComponent.has("min_values") ? subComponent.get("min_values").asInt() : 1,
                subComponent.has("max_values") ? subComponent.get("max_values").asInt() : 1,
                subComponent.has("options") ? subComponent.get("options") : null,
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

    private void processButtonEvent(JsonNode subComponent, MessageComponentInteractionData interactionData) {
        ButtonStyle style = EnumUtils.getEnumObject(subComponent, "style", ButtonStyle.class);
        ButtonEvent buttonEvent = new ButtonEvent(
                interactionData, style, null, subComponent.get("label").asText()
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
