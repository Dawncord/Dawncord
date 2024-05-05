package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.GuildOnboarding;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.OnboardingMode;

/**
 * Represents an action for updating a guild onboarding.
 *
 * @see GuildOnboarding
 */
public class GuildOnboardingModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildOnboardingModifyAction}
     *
     * @param guildId The ID of the guild for which onboarding settings are being modified.
     */
    public GuildOnboardingModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildOnboardingModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets prompts for the guild onboarding.
     *
     * @param prompts an array of prompts to set
     * @return the modified GuildOnboardingModifyAction object
     */
    public GuildOnboardingModifyAction setPrompts(GuildOnboarding.Prompt... prompts) {
        ArrayNode promptsArray = mapper.createArrayNode();
        for (GuildOnboarding.Prompt prompt : prompts) {
            ObjectNode promptJson = mapper.createObjectNode()
                    .put("id", prompt.getId())
                    .put("type", prompt.getType().getValue())
                    .<ObjectNode>set("options", setOptions(prompt))
                    .put("title", prompt.getTitle())
                    .put("single_select", prompt.isSingleSelect())
                    .put("required", prompt.isRequired())
                    .put("in_onboarding", prompt.inOnboarding());
            promptsArray.add(promptJson);
        }
        jsonObject.set("prompts", promptsArray);
        return this;
    }

    private ArrayNode setOptions(GuildOnboarding.Prompt prompt) {
        ArrayNode optionsArray = mapper.createArrayNode();
        for (GuildOnboarding.Prompt.Option option : prompt.getOptions()) {
            ObjectNode optionJson = mapper.createObjectNode()
                    .put("id", option.getId())
                    .put("title", option.getTitle())
                    .put("description", option.getDescription())
                    .put("emoji_id", option.getEmoji().getId())
                    .put("emoji_name", option.getEmoji().getName())
                    .put("emoji_animated", option.getEmoji().isAnimated())
                    .<ObjectNode>set("channel_ids", mapper.valueToTree(option.getChannels().stream().map(GuildChannel::getId).toList()))
                    .set("role_ids", mapper.valueToTree(option.getRoles().stream().map(GuildRole::getId).toList()));
            optionsArray.add(optionJson);
        }
        return optionsArray;
    }

    /**
     * Sets the channel IDs for the guild onboarding.
     *
     * @param channelIds the IDs of the channels to set
     * @return the modified GuildOnboardingModifyAction object
     */
    public GuildOnboardingModifyAction setChannelIds(String... channelIds) {
        return setProperty("default_channel_ids", channelIds);
    }

    /**
     * Sets the enabled state for the guild onboarding.
     *
     * @param enabled the new enabled state
     * @return the modified GuildOnboardingModifyAction object
     */
    public GuildOnboardingModifyAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the mode for the guild onboarding.
     *
     * @param mode the mode to set
     * @return the modified GuildOnboardingModifyAction object
     */
    public GuildOnboardingModifyAction setMode(OnboardingMode mode) {
        return setProperty("mode", mode.getValue());
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.put(jsonObject, Routes.Guild.Onboarding(guildId));
            hasChanges = false;
        }
    }
}
