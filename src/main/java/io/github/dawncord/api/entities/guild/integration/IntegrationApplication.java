package io.github.dawncord.api.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.application.IApplication;
import io.github.dawncord.api.entities.image.ApplicationIcon;

/**
 * Represents an application integrated into a platform.
 */
public class IntegrationApplication implements IApplication {
    private final JsonNode application;
    private String id;
    private String name;
    private String description;
    private ApplicationIcon icon;
    private User bot;

    /**
     * Constructs a new IntegrationApplication with the given JSON application data.
     *
     * @param application The JSON data representing the application.
     */
    public IntegrationApplication(JsonNode application) {
        this.application = application;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = application.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = application.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = application.get("description").asText();
        }
        return description;
    }

    @Override
    public ApplicationIcon getIcon() {
        if (icon == null) {
            icon = application.has("icon") && application.hasNonNull("icon")
                    ? new ApplicationIcon(getId(), application.get("icon").asText())
                    : null;
        }
        return icon;
    }

    @Override
    public User getBot() {
        if (bot == null) {
            bot = application.has("bot") ? new UserImpl(application.get("bot")) : null;
        }
        return bot;
    }
}
