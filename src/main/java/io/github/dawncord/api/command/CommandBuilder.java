package io.github.dawncord.api.command;

import io.github.dawncord.api.types.Locale;

import java.util.Map;

/**
 * A builder class for creating {@link Command} instances.
 *
 * <p>This class provides a fluent API for setting the properties of a {@link Command} instance.
 * The {@link #build()} method is used to create an immutable {@link Command} instance.
 */
public interface CommandBuilder {

    /**
     * Sets the name of the command.
     *
     * @param name the name of the command
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setName(String name);

    /**
     * Sets the description of the command.
     *
     * @param description the description of the command
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setDescription(String description);

    /**
     * Sets the localized name of the command for a specific locale.
     *
     * @param locale the locale for which to set the localized name
     * @param name   the localized name of the command
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setLocalizedName(Locale locale, String name);

    /**
     * Sets the localized names of the command for multiple locales.
     *
     * @param localizedNames a map of locales to their corresponding localized names
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setLocalizedNames(Map<Locale, String> localizedNames);

    /**
     * Sets the localized description of the command for a specific locale.
     *
     * @param locale      the locale for which to set the localized description
     * @param description the localized description of the command
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setLocalizedDescription(Locale locale, String description);

    /**
     * Sets the localized descriptions of the command for multiple locales.
     *
     * @param localizedDescriptions a map of locales to their corresponding localized descriptions
     * @return this {@link CommandBuilder} instance
     */
    CommandBuilder setLocalizedDescriptions(Map<Locale, String> localizedDescriptions);

    /**
     * Builds an immutable {@link Command} instance using the properties set on this {@link CommandBuilder} instance.
     *
     * @return an immutable {@link Command} instance
     */
    Command build();
}
