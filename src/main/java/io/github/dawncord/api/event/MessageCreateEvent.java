package io.github.dawncord.api.event;

import java.util.function.Consumer;

/**
 * Represents an event for the creation of a message.
 */
public class MessageCreateEvent {
    private final String channelId;
    private final String messageId;
    private final String guildId;

    /**
     * Constructs a MessageCreateEvent with the specified channel ID, message ID, and guild ID.
     *
     * @param channelId The ID of the channel where the message was created.
     * @param messageId The ID of the created message.
     * @param guildId   The ID of the guild where the message was created.
     */
    public MessageCreateEvent(String channelId, String messageId, String guildId) {
        this.channelId = channelId;
        this.messageId = messageId;
        this.guildId = guildId;
    }

    /**
     * Accepts a consumer to handle actions after the message creation event.
     *
     * @param reply The consumer to handle actions after the message creation event.
     */
    public void then(Consumer<MessageCreateAfterEvent> reply) {
        reply.accept(new MessageCreateAfterEvent(channelId, messageId, guildId));
    }
}
