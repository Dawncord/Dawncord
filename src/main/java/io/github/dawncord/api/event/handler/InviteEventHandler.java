package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.InviteEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for invite events.
 */
public class InviteEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<InviteEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<InviteEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the invite creation event with the provided handler.
     *
     * @param handler The handler for the invite creation event.
     */
    public void create(Consumer<InviteEvent> handler) {
        eventHandlers.put(GatewayEvent.INVITE_CREATE, handler);
    }

    /**
     * Associates the invite deletion event with the provided handler.
     *
     * @param handler The handler for the invite deletion event.
     */
    public void delete(Consumer<InviteEvent> handler) {
        eventHandlers.put(GatewayEvent.INVITE_DELETE, handler);
    }
}
