package io.github.dawncord.api.entities.message.modal;

/**
 * Represents data for an element.
 */
public record ElementData(String value, String customId) {
    /**
     * Constructs an ElementData object with the specified value and custom ID.
     *
     * @param value    The value of the element.
     * @param customId The custom ID of the element.
     */
    public ElementData {
    }

    /**
     * Gets the value of the element.
     *
     * @return The value.
     */
    @Override
    public String value() {
        return value;
    }

    /**
     * Gets the custom ID of the element.
     *
     * @return The custom ID.
     */
    @Override
    public String customId() {
        return customId;
    }
}
