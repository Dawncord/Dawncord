package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.AutoModActionType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.types.KeywordPreset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.util.List;

public class AutoModRuleModifyAction {
    private final String guildId;
    private final AutoModTriggerType triggerType;
    private final JSONObject jsonObject;

    public AutoModRuleModifyAction(String guildId, AutoModTriggerType triggerType) {
        this.guildId = guildId;
        this.triggerType = triggerType;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("actions", new JSONArray());
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setKeywordTrigger(List<String> keywordFilter, List<String> allows) {
        if (triggerType == AutoModTriggerType.KEYWORD) {
            setProperty("trigger_metadata",
                    new JSONObject()
                            .put("keyword_filter", keywordFilter)
                            .put("allow_list", allows)
            );
        }
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setSpamTrigger() {
        if (triggerType == AutoModTriggerType.SPAM) {
            setProperty("trigger_metadata", new JSONObject());
        }
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        if (triggerType == AutoModTriggerType.KEYWORD_PRESET) {
            setProperty("trigger_metadata",
                    new JSONObject()
                            .put("presets", presets.stream().map(KeywordPreset::getValue).toList())
                            .put("allow_list", allows)
            );
        }
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        if (triggerType == AutoModTriggerType.MENTION_SPAM) {
            setProperty("trigger_metadata",
                    new JSONObject()
                            .put("mention_total_limit", mentionLimit)
                            .put("mention_raid_protection_enabled", isRaidProtected)
            );
        }
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setTimeoutAction(int duration) {
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
    public AutoModRuleModifyAction setAlertMessageAction(String channelId) {
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
    public AutoModRuleModifyAction setBlockMessageAction(String message) {
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
    public AutoModRuleModifyAction setBlockMessageAction() {
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
    public AutoModRuleModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setExemptRoles(String... exemptRoles) {
        setProperty("exempt_roles", exemptRoles);
        return this;
    }

    @CheckReturnValue
    public AutoModRuleModifyAction setExemptChannels(String... exemptChannels) {
        setProperty("exempt_channels", exemptChannels);
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/auto-moderation/rules");
        jsonObject.clear();
    }
}
