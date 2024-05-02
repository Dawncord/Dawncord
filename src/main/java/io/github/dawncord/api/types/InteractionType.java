package io.github.dawncord.api.types;

/**
 * Represents types of interactions.
 */
public enum InteractionType {
    /**
     * Ping.
     */
    PING(1),

    /**
     * Slash command.
     */
    APPLICATION_COMMAND(2),

    /**
     * Message component interaction.
     */
    MESSAGE_COMPONENT(3),

    /**
     * Autocomplete command interaction.
     */
    APPLICATION_COMMAND_AUTOCOMPLETE(4),

    /**
     * Modal submit interaction.
     */
    MODAL_SUBMIT(5);

    private final int value;

    InteractionType(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value representing the interaction type.
     *
     * @return The integer value.
     */
    public int getValue() {
        return value;
    }
}
