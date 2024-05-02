package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.guild.Guild;

import java.util.List;

/**
 * Represents the ready event sent by the Discord gateway when the bot is ready to start receiving events.
 */
public class ReadyEvent {
    private final String version;
    private final User bot;
    private final String sessionType;
    private final String sessionId;
    private final String resumeUrl;
    private final List<Guild> guilds;
    private final Application application;

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
    public ReadyEvent(String version, User bot, String sessionType, String sessionId, String resumeUrl, List<Guild> guilds, Application application) {
        this.version = version;
        this.bot = bot;
        this.sessionType = sessionType;
        this.sessionId = sessionId;
        this.resumeUrl = resumeUrl;
        this.guilds = guilds;
        this.application = application;
    }

    /**
     * Retrieves the version of the Discord gateway protocol.
     *
     * @return The version of the Discord gateway protocol.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Retrieves the user object representing the bot itself.
     *
     * @return The user object representing the bot itself.
     */
    public User getBot() {
        return bot;
    }

    /**
     * Retrieves the type of session established with the Discord gateway.
     *
     * @return The type of session established with the Discord gateway.
     */
    public String getSessionType() {
        return sessionType;
    }

    /**
     * Retrieves the unique identifier for the current session.
     *
     * @return The unique identifier for the current session.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Retrieves the URL to use for resuming a session.
     *
     * @return The URL to use for resuming a session.
     */
    public String getResumeUrl() {
        return resumeUrl;
    }

    /**
     * Retrieves the list of guilds the bot is a member of.
     *
     * @return The list of guilds the bot is a member of.
     */
    public List<Guild> getGuilds() {
        return guilds;
    }

    /**
     * Retrieves the application information for the bot.
     *
     * @return The application information for the bot.
     */
    public Application getApplication() {
        return application;
    }
}
