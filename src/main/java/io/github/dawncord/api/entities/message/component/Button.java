package io.github.dawncord.api.entities.message.component;

/**
 * Utility class for creating different types of buttons.
 */
public class Button {
    /**
     * Creates a primary button.
     *
     * @param customId The custom ID of the button.
     * @param label    The label text of the button.
     * @return A ButtonBuilder for configuring the button.
     */
    public static ButtonBuilder primary(String customId, String label) {
        return new ButtonBuilder(1, customId, label);
    }

    /**
     * Creates a secondary button.
     *
     * @param customId The custom ID of the button.
     * @param label    The label text of the button.
     * @return A ButtonBuilder for configuring the button.
     */
    public static ButtonBuilder secondary(String customId, String label) {
        return new ButtonBuilder(2, customId, label);
    }

    /**
     * Creates a success button.
     *
     * @param customId The custom ID of the button.
     * @param label    The label text of the button.
     * @return A ButtonBuilder for configuring the button.
     */
    public static ButtonBuilder success(String customId, String label) {
        return new ButtonBuilder(3, customId, label);
    }

    /**
     * Creates a danger button.
     *
     * @param customId The custom ID of the button.
     * @param label    The label text of the button.
     * @return A ButtonBuilder for configuring the button.
     */
    public static ButtonBuilder danger(String customId, String label) {
        return new ButtonBuilder(4, customId, label);
    }

    /**
     * Creates a link button.
     *
     * @param url   The URL the button should navigate to when clicked.
     * @param label The label text of the button.
     * @return A ButtonBuilder for configuring the button.
     */
    public static ButtonBuilder link(String url, String label) {
        return new ButtonBuilder(5, url, label);
    }
}
