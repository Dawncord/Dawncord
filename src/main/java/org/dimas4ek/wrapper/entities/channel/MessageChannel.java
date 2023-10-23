package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.message.Message;

import java.util.List;

public interface MessageChannel extends Channel {
    List<Message> getMessages();

    Message getLastMessage();

    Message getMessageById(String messageId);

    Message getMessageById(long messageId);

    int getRateLimit();

    boolean isNsfw();
}
