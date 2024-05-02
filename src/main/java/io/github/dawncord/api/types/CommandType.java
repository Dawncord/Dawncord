package io.github.dawncord.api.types;

/**
 * Represents the type of command.
 */
public enum CommandType {
    /**
     * Slash commands; a text-based command that shows up when a user types /.
     */
    CHAT_INPUT(1),

    /**
     * A UI-based command that shows up when you right-click or tap on a user.
     */
    USER(2),

    /**
     * A UI-based command that shows up when you right-click or tap on a message.
     */
    MESSAGE(3);

    private final int value;

    CommandType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the command type.
     *
     * @return The value associated with the command type.
     */
    public int getValue() {
        return value;
    }
}
