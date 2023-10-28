package org.dimas4ek.wrapper.utils;

import org.dimas4ek.wrapper.entities.message.component.ActionRowBuilder;
import org.dimas4ek.wrapper.entities.message.component.ButtonBuilder;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.component.SelectMenuBuilder;
import org.dimas4ek.wrapper.types.SelectMenuType;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ComponentUtils {
    public static JSONArray createComponents(List<ComponentBuilder> components) {
        JSONArray actionRowsArray = new JSONArray();

        for (ComponentBuilder component : components) {
            if (component instanceof ActionRowBuilder actionRow) {
                JSONObject actionRowJson = createActionRowJson(actionRow);
                actionRowsArray.put(actionRowJson);
            }
        }
        return actionRowsArray;
    }

    private static JSONObject createActionRowJson(ActionRowBuilder actionRow) {
        JSONObject actionRowJson = new JSONObject()
                .put("type", actionRow.getType());

        if (actionRow.getComponents() != null && !actionRow.getComponents().isEmpty()) {
            JSONArray componentsArray = new JSONArray();
            for (ComponentBuilder actionRowComponent : actionRow.getComponents()) {
                if (actionRowComponent instanceof ButtonBuilder button) {
                    componentsArray.put(createButtonJson(button));
                } else if (actionRowComponent instanceof SelectMenuBuilder selectMenu) {
                    componentsArray.put(createSelectMenuJson(selectMenu));
                }
            }
            actionRowJson.put("components", componentsArray);
        }

        return actionRowJson;
    }

    private static JSONObject createSelectMenuJson(SelectMenuBuilder selectMenu) {
        return new JSONObject()
                .put("type", selectMenu.getType())
                .put("custom_id", selectMenu.getCustomId())
                .put("placeholder", selectMenu.getPlaceholder())
                .put("channel_types", setChannelTypes(selectMenu))
                .put("default_values", setDefaultValues(selectMenu))
                .put("min_values", selectMenu.getMinValues())
                .put("max_values", selectMenu.getMaxValues())
                .put("disabled", selectMenu.isDisabled())
                .put("options", setSelectOptions(selectMenu));
    }

    private static JSONObject createButtonJson(ButtonBuilder button) {
        return new JSONObject()
                .put("type", button.getType())
                .put("style", button.getStyle())
                .put("custom_id", button.getCustomId())
                .put("url", button.getUrl())
                .put("label", button.getLabel())
                .put("disabled", button.isDisabled())
                .put("emoji", createEmojiJson(button.getEmoji()));
    }

    private static JSONObject createEmojiJson(String emoji) {
        if (emoji == null) {
            return null;
        }

        JSONObject emojiJson = new JSONObject();
        if (MessageUtils.isEmojiLong(emoji)) {
            emojiJson.put("id", emoji);
        } else {
            emojiJson.put("name", emoji);
        }

        return emojiJson;
    }

    @Nullable
    private static JSONArray setSelectOptions(SelectMenuBuilder selectMenu) {
        if (selectMenu.getOptions() == null || selectMenu.getOptions().isEmpty()) {
            return null;
        }

        JSONArray selectOptions = new JSONArray();
        selectMenu.getOptions().forEach(option -> {
            JSONObject optionJson = new JSONObject()
                    .put("label", option.getLabel())
                    .put("value", option.getValue())
                    .put("description", option.getDescription())
                    .put("emoji", createEmojiJson(option.getEmoji()))
                    .put("default", option.isDefault());

            selectOptions.put(optionJson);
        });

        return selectOptions;
    }

    @Nullable
    private static JSONArray setDefaultValues(SelectMenuBuilder selectMenu) {
        if (selectMenu.getDefaultValues() == null || selectMenu.getDefaultValues().isEmpty()) {
            return null;
        }

        JSONArray defaultValues = new JSONArray();
        selectMenu.getDefaultValues().forEach(value -> {
            if (value.getType() != SelectMenuType.TEXT) {
                JSONObject defaultValue = new JSONObject()
                        .put("id", value.getId())
                        .put("type", value.getType().getValue());
                defaultValues.put(defaultValue);
            }
        });

        return defaultValues;
    }

    @Nullable
    private static JSONArray setChannelTypes(SelectMenuBuilder selectMenu) {
        if (selectMenu.getChannelTypes() == null || selectMenu.getChannelTypes().isEmpty() || selectMenu.getType() != SelectMenuType.CHANNEL.getValue()) {
            return null;
        }

        JSONArray channelTypes = new JSONArray();
        selectMenu.getChannelTypes().forEach(type -> {
            channelTypes.put(type.getValue());
        });

        return channelTypes;
    }
}
