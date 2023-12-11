package org.dimas4ek.wrapper.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;

public class GuildBan {
    private final JsonNode ban;
    private User user;
    private String reason;

    public GuildBan(JsonNode ban) {
        this.ban = ban;
    }

    public User getUser() {
        if (user == null) {
            user = new UserImpl(ban.get("user"));
        }
        return user;
    }

    public String getReason() {
        if (reason == null) {
            reason = ban.get("reason").asText();
        }
        return reason;
    }
}
