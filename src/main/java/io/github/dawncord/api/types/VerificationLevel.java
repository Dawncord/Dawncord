package io.github.dawncord.api.types;

/**
 * Represents the verification levels for a Discord server.
 */
public enum VerificationLevel {
    /**
     * No verification required.
     */
    NONE(0),

    /**
     * Must have a verified email on the account.
     */
    LOW(1),

    /**
     * Must be registered on Discord for longer than 5 minutes.
     */
    MEDIUM(2),

    /**
     * Must be a member of the server for longer than 10 minutes.
     */
    HIGH(3),

    /**
     * Must have a verified phone number.
     */
    VERY_HIGH(4);

    private final int value;

    VerificationLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value representing the verification level.
     *
     * @return The numerical value representing the verification level.
     */
    public int getValue() {
        return value;
    }
}
