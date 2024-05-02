package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.MessageEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for message events.
 */
public class MessageEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<MessageEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<MessageEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the message creation event with the provided handler.
     *
     * @param handler The handler for the message creation event.
     */
    public void create(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_CREATE, handler);
    }

    /**
     * Associates the message update event with the provided handler.
     *
     * @param handler The handler for the message update event.
     */
    public void update(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_UPDATE, handler);
    }

    /**
     * Associates the message deletion event with the provided handler.
     *
     * @param handler The handler for the message deletion event.
     */
    public void delete(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_DELETE, handler);
    }

    /**
     * Associates the message bulk deletion event with the provided handler.
     *
     * @param handler The handler for the message bulk deletion event.
     */
    public void deleteBulk(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_DELETE_BULK, handler);
    }

    /**
     * Associates the reaction addition event with the provided handler.
     *
     * @param handler The handler for the reaction addition event.
     */
    public void reactionAdd(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_ADD, handler);
    }

    /**
     * Associates the reaction removal event with the provided handler.
     *
     * @param handler The handler for the reaction removal event.
     */
    public void reactionRemove(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE, handler);
    }

    /**
     * Associates the removal of all reactions event with the provided handler.
     *
     * @param handler The handler for the removal of all reactions event.
     */
    public void reactionRemoveAll(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE_ALL, handler);
    }

    /**
     * Associates the removal of reactions by emoji event with the provided handler.
     *
     * @param handler The handler for the removal of reactions by emoji event.
     */
    public void reactionRemoveEmoji(Consumer<MessageEvent> handler) {
        eventHandlers.put(GatewayEvent.MESSAGE_REACTION_REMOVE_EMOJI, handler);
    }
}
