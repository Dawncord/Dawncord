package io.github.dawncord.api.types;

/**
 * Represents different types of targets.
 */
public enum TargetType {
    /**
     * Stream target.
     */
    STREAM(1),

    /**
     * Embedded application target.
     */
    EMBEDDED_APPLICATION(2);

    private final int value;

    TargetType(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value of the target type.
     *
     * @return The integer value of the target type.
     */
    public int getValue() {
        return value;
    }
}
