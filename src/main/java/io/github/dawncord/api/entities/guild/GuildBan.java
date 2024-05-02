package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;

/**
 * Represents a ban on a guild member.
 */
public class GuildBan {
    private final JsonNode ban;
    private User user;
    private String reason;

    /**
     * Constructs a new GuildBan object with the specified JSON node representing the ban.
     *
     * @param ban The JSON node representing the ban.
     */
    public GuildBan(JsonNode ban) {
        this.ban = ban;
    }

    /**
     * Gets the user who was banned.
     *
     * @return The banned user.
     */
    public User getUser() {
        if (user == null) {
            user = new UserImpl(ban.get("user"));
        }
        return user;
    }

    /**
     * Gets the reason for the ban.
     *
     * @return The reason for the ban.
     */
    public String getReason() {
        if (reason == null) {
            reason = ban.get("reason").asText();
        }
        return reason;
    }
}
