package org.dimas4ek.wrapper.event.handler;

import lombok.Getter;
import org.dimas4ek.wrapper.event.MessageEvent;
import org.dimas4ek.wrapper.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MessageEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<MessageEvent>> eventHandlers = new HashMap<>();

    public void create(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_CREATE, handler);
    }

    public void update(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_UPDATE, handler);
    }

    public void delete(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_DELETE, handler);
    }

    public void deleteBulk(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_DELETE_BULK, handler);
    }

    public void reactionAdd(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_ADD, handler);
    }

    public void reactionRemove(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE, handler);
    }

    public void reactionRemoveAll(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE_ALL, handler);
    }

    public void reactionRemoveEmoji(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE_EMOJI, handler);
    }
}
