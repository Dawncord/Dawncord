package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.application.Application;
import org.dimas4ek.wrapper.entities.guild.Guild;

import java.util.List;

public class ReadyEvent {
    private final String version;
    private final User bot;
    private final String sessionType;
    private final String sessionId;
    private final String resumeUrl;
    private final List<Guild> guilds;
    private final Application application;

    public ReadyEvent(String version, User bot, String sessionType, String sessionId, String resumeUrl, List<Guild> guilds, Application application) {
        this.version = version;
        this.bot = bot;
        this.sessionType = sessionType;
        this.sessionId = sessionId;
        this.resumeUrl = resumeUrl;
        this.guilds = guilds;
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    public User getBot() {
        return bot;
    }

    public String getSessionType() {
        return sessionType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public Application getApplication() {
        return application;
    }
}
