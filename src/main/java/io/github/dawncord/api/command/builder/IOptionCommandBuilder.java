package io.github.dawncord.api.command.builder;

import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.OptionType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Defines builder operations for command options.
 *
 * @param <T> The concrete builder type returned by fluent setters.
 */
public interface IOptionCommandBuilder<T> {
    List<Option> getOptionList();

    /**
     * Adds an option to the command with the specified type, name, and description.
     *
     * @param type        The type of the option.
     * @param name        The name of the option.
     * @param description The description of the option.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    default T addOption(OptionType type, String name, String description) {
        getOptionList().add(new Option(type, name, description));
        return (T) this;
    }

    /**
     * Adds an option to the command with the specified type, name, description, and requirement status.
     *
     * @param type        The type of the option.
     * @param name        The name of the option.
     * @param description The description of the option.
     * @param isRequired  Whether the option is required.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    default T addOption(OptionType type, String name, String description, boolean isRequired) {
        getOptionList().add(new Option(type, name, description, isRequired));
        return (T) this;
    }

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
    default T addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        getOptionList().add(new Option(type, name, description, isRequired, isAutocomplete));
        return (T) this;
    }

    /**
     * Adds the provided option to the command.
     *
     * @param option The option to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    default T addOption(Option option) {
        getOptionList().add(option);
        return (T) this;
    }

    /**
     * Adds multiple options to the command.
     *
     * @param options The options to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    default T addOptions(Option... options) {
        Collections.addAll(getOptionList(), options);
        return (T) this;
    }

    /**
     * Adds a collection of options to the command.
     *
     * @param options The collection of options to add.
     * @return This OptionCommandBuilder instance for method chaining.
     */
    default T addOptions(Collection<Option> options) {
        getOptionList().addAll(options);
        return (T) this;
    }
}
