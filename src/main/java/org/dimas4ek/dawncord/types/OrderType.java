package org.dimas4ek.dawncord.types;

public enum OrderType {
    LATEST_ACTIVITY(0, "Sort forum posts by activity"),
    CREATION_DATE(1, "Sort forum posts by creation time (from most recent to oldest)");

    private final int value;
    private final String description;

    OrderType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
