package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.AutoModActionType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.types.KeywordPreset;

import java.util.List;

public class AutoModRuleModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final AutoModTriggerType triggerType;
    private boolean hasChanges = false;

    public AutoModRuleModifyAction(String guildId, AutoModTriggerType triggerType) {
        this.guildId = guildId;
        this.triggerType = triggerType;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.set("actions", mapper.createArrayNode());
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public AutoModRuleModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public AutoModRuleModifyAction setKeywordTrigger(List<String> keywordFilter, List<String> allows) {
        if (triggerType == AutoModTriggerType.KEYWORD) {
            setProperty("trigger_metadata",
                    mapper.createObjectNode()
                            .<ObjectNode>set("keyword_filter", mapper.valueToTree(keywordFilter))
                            .set("allow_list", mapper.valueToTree(allows))
            );
        }
        return this;
    }

    public AutoModRuleModifyAction setSpamTrigger() {
        if (triggerType == AutoModTriggerType.SPAM) {
            setProperty("trigger_metadata", mapper.createObjectNode());
        }
        return this;
    }

    public AutoModRuleModifyAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        if (triggerType == AutoModTriggerType.KEYWORD_PRESET) {
            setProperty("trigger_metadata",
                    mapper.createObjectNode()
                            .<ObjectNode>set("presets", mapper.valueToTree(presets.stream().map(KeywordPreset::getValue).toList()))
                            .set("allow_list", mapper.valueToTree(allows))
            );
        }
        return this;
    }

    public AutoModRuleModifyAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        if (triggerType == AutoModTriggerType.MENTION_SPAM) {
            setProperty("trigger_metadata",
                    mapper.createObjectNode()
                            .put("mention_total_limit", mentionLimit)
                            .put("mention_raid_protection_enabled", isRaidProtected)
            );
        }
        return this;
    }

    public AutoModRuleModifyAction setTimeoutAction(int duration) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.TIMEOUT.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("duration_seconds", duration)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    public AutoModRuleModifyAction setAlertMessageAction(String channelId) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.SEND_ALERT_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("channel_id", channelId)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    public AutoModRuleModifyAction setBlockMessageAction(String message) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("custom_message", message)
                        )
        );
        setProperty("actions", actionsArray);
        return this;
    }

    public AutoModRuleModifyAction setBlockMessageAction() {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode())
        );
        setProperty("actions", actionsArray);
        return this;
    }

    public AutoModRuleModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    public AutoModRuleModifyAction setExemptRoles(String... exemptRoles) {
        setProperty("exempt_roles", exemptRoles);
        return this;
    }

    public AutoModRuleModifyAction setExemptChannels(String... exemptChannels) {
        setProperty("exempt_channels", exemptChannels);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/auto-moderation/rules");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
