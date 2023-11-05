package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.message.Message;

import java.util.List;

public interface MessageChannel extends Channel {
    List<Message> getMessages();

    Message getLastMessage();

    Message getMessageById(String messageId);

    Message getMessageById(long messageId);

    int getRateLimit();

    boolean isNsfw();

    void deleteMessages(int count);

    void setTyping();

    List<Message> getPinnedMessages();

    void pinMessage(String messageId);

    void pinMessage(long messageId);

    void unpinMessage(String messageId);

    void unpinMessage(long messageId);

    ThreadCreateAction startThread(String messageId);

    ThreadCreateAction startThread(long messageId);

    ThreadCreateAction startThread();
}
