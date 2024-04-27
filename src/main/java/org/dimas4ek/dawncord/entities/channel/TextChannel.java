package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.action.MessageCreateAction;
import org.dimas4ek.dawncord.event.MessageCreateEvent;

import java.util.function.Consumer;

public interface TextChannel extends MessageChannel {
    MessageCreateEvent sendMessage(String message, Consumer<MessageCreateAction> handler);

    MessageCreateEvent sendMessage(String message);

    MessageCreateEvent sendMessage(Consumer<MessageCreateAction> handler);

    GuildCategory getCategory();
}
