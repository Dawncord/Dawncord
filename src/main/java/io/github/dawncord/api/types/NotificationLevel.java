package io.github.dawncord.api.types;

/**
 * Represents notification levels for message notifications.
 */
public enum NotificationLevel {
    /**
     * All messages notification level.
     */
    ALL_MESSAGES(0),

    /**
     * Only mentions notification level.
     */
    ONLY_MENTIONS(1);

    private final int value;

    NotificationLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the notification level.
     *
     * @return The value of the notification level.
     */
    public int getValue() {
        return value;
    }
}
