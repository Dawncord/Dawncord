package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.KeywordPreset;
import io.github.dawncord.api.utils.EnumUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link AutoModTriggerMetadata} interface.
 */
public class AutoModTriggerMetadataImpl implements AutoModTriggerMetadata {
    private final JsonNode metadata;
    private List<String> keywordFilters;
    private List<KeywordPreset> presets;
    private List<String> allows;
    private Integer mentionLimit;
    private Boolean isRaidProtected;

    /**
     * Constructs a new AutoModTriggerMetadataImpl with the specified JSON node.
     *
     * @param metadata The JSON node containing metadata.
     */
    public AutoModTriggerMetadataImpl(JsonNode metadata) {
        this.metadata = metadata;
    }

    protected AutoModTriggerMetadataImpl(List<String> keywordFilter, List<KeywordPreset> presets, List<String> allows, int mentionLimit, boolean raidProtected) {
        this.metadata = null;
        this.keywordFilters = keywordFilter;
        this.presets = presets;
        this.allows = allows;
        this.mentionLimit = mentionLimit;
        this.isRaidProtected = raidProtected;
    }

    @Override
    public List<String> getKeywordFilters() {
        if (keywordFilters == null) {
            assert metadata != null;
            keywordFilters = getStringList(metadata, "keyword_filter");
        }
        return keywordFilters;
    }

    @Override
    public List<KeywordPreset> getPresets() {
        if (presets == null) {
            assert metadata != null;
            presets = metadata.has("presets") && metadata.hasNonNull("presets")
                    ? EnumUtils.getEnumList(metadata.get("presets"), KeywordPreset.class)
                    : null;
        }
        return presets;
    }

    @Override
    public List<String> getAllows() {
        if (allows == null) {
            assert metadata != null;
            allows = getStringList(metadata, "allow_list");
        }
        return allows;
    }

    @Override
    public int getMentionLimit() {
        if (mentionLimit == null) {
            assert metadata != null;
            mentionLimit = metadata.get("mention_total_limit").asInt();
        }
        return mentionLimit;
    }

    @Override
    public boolean isRaidProtected() {
        if (isRaidProtected == null) {
            assert metadata != null;
            isRaidProtected = metadata.get("mention_raid_protection_enabled").asBoolean();
        }
        return isRaidProtected;
    }

    @NotNull
    private List<String> getStringList(JsonNode metadata, String key) {
        List<String> list = new ArrayList<>();
        for (JsonNode node : metadata) {
            if (node.has(key)) {
                list.add(node.asText());
            }
        }
        return list;
    }
}
