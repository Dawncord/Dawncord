package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.types.ButtonStyle;

/**
 * Represents the data of a button component.
 */
public class ButtonData {
    private final String customId;
    private final String url;
    private final ButtonStyle style;
    private final String label;
    private final boolean disabled;
    private final Emoji emoji;

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
    public ButtonData(String customId, String url, ButtonStyle style, String label, boolean disabled, Emoji emoji) {
        this.customId = customId;
        this.url = url;
        this.style = style;
        this.label = label;
        this.disabled = disabled;
        this.emoji = emoji;
    }

    /**
     * Gets the custom ID of the button.
     *
     * @return The custom ID of the button.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Gets the URL of the button.
     *
     * @return The URL of the button.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the style of the button.
     *
     * @return The style of the button.
     */
    public ButtonStyle getStyle() {
        return style;
    }

    /**
     * Gets the label text of the button.
     *
     * @return The label text of the button.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Checks if the button is disabled.
     *
     * @return true if the button is disabled, false otherwise.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Gets the emoji associated with the button.
     *
     * @return The emoji associated with the button.
     */
    public Emoji getEmoji() {
        return emoji;
    }
}
