package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.types.KeywordPreset;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoModTriggerMetadataImpl implements AutoModTriggerMetadata {
    private final JSONObject metadata;

    public AutoModTriggerMetadataImpl(JSONObject metadata) {
        this.metadata = metadata;
    }

    @Override
    public List<String> getKeywordFilters() {
        return getStringList("keyword_filter");
    }

    @Override
    public List<KeywordPreset> getPresets() {
        JSONArray presetsArray = metadata.optJSONArray("presets");
        if (presetsArray != null) {
            return EnumUtils.getEnumList(metadata.getJSONArray("presets"), KeywordPreset.class);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getAllows() {
        return getStringList("allow_list");
    }

    @Override
    public int getMentionLimit() {
        return metadata.getInt("mention_total_limit");
    }

    @Override
    public boolean isRaidProtected() {
        return metadata.getBoolean("mention_raid_protection_enabled");
    }

    @NotNull
    private List<String> getStringList(String key) {
        JSONArray array = metadata.optJSONArray(key);
        if (array != null) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            return list;
        }
        return Collections.emptyList();
    }
}
