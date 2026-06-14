package io.github.dawncord.api.types;

/**
 * Represents the type of action taken by an auto-moderation system.
 */
public enum AutoModActionType {
    /**
     * Blocks the message.
     */
    BLOCK_MESSAGE(1),

    /**
     * Sends an alert message.
     */
    SEND_ALERT_MESSAGE(2),

    /**
     * Applies a timeout.
     */
    TIMEOUT(3),

    /**
     * Blocks a member from interacting (sending messages, adding reactions, joining voice channels, etc.)
     * until the offending content is reviewed.
     */
    BLOCK_MEMBER_INTERACTION(4);

    private final int value;

    AutoModActionType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the action type.
     *
     * @return The value associated with the action type.
     */
    public int getValue() {
        return value;
    }
}
