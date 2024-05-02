package io.github.dawncord.api.types;

/**
 * Represents the type of component.
 */
public enum ComponentType {
    /**
     * An action row component.
     */
    ACTION(1),

    /**
     * A button component.
     */
    BUTTON(2),

    /**
     * A text component.
     */
    TEXT(3),

    /**
     * A text input component.
     */
    TEXT_INPUT(4),

    /**
     * A user component.
     */
    USER(5),

    /**
     * A role component.
     */
    ROLE(6),

    /**
     * A mentionable component.
     */
    MENTIONABLE(7),

    /**
     * A channel component.
     */
    CHANNEL(8);

    private final int value;

    ComponentType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the component type.
     *
     * @return The value associated with the component type.
     */
    public int getValue() {
        return value;
    }
}
