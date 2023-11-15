package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.MessageModifyAction;
import org.dimas4ek.wrapper.action.ThreadCreateAction;
import org.dimas4ek.wrapper.entities.message.Message;

import java.util.List;
import java.util.function.Consumer;

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

    void startThread(String messageId, Consumer<ThreadCreateAction> handler);

    void startThread(long messageId, Consumer<ThreadCreateAction> handler);

    void startThread(Consumer<ThreadCreateAction> handler);

    void startThread();

    void modifyMessageById(String messageId, Consumer<MessageModifyAction> handler);

    void modifyMessageById(long messageId, Consumer<MessageModifyAction> handler);
}
