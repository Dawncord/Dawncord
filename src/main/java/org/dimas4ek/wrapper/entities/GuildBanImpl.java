package org.dimas4ek.wrapper.entities;

import org.json.JSONObject;

public class GuildBanImpl implements GuildBan{
    private final JSONObject ban;

    public GuildBanImpl(JSONObject ban) {
        this.ban = ban;
    }

    @Override
    public User getUser() {
        return new UserImpl(ban.getJSONObject("user"));
    }

    @Override
    public String getReason() {
        return ban.getString("reason");
    }
}
