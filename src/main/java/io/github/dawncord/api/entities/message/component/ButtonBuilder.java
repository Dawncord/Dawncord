package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.types.ButtonStyle;

/**
 * Builder class for creating buttons with various styles and configurations.
 */
public class ButtonBuilder implements ComponentBuilder {
    private final int style;
    private final String customId;
    private final String url;
    private final String label;
    private boolean isDisabled;
    private Emoji emoji;

    /**
     * Constructs a ButtonBuilder with the specified style, custom ID or URL, and label.
     *
     * @param style         The style of the button (e.g., primary, secondary).
     * @param customIdOrUrl The custom ID or URL of the button, depending on the button style.
     * @param label         The label text of the button.
     */
    public ButtonBuilder(int style, String customIdOrUrl, String label) {
        this.style = style;
        this.customId = style != ButtonStyle.Link.getValue() ? customIdOrUrl : null;
        this.url = style == ButtonStyle.Link.getValue() ? customIdOrUrl : null;
        this.label = label;
    }

    @Override
    public int getType() {
        return 2;
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
    public int getStyle() {
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
        return isDisabled;
    }

    /**
     * Gets the emoji associated with the button.
     *
     * @return The emoji associated with the button.
     */
    public Emoji getEmoji() {
        return emoji;
    }

    /**
     * Sets the emoji for the button.
     *
     * @param emoji The emoji to set.
     * @return This ButtonBuilder instance for method chaining.
     */
    public ButtonBuilder withEmoji(Emoji emoji) {
        this.emoji = emoji;
        return this;
    }

    /**
     * Sets the emoji for the button by its name.
     *
     * @param name The name of the emoji.
     * @return This ButtonBuilder instance for method chaining.
     */
    public ButtonBuilder withEmoji(String name) {
        this.emoji = new DefaultEmoji(name);
        return this;
    }

    /**
     * Enables the button.
     *
     * @return This ButtonBuilder instance for method chaining.
     */
    public ButtonBuilder enabled() {
        this.isDisabled = false;
        return this;
    }

    /**
     * Disables the button.
     *
     * @return This ButtonBuilder instance for method chaining.
     */
    public ButtonBuilder disabled() {
        this.isDisabled = true;
        return this;
    }
}
