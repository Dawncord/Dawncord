package org.dimas4ek.dawncord.types;

public enum StagePrivacyLevel {
    GUILD_ONLY(2);

    private final int value;

    StagePrivacyLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
