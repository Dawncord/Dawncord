package org.dimas4ek.dawncord.types;

public enum OnboardingMode {
    ONBOARDING_DEFAULT(0, "Counts only Default Channels towards constraints"),
    ONBOARDING_ADVANCED(1, "Counts Default Channels and Questions towards constraints");

    private final int value;
    private final String description;

    OnboardingMode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
