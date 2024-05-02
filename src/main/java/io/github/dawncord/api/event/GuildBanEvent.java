package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event where a user is banned from a guild.
 */
public class GuildBanEvent implements Event {
    private final Guild guild;
    private final User user;

    /**
     * Constructs a GuildBanEvent with the specified guild and banned user.
     *
     * @param guild The guild from which the user was banned.
     * @param user  The user who was banned from the guild.
     */
    public GuildBanEvent(Guild guild, User user) {
        this.guild = guild;
        this.user = user;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the user who was banned from the guild.
     *
     * @return The user who was banned from the guild.
     */
    public User getUser() {
        return user;
    }
}
