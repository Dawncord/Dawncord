package io.github.dawncord.api.types;

/**
 * Represents the type of the prompt
 */
public enum PromptType {
    /**
     * Multiple choice
     */
    MULTIPLE_CHOICE(0),
    /**
     * Dropdown
     */
    DROPDOWN(1);

    private final int value;

    PromptType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the prompt type.
     *
     * @return The value of the prompt type.
     */
    public int getValue() {
        return value;
    }
}
