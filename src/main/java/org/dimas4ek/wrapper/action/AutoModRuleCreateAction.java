package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.AutoModActionType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.types.KeywordPreset;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

public class AutoModRuleCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String guildId;
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    public AutoModRuleCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("event_type", 1);
        this.jsonObject.set("actions", mapper.createArrayNode());
    }

    private AutoModRuleCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public AutoModRuleCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public AutoModRuleCreateAction setKeywordTrigger(List<String> keywordFilter, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .<ObjectNode>set("keyword_filter", mapper.valueToTree(keywordFilter))
                        .set("allow_list", mapper.valueToTree(allows))
        );
        return this;
    }

    public AutoModRuleCreateAction setSpamTrigger() {
        setProperty("trigger_type", AutoModTriggerType.SPAM.getValue());
        setProperty("trigger_metadata", mapper.createObjectNode());
        return this;
    }

    public AutoModRuleCreateAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD_PRESET.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .<ObjectNode>set("presets", mapper.valueToTree(presets.stream().map(KeywordPreset::getValue).toList()))
                        .set("allow_list", mapper.valueToTree(allows))
        );
        return this;
    }

    public AutoModRuleCreateAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        setProperty("trigger_type", AutoModTriggerType.MENTION_SPAM.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .put("mention_total_limit", mentionLimit)
                        .put("mention_raid_protection_enabled", isRaidProtected)
        );
        return this;
    }

    public AutoModRuleCreateAction setTimeoutAction(int duration) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.TIMEOUT.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("duration_seconds", duration)
                        )
        );
        return setProperty("actions", actionsArray);
    }

    public AutoModRuleCreateAction setAlertMessageAction(String channelId) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.SEND_ALERT_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("channel_id", channelId)
                        )
        );
        return setProperty("actions", actionsArray);
    }

    public AutoModRuleCreateAction setBlockMessageAction(String message) {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode()
                                .put("custom_message", message)
                        )
        );
        return setProperty("actions", actionsArray);
    }

    public AutoModRuleCreateAction setBlockMessageAction() {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode())
        );
        return setProperty("actions", actionsArray);
    }

    public AutoModRuleCreateAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    public AutoModRuleCreateAction setExemptRoles(String... exemptRoles) {
        return setProperty("exempt_roles", exemptRoles);
    }

    public AutoModRuleCreateAction setExemptChannels(String... exemptChannels) {
        return setProperty("exempt_channels", exemptChannels);
    }

    private void submit() {
        if (hasChanges) {
            int keywordLimit = 0;
            int spamLimit = 0;
            int mentionSpamLimit = 0;
            int keywordPresetLimit = 0;
            JsonNode rules = JsonUtils.fetchArray(Routes.Guild.AutoMod.All(guildId));
            for (JsonNode rule : rules) {
                switch (Objects.requireNonNull(EnumUtils.getEnumObject(rule, "trigger_type", AutoModTriggerType.class))) {
                    case KEYWORD -> keywordLimit++;
                    case SPAM -> spamLimit++;
                    case MENTION_SPAM -> mentionSpamLimit++;
                    case KEYWORD_PRESET -> keywordPresetLimit++;
                }
            }
            int triggerType = jsonObject.get("trigger_type").asInt();
            if (triggerType == AutoModTriggerType.KEYWORD.getValue() && keywordLimit == 6 ||
                    triggerType == AutoModTriggerType.SPAM.getValue() && spamLimit == 1 ||
                    triggerType == AutoModTriggerType.MENTION_SPAM.getValue() && mentionSpamLimit == 1 ||
                    triggerType == AutoModTriggerType.KEYWORD_PRESET.getValue() && keywordPresetLimit == 1) {
                jsonObject.removeAll();
                return;
            }
            ApiClient.post(jsonObject, Routes.Guild.AutoMod.All(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
