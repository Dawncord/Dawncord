package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.GuildOnboarding;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.types.OnboardingMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class GuildOnboardingModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildOnboardingModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildOnboardingModifyAction setPrompts(GuildOnboarding.Prompt... prompts) {
        JSONArray promptsArray = new JSONArray();
        for (GuildOnboarding.Prompt prompt : prompts) {
            JSONObject promptJson = new JSONObject()
                    .put("id", prompt.getId())
                    .put("type", prompt.getType().getValue())
                    .put("options", setOptions(prompt))
                    .put("title", prompt.getTitle())
                    .put("single_select", prompt.isSingleSelect())
                    .put("required", prompt.isRequired())
                    .put("in_onboarding", prompt.inOnboarding());
            promptsArray.put(promptJson);
        }
        jsonObject.put("prompts", promptsArray);
        return this;
    }

    private JSONArray setOptions(GuildOnboarding.Prompt prompt) {
        JSONArray optionsArray = new JSONArray();
        for (GuildOnboarding.Prompt.Option option : prompt.getOptions()) {
            JSONObject optionJson = new JSONObject()
                    .put("id", option.getId())
                    .put("title", option.getTitle())
                    .put("description", option.getDescription())
                    .put("channel_ids", option.getChannels().stream().map(GuildChannel::getId).toList())
                    .put("role_ids", option.getRoles().stream().map(GuildRole::getId).toList())
                    .put("emoji_id", option.getEmoji().getId())
                    .put("emoji_name", option.getEmoji().getName())
                    .put("emoji_animated", option.getEmoji().isAnimated());
            optionsArray.put(optionJson);
        }
        return optionsArray;
    }

    public GuildOnboardingModifyAction setChannelIds(String... channelIds) {
        jsonObject.put("default_channel_ids", channelIds);
        return this;
    }

    public GuildOnboardingModifyAction setEnabled(boolean enabled) {
        jsonObject.put("enabled", enabled);
        return this;
    }

    public GuildOnboardingModifyAction setMode(OnboardingMode mode) {
        jsonObject.put("mode", mode.getValue());
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.put(jsonObject, "/guilds/" + guildId + "/onboarding");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
