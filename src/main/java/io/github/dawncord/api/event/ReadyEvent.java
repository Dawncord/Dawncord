package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.guild.Guild;

import java.util.List;

/**
 * Represents the ready event sent by the Discord gateway when the bot is ready to start receiving events.
 */
public record ReadyEvent(String version, User bot, String sessionType, String sessionId, String resumeUrl,
                         List<Guild> guilds, Application application) {
    /**
     * Constructs a new ReadyEvent object.
     *
     * @param version     The version of the Discord gateway protocol.
     * @param bot         The user object representing the bot itself.
     * @param sessionType The type of session established with the Discord gateway.
     * @param sessionId   The unique identifier for the current session.
     * @param resumeUrl   The URL to use for resuming a session.
     * @param guilds      The list of guilds the bot is a member of.
     * @param application The application information for the bot.
     */
    public ReadyEvent {
    }

    /**
     * Retrieves the version of the Discord gateway protocol.
     *
     * @return The version of the Discord gateway protocol.
     */
    @Override
    public String version() {
        return version;
    }

    /**
     * Retrieves the user object representing the bot itself.
     *
     * @return The user object representing the bot itself.
     */
    @Override
    public User bot() {
        return bot;
    }

    /**
     * Retrieves the type of session established with the Discord gateway.
     *
     * @return The type of session established with the Discord gateway.
     */
    @Override
    public String sessionType() {
        return sessionType;
    }

    /**
     * Retrieves the unique identifier for the current session.
     *
     * @return The unique identifier for the current session.
     */
    @Override
    public String sessionId() {
        return sessionId;
    }

    /**
     * Retrieves the URL to use for resuming a session.
     *
     * @return The URL to use for resuming a session.
     */
    @Override
    public String resumeUrl() {
        return resumeUrl;
    }

    /**
     * Retrieves the list of guilds the bot is a member of.
     *
     * @return The list of guilds the bot is a member of.
     */
    @Override
    public List<Guild> guilds() {
        return guilds;
    }

    /**
     * Retrieves the application information for the bot.
     *
     * @return The application information for the bot.
     */
    @Override
    public Application application() {
        return application;
    }
}
