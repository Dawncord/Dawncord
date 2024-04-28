package org.dimas4ek.dawncord.types;

public enum MfaLevel {
    NONE(0, "guild has no MFA/2FA requirement for moderation actions"),
    ELEVATED(1, "guild has a 2FA requirement for moderation actions");

    private final int value;
    private final String description;

    MfaLevel(int value, String description) {
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
