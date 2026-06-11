package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.KeywordPreset;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;


/**
 * Represents metadata for an auto moderation rule trigger.
 */
public class AutoModTriggerMetadata {
    private final LazyLoader loader;
    private List<String> keywordFilters;
    private List<KeywordPreset> presets;
    private List<String> allows;
    private Integer mentionLimit;
    private Boolean isRaidProtected;

    /**
     * Constructs a new AutoModTriggerMetadata with the specified JSON node.
     *
     * @param metadata The JSON node containing metadata.
     */
    public AutoModTriggerMetadata(JsonNode metadata) {
        loader = new LazyLoader(metadata);
    }

    /*protected AutoModTriggerMetadata(List<String> keywordFilter, List<KeywordPreset> presets, List<String> allows, int mentionLimit, boolean raidProtected) {
        this.metadata = null;
        this.loader = null;
        this.keywordFilters = keywordFilter;
        this.presets = presets;
        this.allows = allows;
        this.mentionLimit = mentionLimit;
        this.isRaidProtected = raidProtected;
    }*/

    public List<String> getKeywordFilters() {
        keywordFilters = loader.loadStringList(keywordFilters, "keyword_filter");
        return keywordFilters;
    }

    public List<KeywordPreset> getPresets() {
        presets = loader.loadEnumList(presets, "presets", KeywordPreset.class, "int");
        return presets;
    }

    public List<String> getAllows() {
        allows = loader.loadStringList(allows, "allow_list");
        return allows;
    }

    public int getMentionLimit() {
        mentionLimit = loader.loadInt(mentionLimit, "mention_total_limit");
        return mentionLimit;
    }

    public boolean isRaidProtected() {
        isRaidProtected = loader.loadBoolean(isRaidProtected, "mention_raid_protection_enabled");
        return isRaidProtected;
    }
}
