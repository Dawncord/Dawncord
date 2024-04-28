package org.dimas4ek.dawncord.types;

public enum GuildEventStatus {
    SCHEDULED(1),
    ACTIVE(2),
    COMPLETED(3),
    CANCELED(4);

    private final int value;

    GuildEventStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
