package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.entities.message.component.ActionRowBuilder;
import org.dimas4ek.wrapper.entities.message.component.ButtonBuilder;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.component.SelectMenuBuilder;
import org.dimas4ek.wrapper.types.SelectMenuType;

import java.util.List;

public class ComponentUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ArrayNode createComponents(List<ComponentBuilder> components) {
        ArrayNode actionRowsArray = mapper.createArrayNode();

        for (ComponentBuilder component : components) {
            if (component instanceof ActionRowBuilder actionRow) {
                ObjectNode actionRowJson = createActionRowJson(actionRow);
                actionRowsArray.add(actionRowJson);
            }
        }
        return actionRowsArray;
    }

    private static ObjectNode createActionRowJson(ActionRowBuilder actionRow) {
        ObjectNode actionRowJson = mapper.createObjectNode();
        actionRowJson.put("type", actionRow.getType());

        if (actionRow.getComponents() != null && !actionRow.getComponents().isEmpty()) {
            ArrayNode componentsArray = mapper.createArrayNode();
            for (ComponentBuilder actionRowComponent : actionRow.getComponents()) {
                if (actionRowComponent instanceof ButtonBuilder button) {
                    componentsArray.add(createButtonJson(button));
                } else if (actionRowComponent instanceof SelectMenuBuilder selectMenu) {
                    componentsArray.add(createSelectMenuJson(selectMenu));
                }
            }
            actionRowJson.set("components", componentsArray);
        }

        return actionRowJson;
    }

    private static ObjectNode createSelectMenuJson(SelectMenuBuilder selectMenu) {
        ObjectNode selectMenuJson = mapper.createObjectNode();
        selectMenuJson.put("type", selectMenu.getType())
                .put("custom_id", selectMenu.getCustomId())
                .put("placeholder", selectMenu.getPlaceholder())
                .put("min_values", selectMenu.getMinValues())
                .put("max_values", selectMenu.getMaxValues())
                .put("disabled", selectMenu.isDisabled());
        selectMenuJson.set("channel_types", setChannelTypes(selectMenu));
        selectMenuJson.set("default_values", setDefaultValues(selectMenu));
        selectMenuJson.set("options", setSelectOptions(selectMenu));

        return selectMenuJson;
    }

    private static ObjectNode createButtonJson(ButtonBuilder button) {
        ObjectNode buttonJson = mapper.createObjectNode();
        buttonJson.put("type", button.getType())
                .put("style", button.getStyle())
                .put("custom_id", button.getCustomId())
                .put("url", button.getUrl())
                .put("label", button.getLabel())
                .put("disabled", button.isDisabled())
                .set("emoji", createEmojiJson(button.getEmoji()));

        return buttonJson;
    }

    private static ObjectNode createEmojiJson(String emoji) {
        ObjectNode emojiJson = mapper.createObjectNode();
        if (emoji != null) {
            if (MessageUtils.isEmojiLong(emoji)) {
                emojiJson.put("id", emoji);
            } else {
                emojiJson.put("name", emoji);
            }
        }
        return emojiJson;
    }

    private static ArrayNode setSelectOptions(SelectMenuBuilder selectMenu) {
        ArrayNode selectOptions = mapper.createArrayNode();

        if (selectMenu.getOptions() != null) {
            selectMenu.getOptions().forEach(option -> {
                ObjectNode optionJson = mapper.createObjectNode()
                        .put("label", option.getLabel())
                        .put("value", option.getValue())
                        .put("description", option.getDescription())
                        .put("default", option.isDefault());
                optionJson.set("emoji", createEmojiJson(option.getEmoji()));
                selectOptions.add(optionJson);
            });
        }

        return selectOptions;
    }

    private static ArrayNode setDefaultValues(SelectMenuBuilder selectMenu) {
        ArrayNode defaultValues = mapper.createArrayNode();

        if (selectMenu.getDefaultValues() != null) {
            selectMenu.getDefaultValues().forEach(value -> {
                if (value.getType() != SelectMenuType.TEXT) {
                    ObjectNode defaultValue = mapper.createObjectNode()
                            .put("id", value.getId())
                            .put("type", value.getType().getValue());
                    defaultValues.add(defaultValue);
                }
            });
        }

        return defaultValues;
    }

    private static ArrayNode setChannelTypes(SelectMenuBuilder selectMenu) {
        ArrayNode channelTypes = mapper.createArrayNode();

        if (selectMenu.getChannelTypes() != null && selectMenu.getType() == SelectMenuType.CHANNEL.getValue()) {
            selectMenu.getChannelTypes().forEach(type -> {
                channelTypes.add(type.getValue());
            });
        }

        return channelTypes;
    }
}
