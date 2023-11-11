package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.AutoModActionType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.types.KeywordPreset;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.util.List;
import java.util.Objects;

public class AutoModRuleCreateAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public AutoModRuleCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("event_type", 1);
        this.jsonObject.put("actions", new JSONArray());
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setKeywordTrigger(List<String> keywordFilter, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD.getValue());
        setProperty("trigger_metadata",
                new JSONObject()
                        .put("keyword_filter", keywordFilter)
                        .put("allow_list", allows)
        );
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setSpamTrigger() {
        setProperty("trigger_type", AutoModTriggerType.SPAM.getValue());
        setProperty("trigger_metadata", new JSONObject());
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD_PRESET.getValue());
        setProperty("trigger_metadata",
                new JSONObject()
                        .put("presets", presets.stream().map(KeywordPreset::getValue).toList())
                        .put("allow_list", allows)
        );
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        setProperty("trigger_type", AutoModTriggerType.MENTION_SPAM.getValue());
        setProperty("trigger_metadata",
                new JSONObject()
                        .put("mention_total_limit", mentionLimit)
                        .put("mention_raid_protection_enabled", isRaidProtected)
        );
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setTimeoutAction(int duration) {
        JSONArray actionsArray = jsonObject.getJSONArray("actions");
        actionsArray.put(
                new JSONObject()
                        .put("type", AutoModActionType.TIMEOUT.getValue())
                        .put("metadata", new JSONObject()
                                .put("duration_seconds", duration)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setAlertMessageAction(String channelId) {
        JSONArray actionsArray = jsonObject.getJSONArray("actions");
        actionsArray.put(
                new JSONObject()
                        .put("type", AutoModActionType.SEND_ALERT_MESSAGE.getValue())
                        .put("metadata", new JSONObject()
                                .put("channel_id", channelId)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setBlockMessageAction(String message) {
        JSONArray actionsArray = jsonObject.getJSONArray("actions");
        actionsArray.put(
                new JSONObject()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .put("metadata", new JSONObject()
                                .put("custom_message", message)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setBlockMessageAction() {
        JSONArray actionsArray = jsonObject.getJSONArray("actions");
        actionsArray.put(
                new JSONObject()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .put("metadata", new JSONObject())
        );
        setProperty("actions", actionsArray);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setExemptRoles(String... exemptRoles) {
        setProperty("exempt_roles", exemptRoles);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleCreateAction setExemptChannels(String... exemptChannels) {
        setProperty("exempt_channels", exemptChannels);
        return this;
    }

    public void submit() {
        int keywordLimit = 0;
        int spamLimit = 0;
        int mentionSpamLimit = 0;
        int keywordPresetLimit = 0;
        JSONArray rules = JsonUtils.fetchArray("/guilds/" + guildId + "/auto-moderation/rules");
        for (int i = 0; i < rules.length(); i++) {
            JSONObject rule = rules.getJSONObject(i);
            switch (Objects.requireNonNull(EnumUtils.getEnumObject(rule, "trigger_type", AutoModTriggerType.class))) {
                case KEYWORD -> keywordLimit++;
                case SPAM -> spamLimit++;
                case MENTION_SPAM -> mentionSpamLimit++;
                case KEYWORD_PRESET -> keywordPresetLimit++;
            }
        }
        if (jsonObject.getInt("trigger_type") == AutoModTriggerType.KEYWORD.getValue() && keywordLimit == 6 ||
                jsonObject.getInt("trigger_type") == AutoModTriggerType.SPAM.getValue() && spamLimit == 1 ||
                jsonObject.getInt("trigger_type") == AutoModTriggerType.MENTION_SPAM.getValue() && mentionSpamLimit == 1 ||
                jsonObject.getInt("trigger_type") == AutoModTriggerType.KEYWORD_PRESET.getValue() && keywordPresetLimit == 1) {
            jsonObject.clear();
            return;
        }
        ApiClient.post(jsonObject, "/guilds/" + guildId + "/auto-moderation/rules");
        jsonObject.clear();
    }
}
