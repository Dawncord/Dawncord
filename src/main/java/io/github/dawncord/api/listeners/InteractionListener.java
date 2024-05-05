package io.github.dawncord.api.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import io.github.dawncord.api.Dawncord;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommandData;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.entities.CustomEmojiImpl;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.message.component.ButtonData;
import io.github.dawncord.api.entities.message.component.SelectMenuData;
import io.github.dawncord.api.entities.message.modal.ElementData;
import io.github.dawncord.api.entities.message.modal.ModalData;
import io.github.dawncord.api.event.*;
import io.github.dawncord.api.interaction.Interaction;
import io.github.dawncord.api.interaction.MessageComponentInteractionData;
import io.github.dawncord.api.interaction.ModalInteractionData;
import io.github.dawncord.api.interaction.SlashCommandInteractionData;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listens for interaction events over a WebSocket connection.
 */
public class InteractionListener extends WebSocketAdapter {
    private static final Logger logger = LoggerFactory.getLogger(InteractionListener.class);

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

                if (interactionType == InteractionType.MODAL_SUBMIT.getValue()) {
                    processModals(d, guildId, channelId, memberId);
                }
            }
        }
    }

    private void processModals(JsonNode d, String guildId, String channelId, String memberId) {
        JsonNode data = d.get("data");

        String interactionId = d.get("id").asText();
        String interactionToken = d.get("token").asText();
        Interaction interaction = new Interaction(interactionId, interactionToken);

        ModalInteractionData interactionData = new ModalInteractionData(interaction, guildId, channelId, memberId);

        List<ElementData> elements = new ArrayList<>();
        for (JsonNode elementNode : data.get("components")) {
            ElementData elementData = new ElementData(
                    elementNode.get("components").get(0).get("value").asText(),
                    elementNode.get("components").get(0).get("custom_id").asText());
            elements.add(elementData);
        }
        ModalData modalData = new ModalData(data.get("custom_id").asText(), elements);

        ModalSubmitEvent modalSubmitEvent = new ModalSubmitEvent(interactionData, modalData);

        invokeEvent("processModalSubmitEvent", ModalSubmitEvent.class, modalSubmitEvent);
    }

    private void processSlashCommands(JsonNode d, String guildId, String guildChannelId, String guildMemberId) {
        JsonNode data = d.get("data");

        SubCommand subCommand = null;
        SubCommandGroup subCommandGroup = null;
        if (data.has("options") && data.hasNonNull("options")) {
            for (JsonNode option : data.get("options")) {
                if (option.get("type").asInt() == OptionType.SUB_COMMAND.getValue()) {
                    subCommand = new SubCommand(option);
                }
                if (option.get("type").asInt() == OptionType.SUB_COMMAND_GROUP.getValue()) {
                    subCommandGroup = new SubCommandGroup(option);
                }
            }
        }

        String subCommandGroupName = null;
        if (subCommandGroup != null) {
            subCommandGroupName = subCommandGroup.getName();
            SubCommand subCommandFromGroup = subCommandGroup.getSubCommands().get(0);
            if (subCommandFromGroup != null) {
                subCommandGroupName = subCommandGroupName + " " + subCommandFromGroup.getName();
            }
        }

        SlashCommandData slashCommandData = new SlashCommandData(
                data.get("name").asText(),
                subCommand != null ? subCommand.getName() : null,
                subCommandGroupName
        );

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

        SlashCommandInteractionData interactionData = new SlashCommandInteractionData(options, slashCommandData, interaction, guildId, guildChannelId, guildMemberId);

        SlashCommandEvent slashCommandEvent = new SlashCommandEvent(interactionData);

        invokeEvent("processSlashCommandEvent", SlashCommandEvent.class, slashCommandEvent);
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
                                    && componentType != ComponentType.BUTTON.getValue()
                                    && componentType != ComponentType.TEXT_INPUT.getValue()) {
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
                subComponent.has("channel_types") ? EnumUtils.getEnumList(subComponent.get("channel_types"), ChannelType.class) : null,
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
}
