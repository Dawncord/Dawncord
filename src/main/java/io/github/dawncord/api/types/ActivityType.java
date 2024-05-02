package io.github.dawncord.api.types;

/**
 * Represents different types of activities that a user can engage in.
 */
public enum ActivityType {
    /**
     * Gaming activity type.
     */
    GAME(0),

    /**
     * Streaming activity type.
     */
    STREAMING(1),

    /**
     * Listening activity type.
     */
    LISTENING(2),

    /**
     * Watching activity type.
     */
    WATCHING(3),

    /**
     * Custom activity type.
     */
    CUSTOM(4),

    /**
     * Competing activity type.
     */
    COMPETING(5);

    private final int value;

    ActivityType(int value) {
        this.value = value;
    }

    /**
     * Gets the value representing the activity type.
     *
     * @return The value representing the activity type.
     */
    public int getValue() {
        return value;
    }
}
