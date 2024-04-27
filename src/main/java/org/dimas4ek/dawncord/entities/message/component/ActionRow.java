package org.dimas4ek.dawncord.entities.message.component;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.CustomEmojiImpl;
import org.dimas4ek.dawncord.entities.DefaultEmoji;
import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.types.ButtonStyle;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.ComponentType;
import org.dimas4ek.dawncord.types.SelectMenuType;
import org.dimas4ek.dawncord.utils.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class ActionRow {
    private final List<ButtonData> buttons = new ArrayList<>();
    private final List<SelectMenuData> selectMenus = new ArrayList<>();

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

    public List<ButtonData> getButtons() {
        return buttons;
    }

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
