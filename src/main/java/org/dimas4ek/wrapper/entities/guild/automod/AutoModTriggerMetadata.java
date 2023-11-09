package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.types.KeywordPreset;

import java.util.List;

public interface AutoModTriggerMetadata {
    List<String> getKeywordFilters();

    List<KeywordPreset> getPresets();

    List<String> getAllows();

    int getMentionLimit();

    boolean isRaidProtected();
}
