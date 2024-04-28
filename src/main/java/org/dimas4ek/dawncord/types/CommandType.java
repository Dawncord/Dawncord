package org.dimas4ek.dawncord.types;

public enum CommandType {
    CHAT_INPUT(1, "Slash commands; a text-based command that shows up when a user types /"),
    USER(2, "A UI-based command that shows up when you right click or tap on a user"),
    MESSAGE(3, "A UI-based command that shows up when you right click or tap on a message");

    private final int value;
    private final String description;

    CommandType(int value, String description) {
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
