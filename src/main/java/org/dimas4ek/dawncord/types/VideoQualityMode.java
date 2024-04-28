package org.dimas4ek.dawncord.types;

public enum VideoQualityMode {
    AUTO(1, "Discord chooses the quality for optimal performance"),
    FULL(2, "720p");

    private final int value;
    private final String description;

    VideoQualityMode(int value, String description) {
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
