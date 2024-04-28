package org.dimas4ek.dawncord.types;

public enum IntegrationExpireBehavior {
    REMOVE_ROLE(0, "Remove Role"),
    KICK(1, "Kick");

    private final int value;
    private final String description;

    IntegrationExpireBehavior(int value, String description) {
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
