package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.types.AutoModActionType;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.types.KeywordPreset;

import java.util.List;

/**
 * Represents an action for updating an automod rule in a guild.
 *
 * @see AutoModRule
 */
public class AutoModRuleModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final AutoModTriggerType triggerType;
    private boolean hasChanges = false;

    /**
     * Create a new {@link AutoModRuleModifyAction}
     *
     * @param guildId     The ID of the guild where the auto-moderation rule is located.
     * @param triggerType The trigger type of the auto-moderation rule to be modified.
     */
    public AutoModRuleModifyAction(String guildId, AutoModTriggerType triggerType) {
        this.guildId = guildId;
        this.triggerType = triggerType;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.set("actions", mapper.createArrayNode());
    }

    private AutoModRuleModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the rule.
     *
     * @param name the new name to set
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the keyword trigger for the rule.
     *
     * @param keywordFilter the list of keyword filters to set
     * @param allows        the list of allowed keywords
     * @return the modified AutoModRuleModifyAction object
     */
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

    /**
     * Sets the spam trigger v
     *
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setSpamTrigger() {
        if (triggerType == AutoModTriggerType.SPAM) {
            setProperty("trigger_metadata", mapper.createObjectNode());
        }
        return this;
    }

    /**
     * Sets the keyword preset trigger for the rule.
     *
     * @param presets a list of KeywordPreset objects representing the presets
     * @param allows  a list of strings representing the allow list
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows) {
        if (triggerType == AutoModTriggerType.KEYWORD_PRESET) {
            return setProperty("trigger_metadata",
                    mapper.createObjectNode()
                            .<ObjectNode>set("presets", mapper.valueToTree(presets.stream().map(KeywordPreset::getValue).toList()))
                            .set("allow_list", mapper.valueToTree(allows))
            );
        }
        return this;
    }

    /**
     * Sets the mention spam trigger for the rule.
     *
     * @param mentionLimit    the mention total limit to set
     * @param isRaidProtected whether raid protection is enabled or not
     * @return the modified AutoModRuleModifyAction object
     */
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

    /**
     * Sets a timeout action for the rule.
     *
     * @param duration the duration in seconds for the timeout action
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setTimeoutAction(int duration) {
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
     * Sets an alert message action for the rule.
     *
     * @param channelId the ID of the channel where the alert message is sent
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setAlertMessageAction(String channelId) {
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
     * Sets a block message action for the rule.
     *
     * @param message the custom message to be used in the block message action
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setBlockMessageAction(String message) {
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
     * Sets a block message action for the rule.
     *
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setBlockMessageAction() {
        ArrayNode actionsArray = (ArrayNode) jsonObject.get("actions");
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", AutoModActionType.BLOCK_MESSAGE.getValue())
                        .set("metadata", mapper.createObjectNode())
        );
        return setProperty("actions", actionsArray);
    }

    /**
     * Sets the enabled for the rule.
     *
     * @param enabled the boolean value to set the enabled property to
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the exempt roles property for the rule.
     *
     * @param exemptRoles the exempt roles to set
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setExemptRoles(String... exemptRoles) {
        return setProperty("exempt_roles", exemptRoles);
    }

    /**
     * Sets the exempt channels property for the rule.
     *
     * @param exemptChannels the exempt channels to set
     * @return the modified AutoModRuleModifyAction object
     */
    public AutoModRuleModifyAction setExemptChannels(String... exemptChannels) {
        return setProperty("exempt_channels", exemptChannels);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.AutoMod.All(guildId));
            hasChanges = false;
        }
    }
}
