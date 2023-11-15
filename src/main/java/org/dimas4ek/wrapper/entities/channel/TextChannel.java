package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageCreateAction;

import java.util.function.Consumer;

public interface TextChannel extends MessageChannel {
    void sendMessage(String message, Consumer<MessageCreateAction> handler);

    void sendMessage(String message);

    GuildCategory getCategory();
}
