package org.dimas4ek.dawncord.event;

import org.dimas4ek.dawncord.action.MessageModifyAction;

import java.util.function.Consumer;

public interface MessageComponentEvent extends ReplyEvent {
    CallbackEvent<MessageModifyAction> edit(Consumer<MessageModifyAction> handler);
}
