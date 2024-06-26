package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.message.component.ActionRowBuilder;
import io.github.dawncord.api.entities.message.component.ButtonBuilder;
import io.github.dawncord.api.entities.message.component.ComponentBuilder;
import io.github.dawncord.api.entities.message.component.SelectMenuBuilder;
import io.github.dawncord.api.types.SelectMenuType;

import java.util.List;

/**
 * Utility class for creating components.
 */
public class ComponentUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates an array node of components from a list of component builders.
     *
     * @param components The list of component builders.
     * @return The array node containing the components.
     */
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
                .put("disabled", button.isDisabled());
        if (button.getEmoji() != null) {
            buttonJson.set("emoji", createEmojiJson(button.getEmoji()));
        }

        return buttonJson;
    }

    private static ObjectNode createEmojiJson(Emoji emoji) {
        ObjectNode emojiJson = mapper.createObjectNode();
        if (emoji instanceof CustomEmoji customEmoji) {
            emojiJson.put("id", customEmoji.getId());
            if (customEmoji.getName() != null) {
                emojiJson.put("name", customEmoji.getName());
            }
            if (customEmoji.isAnimated()) {
                emojiJson.put("animated", true);
            }
        } else if (emoji instanceof DefaultEmoji defaultEmoji) {
            emojiJson.set("id", JsonNodeFactory.instance.nullNode());
            emojiJson.put("name", defaultEmoji.getName());
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
                if (option.getEmoji() != null) {
                    optionJson.set("emoji", createEmojiJson(option.getEmoji()));
                }
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
            selectMenu.getChannelTypes().forEach(type -> channelTypes.add(type.getValue()));
        }

        return channelTypes;
    }
}
