package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event where a user is banned from a guild.
 */
public record GuildBanEvent(Guild getGuild, User user) implements Event {
    /**
     * Constructs a GuildBanEvent with the specified guild and banned user.
     *
     * @param getGuild The guild from which the user was banned.
     * @param user     The user who was banned from the guild.
     */
    public GuildBanEvent {
    }

    /**
     * Retrieves the user who was banned from the guild.
     *
     * @return The user who was banned from the guild.
     */
    @Override
    public User user() {
        return user;
    }
}
