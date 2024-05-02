package io.github.dawncord.api.types;

/**
 * Represents various flags indicating different activities or events.
 */
public enum ActivityFlag {
    /**
     * Instance.
     */
    INSTANCE(1 << 0),

    /**
     * User has joined an activity.
     */
    JOIN(1 << 1),

    /**
     * User is spectating an activity.
     */
    SPECTATE(1 << 2),

    /**
     * Join request for an activity.
     */
    JOIN_REQUEST(1 << 3),

    /**
     * Synchronization of activities.
     */
    SYNC(1 << 4),

    /**
     * Playing an activity.
     */
    PLAY(1 << 5),

    /**
     * Privacy settings with friends for a party.
     */
    PARTY_PRIVACY_FRIENDS(1 << 6),

    /**
     * Privacy settings within a voice channel for a party.
     */
    PARTY_PRIVACY_VOICE_CHANNEL(1 << 7),

    /**
     * Embedded activity.
     */
    EMBEDDED(1 << 8);

    private final long value;

    ActivityFlag(long value) {
        this.value = value;
    }

    /**
     * Gets the value representing the activity flag.
     *
     * @return The value representing the activity flag.
     */
    public long getValue() {
        return value;
    }
}
