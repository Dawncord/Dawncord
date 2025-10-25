package io.github.dawncord.api.action.automoderule;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.types.KeywordPreset;

import java.util.List;

/**
 * Represents an action for updating an automod rule in a guild.
 *
 * @see AutoModRule
 */
public class AutoModRuleModifyAction extends AutoModeRuleAction {
    private final AutoModTriggerType triggerType;

    /**
     * Create a new {@link AutoModRuleModifyAction}
     *
     * @param guildId     The ID of the guild where the auto-moderation rule is located.
     * @param triggerType The trigger type of the auto-moderation rule to be modified.
     */
    public AutoModRuleModifyAction(String guildId, AutoModTriggerType triggerType) {
        this.triggerType = triggerType;
        super(guildId);
    }

    /**
     * Sets the keyword trigger for the rule.
     *
     * @param keywordFilter the list of keyword filters to set
     * @param allows        the list of allowed keywords
     * @return the modified AutoModRuleModifyAction object
     */
    @Override
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
    @Override
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
    @Override
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

    /**
     * Sets the mention spam trigger for the rule.
     *
     * @param mentionLimit    the mention total limit to set
     * @param isRaidProtected whether raid protection is enabled or not
     * @return the modified AutoModRuleModifyAction object
     */
    @Override
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

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.AutoMod.All(guildId));
            hasChanges = false;
        }
    }
}
