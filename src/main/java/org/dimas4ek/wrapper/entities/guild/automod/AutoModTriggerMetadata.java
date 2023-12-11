package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.types.KeywordPreset;

import java.util.List;

public interface AutoModTriggerMetadata {
    List<String> getKeywordFilters();

    List<KeywordPreset> getPresets();

    List<String> getAllows();

    int getMentionLimit();

    boolean isRaidProtected();

    static AutoModTriggerMetadata of(List<String> keywordFilter, List<KeywordPreset> presets, List<String> allows, int mentionLimit, boolean raidProtected) {
        return new AutoModTriggerMetadataImpl(keywordFilter, presets, allows, mentionLimit, raidProtected);
    }
}
