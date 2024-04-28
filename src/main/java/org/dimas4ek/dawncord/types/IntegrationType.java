package org.dimas4ek.dawncord.types;

public enum IntegrationType {
    TWITCH("twitch"),
    DISCORD("discord"),
    YOUTUBE("youtube"),
    GUILD_SUBSCRIPTION("guild_subscription");

    private final String value;

    IntegrationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
