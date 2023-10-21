package org.dimas4ek.wrapper.entities.channel;

import org.json.JSONObject;

public class GuildCategoryImpl implements GuildCategory {
    private final JSONObject category;

    public GuildCategoryImpl(JSONObject category) {
        this.category = category;
    }

    @Override
    public String getId() {
        return category.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return category.getString("name");
    }
}
