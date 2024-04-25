package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.action.MessageModifyAction;

import java.util.function.Consumer;

public interface MessageComponentEvent extends ReplyEvent {
    void edit(Consumer<MessageModifyAction> handler);
}
