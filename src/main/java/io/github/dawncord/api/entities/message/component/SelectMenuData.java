package io.github.dawncord.api.entities.message.component;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.CustomEmojiImpl;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.ChannelType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data for a select menu.
 */
public class SelectMenuData {
    private final String customId;
    private final String placeholder;
    private final int minValues;
    private final int maxValues;
    private final JsonNode options;
    private final boolean disabled;
    private final List<ChannelType> channelTypes;
    private final Guild guild;

    /**
     * Constructs a new SelectMenuData object with the specified parameters.
     *
     * @param customId     The custom ID of the select menu.
     * @param placeholder  The placeholder text displayed in the select menu.
     * @param minValues    The minimum number of values that can be selected.
     * @param maxValues    The maximum number of values that can be selected.
     * @param options      The JSON node containing the options for the select menu.
     * @param disabled     Whether the select menu is disabled.
     * @param channelTypes The types of channels the select menu is applicable to.
     * @param guild        The guild associated with the select menu.
     */
    public SelectMenuData(String customId, String placeholder, int minValues, int maxValues, JsonNode options, boolean disabled, List<ChannelType> channelTypes, Guild guild) {
        this.customId = customId;
        this.placeholder = placeholder;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.options = options;
        this.disabled = disabled;
        this.channelTypes = channelTypes;
        this.guild = guild;
    }

    /**
     * Retrieves the custom ID of the select menu.
     *
     * @return The custom ID of the select menu.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Retrieves the placeholder text of the select menu.
     *
     * @return The placeholder text of the select menu.
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Retrieves the minimum number of values that can be selected in the select menu.
     *
     * @return The minimum number of values that can be selected.
     */
    public int getMinValues() {
        return minValues;
    }

    /**
     * Retrieves the maximum number of values that can be selected in the select menu.
     *
     * @return The maximum number of values that can be selected.
     */
    public int getMaxValues() {
        return maxValues;
    }

    /**
     * Retrieves the options available in the select menu.
     *
     * @return A list of SelectOption objects representing the options.
     */
    public List<SelectOption> getOptions() {
        List<SelectOption> optionList = new ArrayList<>();
        if (options == null) return optionList;

        for (JsonNode option : options) {
            SelectOption selectOption = new SelectOption(option.get("label").asText(), option.get("value").asText());
            if (option.has("description")) {
                selectOption.setDescription(option.get("description").asText());
            }
            if (option.has("emoji")) {
                Emoji emoji;
                if (option.get("emoji").has("id") && option.get("emoji").get("id") != null) {
                    emoji = new CustomEmojiImpl(option.get("emoji"), guild);
                } else {
                    emoji = new DefaultEmoji(option.get("emoji").get("name").asText());
                }
                selectOption.withEmoji(emoji);
            }
            optionList.add(new SelectOption(option.get("label").asText(), option.get("value").asText()));
        }
        return optionList;
    }

    /**
     * Checks if the select menu is disabled.
     *
     * @return true if the select menu is disabled, false otherwise.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Retrieves the types of channels the select menu is applicable to.
     *
     * @return A list of ChannelType enum values representing the applicable channel types.
     */
    public List<ChannelType> getChannelTypes() {
        return channelTypes;
    }
}
