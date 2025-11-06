package io.github.dawncord.api.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.AbstractApplication;
import io.github.dawncord.api.entities.IApplication;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents an application integrated into a platform.
 */
public class IntegrationApplication extends AbstractApplication {
    /**
     * Constructs a new IntegrationApplication with the given JSON application data.
     *
     * @param application The JSON data representing the application.
     */
    public IntegrationApplication(JsonNode application) {
        super(application);
    }
}
