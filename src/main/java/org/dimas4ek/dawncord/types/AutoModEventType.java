package org.dimas4ek.dawncord.types;

public enum AutoModEventType {
    MESSAGE_SEND(1, "when a member sends or edits a message in the guild");

    private final int value;
    private final String description;

    AutoModEventType(int value, String description) {
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
