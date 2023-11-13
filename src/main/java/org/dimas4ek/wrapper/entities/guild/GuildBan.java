package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.json.JSONObject;

public class GuildBan {
    private final JSONObject ban;

    public GuildBan(JSONObject ban) {
        this.ban = ban;
    }

    public User getUser() {
        return new UserImpl(ban.getJSONObject("user"));
    }

    public String getReason() {
        return ban.getString("reason");
    }
}
