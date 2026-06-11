package io.github.dawncord.api.action.command;

import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.OptionType;

import java.util.Collections;
import java.util.List;

/**
 * Defines actions that can contain command options.
 *
 * @param <T> The concrete action type returned by fluent setters.
 */
public interface Optionable<T> {
    List<Option> getOptionList();

    T getSelf();

    void setHasChanges(boolean hasChanges);

    /**
     * Adds a new option to the list of options.
     */
    default T addOption(OptionType type, String name, String description) {
        return addOption(type, name, description, false, false);
    }

    /**
     * Adds a new option to the list of options.
     */
    default T addOption(OptionType type, String name, String description, boolean isRequired) {
        return addOption(type, name, description, isRequired, false);
    }

    /**
     * Adds a new option to the list of options.
     */
    default T addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        return addOption(new Option(type, name, description, isRequired, isAutocomplete));
    }

    /**
     * Adds an option to the list of options.
     */
    default T addOption(Option option) {
        getOptionList().add(option);
        setHasChanges(true);
        return getSelf();
    }

    /**
     * Adds the given options to the list of options.
     */
    default T addOptions(Option... options) {
        Collections.addAll(getOptionList(), options);
        setHasChanges(true);
        return getSelf();
    }

    /**
     * Adds the given options to the list of options.
     */
    default T addOptions(List<Option> options) {
        getOptionList().addAll(options);
        setHasChanges(true);
        return getSelf();
    }
}
