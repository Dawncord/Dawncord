package org.dimas4ek.dawncord.types;

public enum NotificationLevel {
    ALL_MESSAGES(0, "members will receive notifications for all messages by default"),
    ONLY_MENTIONS(1, "members will receive notifications only for messages that @mention them by default");

    private final int value;
    private final String description;

    NotificationLevel(int value, String description) {
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
