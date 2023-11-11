package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.types.KeywordPreset;
import org.json.JSONObject;

import java.util.List;

public interface AutoModTriggerMetadata {
    List<String> getKeywordFilters();

    List<KeywordPreset> getPresets();

    List<String> getAllows();

    int getMentionLimit();

    boolean isRaidProtected();

    static AutoModTriggerMetadata of(List<String> keywordFilter, List<KeywordPreset> presets, List<String> allows, int mentionLimit, boolean raidProtected) {
        return new AutoModTriggerMetadataImpl(
                new JSONObject()
                        .put("keyword_filter", keywordFilter)
                        .put("presets", presets.stream().map(KeywordPreset::getValue).toList())
                        .put("allow_list", allows)
                        .put("mention_total_limit", mentionLimit)
                        .put("mention_raid_protection_enabled", raidProtected)
        );
    }
}
