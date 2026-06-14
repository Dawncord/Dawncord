package io.github.dawncord.api.action.automoderule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.types.AutoModEventType;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.types.KeywordPreset;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an action for creating an automod rule in a guild.
 *
 * @see AutoModRule
 */
public class AutoModRuleCreateAction extends AutoModRuleAction {
    /**
     * Create a new {@link AutoModRuleCreateAction}
     *
     * @param guildId The ID of the guild for which the rule is created.
     */
    public AutoModRuleCreateAction(String guildId) {
        super(guildId);
    }

    /**
     * Sets the event type for the rule.
     *
     * @param eventType the event type to set
     * @return the modified AutoModRuleCreateAction object
     */
    @Override
    public AutoModRuleCreateAction setEventType(AutoModEventType eventType) {
        setProperty("event_type", eventType.getValue());
        return this;
    }

    /**
     * Sets the keyword trigger for the rule.
     *
     * @param keywordFilter The filter for keywords.
     * @param allows        The list of allowed keywords.
     * @return the modified AutoModRuleCreateAction object
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public AutoModRuleCreateAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected) {
        setProperty("trigger_type", AutoModTriggerType.MENTION_SPAM.getValue());
        setProperty("trigger_metadata",
                mapper.createObjectNode()
                        .put("mention_total_limit", mentionLimit)
                        .put("mention_raid_protection_enabled", isRaidProtected)
        );
        return this;
    }

    @Override
    protected void submit() {
        if (!hasChanges) {
            return;
        }

        if (isAutoModLimitReached()) {
            return;
        }

        createAutoModRule();
    }

    private boolean isAutoModLimitReached() {
        Map<AutoModTriggerType, Integer> ruleCounts = countExistingRules();
        AutoModTriggerType triggerType = getCurrentTriggerType();

        return switch (triggerType) {
            case KEYWORD -> ruleCounts.getOrDefault(AutoModTriggerType.KEYWORD, 0) >= 6;
            case SPAM -> ruleCounts.getOrDefault(AutoModTriggerType.SPAM, 0) >= 1;
            case MENTION_SPAM -> ruleCounts.getOrDefault(AutoModTriggerType.MENTION_SPAM, 0) >= 1;
            case KEYWORD_PRESET -> ruleCounts.getOrDefault(AutoModTriggerType.KEYWORD_PRESET, 0) >= 1;
        };
    }

    private Map<AutoModTriggerType, Integer> countExistingRules() {
        Map<AutoModTriggerType, Integer> counts = new EnumMap<>(AutoModTriggerType.class);
        JsonNode rules = JsonUtils.fetch(Routes.Guild.AutoMod.All(guildId));

        for (JsonNode rule : rules) {
            AutoModTriggerType triggerType = getTriggerTypeFromRule(rule);
            if (triggerType != null) {
                counts.merge(triggerType, 1, Integer::sum);
            }
        }

        return counts;
    }

    private AutoModTriggerType getTriggerTypeFromRule(JsonNode rule) {
        return EnumUtils.getEnumObject(rule, "trigger_type", AutoModTriggerType.class);
    }

    private AutoModTriggerType getCurrentTriggerType() {
        int triggerTypeValue = jsonObject.get("trigger_type").asInt();
        return AutoModTriggerType.fromValue(triggerTypeValue);
    }

    private void createAutoModRule() {
        JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.AutoMod.All(guildId));

        if (jsonNode != null && jsonNode.has("id")) {
            createdId = jsonNode.get("id").asText();
        }

        hasChanges = false;
    }
}
