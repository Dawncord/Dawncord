package io.github.dawncord.api.types;

/**
 * Represents MFA (Multi-Factor Authentication) levels.
 */
public enum MfaLevel {
    /**
     * Indicates no MFA/2FA requirement for moderation actions.
     */
    NONE(0),

    /**
     * Indicates a 2FA requirement for moderation actions.
     */
    ELEVATED(1);

    private final int value;

    MfaLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the MFA level.
     *
     * @return The value of the MFA level.
     */
    public int getValue() {
        return value;
    }
}
