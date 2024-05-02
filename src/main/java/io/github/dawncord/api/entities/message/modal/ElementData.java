package io.github.dawncord.api.entities.message.modal;

/**
 * Represents data for an element.
 */
public class ElementData {
    private final String value;
    private final String customId;

    /**
     * Constructs an ElementData object with the specified value and custom ID.
     *
     * @param value    The value of the element.
     * @param customId The custom ID of the element.
     */
    public ElementData(String value, String customId) {
        this.value = value;
        this.customId = customId;
    }

    /**
     * Gets the value of the element.
     *
     * @return The value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the custom ID of the element.
     *
     * @return The custom ID.
     */
    public String getCustomId() {
        return customId;
    }
}
