package io.github.dawncord.api.command.option;

import io.github.dawncord.api.types.OptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an option for a command.
 */
public class Option {
    private final OptionType type;
    private final String name;
    private final String description;
    private boolean isRequired;
    private boolean isAutocomplete;
    private final List<Choice> choices = new ArrayList<>();

    /**
     * Creates a new Option object with the given type, name, and description.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description a brief explanation of what the option does
     */
    public Option(OptionType type, String name, String description) {
        this(type, name, description, false);
    }

    /**
     * Creates a new Option object with the given type, name, description, and whether the option is required or not.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description a brief explanation of what the option does
     * @param isRequired  whether the option is required or not
     */
    public Option(OptionType type, String name, String description, boolean isRequired) {
        this(type, name, description, isRequired, false);
    }

    /**
     * Creates a new Option object with the given type, name, description, whether the option is required or not, and whether the option supports autocomplete or not.
     *
     * @param type           the type of the option
     * @param name           the name of the option
     * @param description    a brief explanation of what the option does
     * @param isRequired     whether the option is required or not
     * @param isAutocomplete whether the option supports autocomplete or not
     */
    public Option(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isRequired = isRequired;
        this.isAutocomplete = isAutocomplete;
    }

    /**
     * Retrieves the type of the Option object.
     *
     * @return the type of the Option object
     */
    public OptionType getType() {
        return type;
    }

    /**
     * Retrieves the name of the object.
     *
     * @return the name of the object as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of the object.
     *
     * @return the description of the object as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves whether the option is required or not.
     *
     * @return whether the option is required or not
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Retrieves whether the option supports autocomplete or not.
     *
     * @return whether the option supports autocomplete or not
     */
    public boolean isAutocomplete() {
        return isAutocomplete;
    }

    /**
     * A list of choices for the option.
     *
     * @return a list of choices for the option
     */
    public List<Choice> getChoices() {
        return choices;
    }

    /**
     * Sets whether the option is required or not.
     *
     * @param isRequired whether the option is required or not
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Sets whether the option supports autocomplete or not.
     *
     * @param isAutocomplete whether the option supports autocomplete or not
     */
    public void setAutocomplete(boolean isAutocomplete) {
        this.isAutocomplete = isAutocomplete;
    }

    /**
     * Adds a choice to the list of choices for the option.
     *
     * @param name  the name of the choice
     * @param value the value of the choice
     * @return the current instance of the Option class
     */
    public Option addChoice(String name, String value) {
        choices.add(new Choice(name, value));
        return this;
    }

    /**
     * Adds a choice to the list of choices for the option.
     *
     * @param choice the choice to add
     * @return the current instance of the Option class
     */
    public Option addChoice(Choice choice) {
        choices.add(choice);
        return this;
    }

    /**
     * Adds the given choices to the list of choices for the option.
     *
     * @param choices the choices to add
     * @return the current instance of the Option class
     */
    public Option addChoices(Choice... choices) {
        Collections.addAll(this.choices, choices);
        return this;
    }

    /**
     * A choice of an option.
     */
    public static class Choice {
        private final String name;
        private final String value;

        /**
         * Creates a new Choice object with the given name and value.
         *
         * @param name  the name of the choice
         * @param value the value of the choice
         */
        public Choice(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Retrieves the name of the object.
         *
         * @return the name of the object as a string
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the name of the object.
         *
         * @return the name of the object as a string
         */
        public String getValue() {
            return value;
        }
    }
}

