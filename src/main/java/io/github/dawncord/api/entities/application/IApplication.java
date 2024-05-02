package io.github.dawncord.api.entities.application;

import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.image.ApplicationIcon;

/**
 * Represents an application.
 * IApplication is an interface providing methods to access properties of an application.
 */
public interface IApplication extends ISnowflake {

    /**
     * Retrieves the name of the application.
     *
     * @return The name of the application.
     */
    String getName();

    /**
     * Retrieves the description of the application.
     *
     * @return The description of the application.
     */
    String getDescription();

    /**
     * Retrieves the icon of the application.
     *
     * @return The icon of the application.
     */
    ApplicationIcon getIcon();

    /**
     * Retrieves the bot user associated with the application.
     *
     * @return The bot user associated with the application.
     */
    User getBot();
}
