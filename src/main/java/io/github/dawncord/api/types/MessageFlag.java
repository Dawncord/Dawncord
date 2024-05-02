package io.github.dawncord.api.types;

/**
 * Enumerates message flags.
 */
public enum MessageFlag {
    /**
     * Message has been published to subscribed channels (via Channel Following).
     */
    ROSSPOSTED(1 << 0),

    /**
     * Message originated from a message in another channel (via Channel Following).
     */
    IS_CROSSPOST(1 << 1),

    /**
     * No embeds should be included when serializing this message.
     */
    SUPPRESS_EMBEDS(1 << 2),

    /**
     * The source message for this crosspost has been deleted (via Channel Following).
     */
    SOURCE_MESSAGE_DELETED(1 << 3),

    /**
     * Message came from the urgent message system.
     */
    URGENT(1 << 4),

    /**
     * Message has an associated thread, with the same id as the message.
     */
    HAS_THREAD(1 << 5),

    /**
     * Message is only visible to the user who invoked the Interaction.
     */
    EPHEMERAL(1 << 6),

    /**
     * Message is an Interaction Response, and the bot is 'thinking'.
     */
    LOADING(1 << 7),

    /**
     * Message failed to mention some roles and add their members to the thread.
     */
    FAILED_TO_MENTION_SOME_ROLES_IN_THREAD(1 << 8),

    /**
     * Message will not trigger push and desktop notifications.
     */
    SUPPRESS_NOTIFICATIONS(1 << 12),

    /**
     * Message is a voice message.
     */
    IS_VOICE_MESSAGE(1 << 13);

    private final int value;

    MessageFlag(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the message flag.
     *
     * @return The value of the message flag.
     */
    public int getValue() {
        return value;
    }
}
