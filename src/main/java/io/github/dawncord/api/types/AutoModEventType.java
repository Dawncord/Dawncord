package io.github.dawncord.api.types;

/**
 * Represents the type of event monitored by an auto-moderation system.
 */
public enum AutoModEventType {
    /**
     * Member sends or edits a message in the guild.
     */
    MESSAGE_SEND(1);

    private final int value;

    AutoModEventType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the event type.
     *
     * @return The value associated with the event type.
     */
    public int getValue() {
        return value;
    }
}
