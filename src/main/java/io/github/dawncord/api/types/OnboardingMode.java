package io.github.dawncord.api.types;

/**
 * Represents onboarding modes for server setup.
 */
public enum OnboardingMode {
    /**
     * Counts only Default Channels towards constraints
     */
    ONBOARDING_DEFAULT(0),

    /**
     * Counts Default Channels and Questions towards constraints
     */
    ONBOARDING_ADVANCED(1);

    private final int value;

    OnboardingMode(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the onboarding mode.
     *
     * @return The value of the onboarding mode.
     */
    public int getValue() {
        return value;
    }
}
