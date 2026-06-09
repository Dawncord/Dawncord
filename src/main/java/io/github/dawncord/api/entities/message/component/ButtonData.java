package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.types.ButtonStyle;

/**
 * Represents the data of a button component.
 */
public record ButtonData(String customId, String url, ButtonStyle style, String label, boolean disabled, Emoji emoji) {
    /**
     * Constructs a ButtonData object with the specified properties.
     *
     * @param customId The custom ID of the button.
     * @param url      The URL of the button (for link-style buttons).
     * @param style    The style of the button (e.g., primary, secondary).
     * @param label    The label text of the button.
     * @param disabled A boolean indicating whether the button is disabled.
     * @param emoji    The emoji associated with the button.
     */
    public ButtonData {
    }

    /**
     * Gets the custom ID of the button.
     *
     * @return The custom ID of the button.
     */
    @Override
    public String customId() {
        return customId;
    }

    /**
     * Gets the URL of the button.
     *
     * @return The URL of the button.
     */
    @Override
    public String url() {
        return url;
    }

    /**
     * Gets the style of the button.
     *
     * @return The style of the button.
     */
    @Override
    public ButtonStyle style() {
        return style;
    }

    /**
     * Gets the label text of the button.
     *
     * @return The label text of the button.
     */
    @Override
    public String label() {
        return label;
    }

    /**
     * Checks if the button is disabled.
     *
     * @return true if the button is disabled, false otherwise.
     */
    @Override
    public boolean disabled() {
        return disabled;
    }

    /**
     * Gets the emoji associated with the button.
     *
     * @return The emoji associated with the button.
     */
    @Override
    public Emoji emoji() {
        return emoji;
    }
}
