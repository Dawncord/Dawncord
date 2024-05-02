package io.github.dawncord.api.event;

import io.github.dawncord.api.action.MessageModifyAction;

import java.util.function.Consumer;

/**
 * Represents an event related to message components, inheriting methods for replying to messages.
 */
public interface MessageComponentEvent extends ReplyEvent {

    /**
     * Edits the message component associated with the event, optionally providing a handler for further modifications.
     *
     * @param handler A handler for further modifications to the message component.
     * @return The event representing the edit action.
     */
    CallbackEvent<MessageModifyAction> edit(Consumer<MessageModifyAction> handler);
}

