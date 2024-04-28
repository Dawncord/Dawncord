package org.dimas4ek.dawncord.entities.message.component;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.CustomEmojiImpl;
import org.dimas4ek.dawncord.entities.DefaultEmoji;
import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.types.ChannelType;

import java.util.ArrayList;
import java.util.List;

public class SelectMenuData {
    private final String customId;
    private final String placeholder;
    private final int minValues;
    private final int maxValues;
    private final JsonNode options;
    private final boolean disabled;
    private final List<ChannelType> channelTypes;
    private final Guild guild;

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

    public String getCustomId() {
        return customId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public int getMinValues() {
        return minValues;
    }

    public int getMaxValues() {
        return maxValues;
    }

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

    public boolean isDisabled() {
        return disabled;
    }

    public List<ChannelType> getChannelTypes() {
        return channelTypes;
    }
}
