package org.dimas4ek.dawncord.event.handler;

import org.dimas4ek.dawncord.event.InviteEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InviteEventHandler implements EventHandler<InviteEvent> {
    private static final Map<GatewayEvent, Consumer<InviteEvent>> eventHandlers = new HashMap<>();

    @Override
    public Map<GatewayEvent, Consumer<InviteEvent>> getEventHandlers() {
        return eventHandlers;
    }

    public void create(Consumer<InviteEvent> handler) {
        eventHandlers.put(GatewayEvent.INVITE_CREATE, handler);
    }

    public void delete(Consumer<InviteEvent> handler) {
        eventHandlers.put(GatewayEvent.INVITE_DELETE, handler);
    }
}
