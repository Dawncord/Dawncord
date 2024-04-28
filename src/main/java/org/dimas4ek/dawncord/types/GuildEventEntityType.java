package org.dimas4ek.dawncord.types;

public enum GuildEventEntityType {
    STAGE_INSTANCE(1),
    VOICE(2),
    EXTERNAL(3);

    private final int value;

    GuildEventEntityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
