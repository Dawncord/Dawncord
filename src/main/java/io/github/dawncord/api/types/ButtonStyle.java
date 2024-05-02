package io.github.dawncord.api.types;

/**
 * Represents the style of a button.
 */
public enum ButtonStyle {
    /**
     * Primary button style.
     */
    Primary(1),

    /**
     * Secondary button style.
     */
    Secondary(2),

    /**
     * Success button style.
     */
    Success(3),

    /**
     * Danger button style.
     */
    Danger(4),

    /**
     * Link button style.
     */
    Link(5);

    private final int value;

    ButtonStyle(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the button style.
     *
     * @return The value associated with the button style.
     */
    public int getValue() {
        return value;
    }
}
