package org.dimas4ek.dawncord.types;

public enum AllowedMention {
    ROLE("roles", "Controls role mentions"),
    USER("users", "Controls user mentions"),
    EVERYONE("everyone", "Controls @everyone and @here mentions"),
    EMPTY(null, "Suppress all mentions");

    private final String value;
    private final String description;

    AllowedMention(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
