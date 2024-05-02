package io.github.dawncord.api.entities.guild.automod;

import io.github.dawncord.api.types.KeywordPreset;

import java.util.List;

/**
 * Represents the metadata associated with an auto-mod trigger.
 */
public interface AutoModTriggerMetadata {
    /**
     * Gets the keyword filters for the trigger.
     *
     * @return The list of keyword filters.
     */
    List<String> getKeywordFilters();

    /**
     * Gets the presets for the trigger.
     *
     * @return The list of keyword presets.
     */
    List<KeywordPreset> getPresets();

    /**
     * Gets the allowed items for the trigger.
     *
     * @return The list of allowed items.
     */
    List<String> getAllows();

    /**
     * Gets the mention limit for the trigger.
     *
     * @return The mention limit.
     */
    int getMentionLimit();

    /**
     * Checks if the trigger is raid protected.
     *
     * @return true if raid protection is enabled,  false otherwise.
     */
    boolean isRaidProtected();

    /**
     * Creates an instance of AutoModTriggerMetadata with the specified parameters.
     *
     * @param keywordFilter The keyword filters.
     * @param presets       The keyword presets.
     * @param allows        The allowed items.
     * @param mentionLimit  The mention limit.
     * @param raidProtected Whether raid protection is enabled.
     * @return An instance of AutoModTriggerMetadata.
     */
    static AutoModTriggerMetadata of(List<String> keywordFilter, List<KeywordPreset> presets, List<String> allows, int mentionLimit, boolean raidProtected) {
        return new AutoModTriggerMetadataImpl(keywordFilter, presets, allows, mentionLimit, raidProtected);
    }
}
