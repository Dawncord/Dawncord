package io.github.dawncord.api.entities.message.modal;

import io.github.dawncord.api.types.TextInputStyle;

/**
 * Represents an element used in modals and dialogs for input.
 */
public class Element {
    private final String label;
    private final String customId;
    private final TextInputStyle style;
    private String placeholder;
    private Boolean required;
    private Integer minLength;
    private Integer maxLength;

    /**
     * Constructs an Element with the specified label, custom ID, and input style.
     *
     * @param label    The label of the element.
     * @param customId The custom ID of the element.
     * @param style    The input style of the element.
     */
    public Element(String label, String customId, TextInputStyle style) {
        this.label = label;
        this.customId = customId;
        this.style = style;
    }

    /**
     * Sets the placeholder text for the element.
     *
     * @param placeholder The placeholder text.
     * @return The Element instance.
     */
    public Element setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    /**
     * Sets whether the element is required.
     *
     * @param required Whether the element is required.
     * @return The Element instance.
     */
    public Element setRequired(Boolean required) {
        this.required = required;
        return this;
    }

    /**
     * Sets the minimum length of input allowed for the element.
     *
     * @param minLength The minimum length.
     * @return The Element instance.
     */
    public Element setMinLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * Sets the maximum length of input allowed for the element.
     *
     * @param maxLength The maximum length.
     * @return The Element instance.
     */
    public Element setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Gets the label of the element.
     *
     * @return The label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the custom ID of the element.
     *
     * @return The custom ID.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Gets the placeholder text of the element.
     *
     * @return The placeholder text.
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Gets the input style of the element.
     *
     * @return The input style.
     */
    public TextInputStyle getStyle() {
        return style;
    }

    /**
     * Checks if the element is required.
     *
     * @return True if the element is required, false otherwise.
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Gets the minimum length of input allowed for the element.
     *
     * @return The minimum length.
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Gets the maximum length of input allowed for the element.
     *
     * @return The maximum length.
     */
    public Integer getMaxLength() {
        return maxLength;
    }
}
