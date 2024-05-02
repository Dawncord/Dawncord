package io.github.dawncord.api.types;

/**
 * Represents the status of guild events.
 */
public enum GuildEventStatus {
    /**
     * Event is scheduled.
     */
    SCHEDULED(1),

    /**
     * Event is active.
     */
    ACTIVE(2),

    /**
     * Event is completed.
     */
    COMPLETED(3),

    /**
     * Event is canceled.
     */
    CANCELED(4);

    private final int value;

    GuildEventStatus(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the status.
     *
     * @return The value of the status.
     */
    public int getValue() {
        return value;
    }
}
