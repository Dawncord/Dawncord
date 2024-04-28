package org.dimas4ek.dawncord.types;

public enum GuildEventPrivacyLevel {
    GUILD_ONLY(2, "the scheduled event is only accessible to guild members");

    private final int value;
    private final String description;

    GuildEventPrivacyLevel(int value, String description) {
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
