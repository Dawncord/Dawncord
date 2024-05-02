package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;

/**
 * Represents an option for a select menu.
 */
public class SelectOption {
    private final String label;
    private final String value;
    private String description;
    private Emoji emoji;
    private boolean isDefault;

    /**
     * Constructs a SelectOption object with the specified label and value.
     *
     * @param label The label of the option.
     * @param value The value of the option.
     */
    public SelectOption(String label, String value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Sets the description of the option.
     *
     * @param description The description of the option.
     * @return The SelectOption object.
     */
    public SelectOption setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the emoji of the option.
     *
     * @param emoji The emoji of the option.
     * @return The SelectOption object.
     */
    public SelectOption withEmoji(Emoji emoji) {
        this.emoji = emoji;
        return this;
    }

    /**
     * Sets the emoji of the option by its name.
     *
     * @param name The name of the emoji.
     * @return The SelectOption object.
     */
    public SelectOption withEmoji(String name) {
        this.emoji = new DefaultEmoji(name);
        return this;
    }

    /**
     * Sets whether the option is default.
     *
     * @param isDefault Whether the option is default.
     */
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Retrieves the label of the option.
     *
     * @return The label of the option.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Retrieves the value of the option.
     *
     * @return The value of the option.
     */
    public String getValue() {
        return value;
    }

    /**
     * Retrieves the description of the option.
     *
     * @return The description of the option.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the emoji of the option.
     *
     * @return The emoji of the option.
     */
    public Emoji getEmoji() {
        return emoji;
    }

    /**
     * Checks if the option is default.
     *
     * @return True if the option is default, otherwise false.
     */
    public boolean isDefault() {
        return isDefault;
    }
}
