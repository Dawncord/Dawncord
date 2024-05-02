package io.github.dawncord.api.types;

/**
 * Represents styles for text input.
 */
public enum TextInputStyle {
    /**
     * Single-line input style.
     */
    SHORT(1),

    /**
     * Multi-line input style.
     */
    PARAGRAPH(2);

    private final int value;

    TextInputStyle(int value) {
        this.value = value;
    }

    /**
     * Gets the value representing the text input style.
     *
     * @return The value representing the text input style.
     */
    public int getValue() {
        return value;
    }
}
