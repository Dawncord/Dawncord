package io.github.dawncord.api.command;

import io.github.dawncord.api.types.Locale;

import java.util.Map;

/**
 * Represents a command that can be executed by the user.
 */
public interface Command {
    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    String getName();

    /**
     * Gets the description of the command.
     *
     * @return The description of the command.
     */
    String getDescription();

    /**
     * Gets the localized names of the command.
     *
     * @return A map of locales to localized names.
     */
    Map<Locale, String> getNameLocalizations();

    /**
     * Gets the localized descriptions of the command.
     *
     * @return A map of locales to localized descriptions.
     */
    Map<Locale, String> getDescriptionLocalizations();
}
