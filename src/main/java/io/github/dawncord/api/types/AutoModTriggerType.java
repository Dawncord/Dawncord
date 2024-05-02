package io.github.dawncord.api.types;

/**
 * Represents the type of trigger used in an auto-moderation system.
 */
public enum AutoModTriggerType {
    /**
     * Checks if content contains words from a user-defined list of keywords. Maximum allowed per guild is 6.
     */
    KEYWORD(1),

    /**
     * Checks if content represents generic spam. Maximum allowed per guild is 1.
     */
    SPAM(3),

    /**
     * Checks if content contains words from internal pre-defined wordsets. Maximum allowed per guild is 1.
     */
    KEYWORD_PRESET(4),

    /**
     * Checks if content contains more unique mentions than allowed. Maximum allowed per guild is 1.
     */
    MENTION_SPAM(5);

    private final int value;

    AutoModTriggerType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the trigger type.
     *
     * @return The value associated with the trigger type.
     */
    public int getValue() {
        return value;
    }
}
