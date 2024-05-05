package io.github.dawncord.api.types;

/**
 * Represents online statuses.
 */
public enum OnlineStatus {
    /**
     * Offline status.
     */
    OFFLINE("offline"),

    /**
     * Do Not Disturb status.
     */
    DND("dnd"),

    /**
     * Idle status.
     */
    IDLE("idle"),

    /**
     * Online status.
     */
    ONLINE(null);

    private final String value;

    OnlineStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
