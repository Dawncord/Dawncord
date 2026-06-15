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
    MENTION_SPAM(5),

    /**
     * Checks if a member profile contains words from a user-defined list of keywords. Maximum allowed per guild is 1.
     */
    MEMBER_PROFILE(6);

    private final int value;

    AutoModTriggerType(int value) {
        this.value = value;
    }

    /**
     * Returns the {@link AutoModTriggerType} corresponding to the given integer value,
     * or {@code null} if no match is found.
     *
     * @param value The integer value of the trigger type.
     * @return The matching {@link AutoModTriggerType}, or {@code null}.
     */
    public static AutoModTriggerType fromValue(int value) {
        for (AutoModTriggerType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
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
