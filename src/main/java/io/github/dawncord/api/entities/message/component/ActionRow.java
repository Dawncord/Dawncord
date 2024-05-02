package io.github.dawncord.api.entities.message.component;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.CustomEmojiImpl;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.ButtonStyle;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.ComponentType;
import io.github.dawncord.api.types.SelectMenuType;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action row containing interactive components like buttons and select menus.
 */
public class ActionRow {
    private final List<ButtonData> buttons = new ArrayList<>();
    private final List<SelectMenuData> selectMenus = new ArrayList<>();

    /**
     * Constructs an ActionRow object from JSON data.
     *
     * @param actionRow The JSON data representing the action row.
     * @param guild     The guild associated with the action row.
     */
    public ActionRow(JsonNode actionRow, Guild guild) {
        JsonNode components = actionRow.get("components");

        for (JsonNode component : components) {
            int type = component.get("type").asInt();

            if (type == ComponentType.BUTTON.getValue()) {
                Emoji emoji = null;
                if (component.has("emoji")) {
                    if (component.get("emoji").has("id") && component.get("emoji").get("id") != null) {
                        emoji = new CustomEmojiImpl(component, guild);
                    } else if (!component.get("emoji").has("id") || component.get("emoji").get("id") == null) {
                        emoji = new DefaultEmoji(component.get("name").asText());
                    }
                }
                ButtonData button = new ButtonData(
                        component.has("custom_id") ? component.get("custom_id").asText() : null,
                        component.has("url") ? component.get("url").asText() : null,
                        EnumUtils.getEnumObjectFromInt(component.get("style").asInt(), ButtonStyle.class),
                        component.get("label").asText(),
                        component.has("disabled") && component.get("disabled").asBoolean(),
                        emoji
                );
                buttons.add(button);
            }

            if (type != ComponentType.ACTION.getValue()
                    || type != ComponentType.BUTTON.getValue()
                    || type != ComponentType.TEXT_INPUT.getValue()) {
                SelectMenuData selectMenu = new SelectMenuData(
                        component.get("custom_id").asText(),
                        component.has("placeholder") ? component.get("placeholder").asText() : null,
                        component.has("min_values") ? component.get("min_values").asInt() : 1,
                        component.has("max_values") ? component.get("max_values").asInt() : 1,
                        component.has("options") ? component.get("options") : null,
                        component.has("disabled") && component.get("disabled").asBoolean(),
                        EnumUtils.getEnumList(component.get("channel_types"), ChannelType.class),
                        guild
                );
                selectMenus.add(selectMenu);
            }
        }
    }

    /**
     * Retrieves the list of buttons in the action row.
     *
     * @return The list of buttons.
     */
    public List<ButtonData> getButtons() {
        return buttons;
    }

    /**
     * Retrieves the list of select menus in the action row.
     *
     * @return The list of select menus.
     */
    public List<SelectMenuData> getSelectMenus() {
        return selectMenus;
    }

    private List<SelectMenuBuilder.DefaultValue> getDefaultValues(JsonNode defaultValuesArray) {
        List<SelectMenuBuilder.DefaultValue> defaultValues = new ArrayList<>();
        for (int i = 0; i < defaultValuesArray.size(); i++) {
            JsonNode defaultValueJson = defaultValuesArray.get(i);
            SelectMenuBuilder.DefaultValue defaultValue = new SelectMenuBuilder.DefaultValue(
                    defaultValueJson.get("id").asText(),
                    EnumUtils.getEnumObject(defaultValueJson, "value", SelectMenuType.class)
            );
            defaultValues.add(defaultValue);
        }

        return defaultValues;
    }
}
