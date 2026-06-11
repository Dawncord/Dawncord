package io.github.dawncord.api.action.automoderule;

import io.github.dawncord.api.types.KeywordPreset;

import java.util.List;

/**
 * Defines configuration options for auto moderation rule actions.
 */
public interface IAutoModeRuleAction {
    IAutoModeRuleAction setKeywordTrigger(List<String> keywordFilter, List<String> allows);

    IAutoModeRuleAction setSpamTrigger();

    IAutoModeRuleAction setKeywordPresetTrigger(List<KeywordPreset> presets, List<String> allows);

    IAutoModeRuleAction setMentionSpamTrigger(int mentionLimit, boolean isRaidProtected);
}
