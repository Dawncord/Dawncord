package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.types.AutoModActionType;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.types.KeywordPreset;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

/**
 * Represents an action for creating an automod rule in a guild.
 *
 * @see AutoModRule
 */
public class AutoModRuleCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String guildId;
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link AutoModRuleCreateAction}
     *
     * @param guildId The ID of the guild for which the rule is created.
     */
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

    /**
     * Sets the name of the rule.
     *
     * @param name The name of the rule.
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the keyword trigger for the rule.
     *
     * @param keywordFilter The filter for keywords.
     * @param allows        The list of allowed keywords.
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setKeywordTrigger(List<String> keywordFilter, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .<ObjectNode>set("keyword_filter", mapper.valueToTree(keywordFilter))
                        .set("allow_list", mapper.valueToTree(allows))
        );
        return this;
    }

    /**
     * Sets the spam trigger for the rule.
     *
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setSpamTrigger() {
        setProperty("trigger_type", AutoModTriggerType.SPAM.getValue());
        setProperty("trigger_metadata", mapper.createObjectNode());
        return this;
    }

    /**
     * Sets the keyword preset trigger for the rule.
     *
     * @param presets The list of keyword presets.
     * @param allows  The list of allowed keywords.
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        setProperty("trigger_type", AutoModTriggerType.KEYWORD_PRESET.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .<ObjectNode>set("presets", mapper.valueToTree(presets.stream().map(KeywordPreset::getValue).toList()))
                        .set("allow_list", mapper.valueToTree(allows))
        );
        return this;
    }

    /**
     * Sets the mention spam trigger for the rule.
     *
     * @param mentionLimit    The mention limit.
     * @param isRaidProtected Whether raid protection is enabled.
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        setProperty("trigger_type", AutoModTriggerType.MENTION_SPAM.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .put("mention_total_limit", mentionLimit)
                        .put("mention_raid_protection_enabled", isRaidProtected)
        );
        return this;
    }

    /**
     * Sets the timeout action for the rule.
     *
     * @param duration The duration of the timeout in seconds.
     * @return the modified AutoModRuleCreateAction object
     */
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

    /**
     * Sets the alert message action for the rule
     *
     * @param channelId the ID of the channel where the alert message will be sent
     * @return the modified AutoModRuleCreateAction object
     */
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

    /**
     * Sets the block message action for the rule
     *
     * @param message the custom message to be displayed
     * @return the modified AutoModRuleCreateAction object
     */
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

    /**
     * Sets the block message action for the rule.
     *
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setBlockMessageAction() {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode())
        );
        return setProperty("actions", actionsArray);
    }

    /**
     * Sets the enabled state for the rule.
     *
     * @param enabled the enabled state to set
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the exempt roles for the rule.
     *
     * @param exemptRoles the roles to exempt from AutoMod rules
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setExemptRoles(String... exemptRoles) {
        return setProperty("exempt_roles", exemptRoles);
    }

    /**
     * Sets the exempt channels for the rule.
     *
     * @param exemptChannels the channels to exempt from AutoMod rules
     * @return the modified AutoModRuleCreateAction object
     */
    public AutoModRuleCreateAction setExemptChannels(String... exemptChannels) {
        return setProperty("exempt_channels", exemptChannels);
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            int keywordLimit = 0;
            int spamLimit = 0;
            int mentionSpamLimit = 0;
            int keywordPresetLimit = 0;
            JsonNode rules = JsonUtils.fetch(Routes.Guild.AutoMod.All(guildId));
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
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.AutoMod.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
