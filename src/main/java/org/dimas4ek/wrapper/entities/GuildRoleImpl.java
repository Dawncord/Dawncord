package org.dimas4ek.wrapper.entities;

import org.json.JSONObject;

public class GuildRoleImpl implements GuildRole{
    private final JSONObject role;

    public GuildRoleImpl(JSONObject role) {
        this.role = role;
    }

    @Override
    public String getId() {
        return role.getString("id");
    }

    @Override
    public String getName() {
        return role.getString("name");
    }
}
