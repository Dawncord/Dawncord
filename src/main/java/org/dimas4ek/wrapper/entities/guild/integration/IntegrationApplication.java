package org.dimas4ek.wrapper.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.application.IApplication;
import org.dimas4ek.wrapper.entities.image.ApplicationIcon;

public class IntegrationApplication implements IApplication {
    private final JsonNode application;
    private String id;
    private String name;
    private String description;
    private ApplicationIcon icon;
    private User bot;

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
