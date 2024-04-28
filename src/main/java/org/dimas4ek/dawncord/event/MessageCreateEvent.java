package org.dimas4ek.dawncord.event;

import java.util.function.Consumer;

public class MessageCreateEvent {
    private final String channelId;
    private final String messageId;
    private final String guildId;

    public MessageCreateEvent(String channelId, String messageId, String guildId) {
        this.channelId = channelId;
        this.messageId = messageId;
        this.guildId = guildId;
    }

    public void then(Consumer<MessageCreateAfterEvent> reply) {
        reply.accept(new MessageCreateAfterEvent(channelId, messageId, guildId));
    }
}
