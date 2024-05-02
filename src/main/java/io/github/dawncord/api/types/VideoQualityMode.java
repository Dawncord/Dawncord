package io.github.dawncord.api.types;

/**
 * Represents the video quality modes for Discord video calls.
 */
public enum VideoQualityMode {
    /**
     * Discord chooses the quality for optimal performance.
     */
    AUTO(1),

    /**
     * 720p quality.
     */
    FULL(2);

    private final int value;

    VideoQualityMode(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value representing the video quality mode.
     *
     * @return The numerical value representing the video quality mode.
     */
    public int getValue() {
        return value;
    }
}
