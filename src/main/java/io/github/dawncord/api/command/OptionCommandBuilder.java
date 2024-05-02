package io.github.dawncord.api.command;

import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.OptionType;

import java.util.Collection;

/**
 * A builder interface for constructing option-based commands.
 * Implementations of this interface facilitate the creation of commands with various options.
 *
 * @see CommandBuilder
 */
public interface OptionCommandBuilder extends CommandBuilder {

    /**
     * Adds an option to the command with the specified type, name, and description.
     *
     * @param type        The type of the option.
     * @param name        The name of the option.
     * @param description The description of the option.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOption(OptionType type, String name, String description);

    /**
     * Adds an option to the command with the specified type, name, description, and requirement status.
     *
     * @param type        The type of the option.
     * @param name        The name of the option.
     * @param description The description of the option.
     * @param isRequired  Whether the option is required.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired);

    /**
     * Adds an option to the command with the specified type, name, description, requirement status, and autocomplete status.
     *
     * @param type           The type of the option.
     * @param name           The name of the option.
     * @param description    The description of the option.
     * @param isRequired     Whether the option is required.
     * @param isAutocomplete Whether the option supports autocomplete.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete);

    /**
     * Adds the provided option to the command.
     *
     * @param option The option to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOption(Option option);

    /**
     * Adds multiple options to the command.
     *
     * @param options The options to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOptions(Option... options);

    /**
     * Adds a collection of options to the command.
     *
     * @param options The collection of options to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    OptionCommandBuilder addOptions(Collection<Option> options);
}
