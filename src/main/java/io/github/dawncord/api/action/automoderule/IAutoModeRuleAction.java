package io.github.dawncord.api.action.automoderule;

import io.github.dawncord.api.types.KeywordPreset;

import java.util.List;

/**
 * Defines trigger configuration options for auto moderation rule actions.
 */
public interface IAutoModeRuleAction {
    /**
     * Sets a keyword trigger with a custom filter and allow list.
     *
     * @param keywordFilter The list of keywords to block.
     * @param allows        The list of allowed keywords.
     * @return This action instance.
     */
    IAutoModeRuleAction setKeywordTrigger(List<String> keywordFilter, List<String> allows);

    /**
     * Sets a spam trigger for the rule.
     *
     * @return This action instance.
     */
    IAutoModeRuleAction setSpamTrigger();

    /**
     * Sets a keyword preset trigger with predefined word sets and an allow list.
     *
     * @param presets The list of keyword presets to use.
     * @param allows  The list of allowed keywords.
     * @return This action instance.
     */
    IAutoModeRuleAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows);

    /**
     * Sets a mention spam trigger with a mention limit and optional raid protection.
     *
     * @param mentionLimit    The maximum number of unique mentions allowed.
     * @param isRaidProtected Whether raid protection is enabled.
     * @return This action instance.
     */
    IAutoModeRuleAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected);
}
