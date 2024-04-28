package org.dimas4ek.dawncord.types;

public enum AutoModActionType {
    BLOCK_MESSAGE(1, "blocks a member's message and prevents it from being posted. A custom explanation can be specified and shown to members whenever their message is blocked."),
    SEND_ALERT_MESSAGE(2, "logs user content to a specified channel"),
    TIMEOUT(3, "timeout user for a specified duration *");

    private final int value;
    private final String description;

    AutoModActionType(int value, String description) {
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
