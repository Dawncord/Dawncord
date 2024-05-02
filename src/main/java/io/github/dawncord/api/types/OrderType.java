package io.github.dawncord.api.types;

/**
 * Represents order types.
 */
public enum OrderType {
    /**
     * Sort forum posts by latest activity.
     */
    LATEST_ACTIVITY(0),

    /**
     * Sort forum posts by creation date.
     */
    CREATION_DATE(1);

    private final int value;

    OrderType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the order type.
     *
     * @return The value of the order type.
     */
    public int getValue() {
        return value;
    }
}
