package org.dimas4ek.dawncord.event.handler;

import org.dimas4ek.dawncord.event.IntegrationEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class IntegrationEventHandler implements EventHandler<IntegrationEvent> {
    private static final Map<GatewayEvent, Consumer<IntegrationEvent>> eventHandlers = new HashMap<>();

    @Override
    public Map<GatewayEvent, Consumer<IntegrationEvent>> getEventHandlers() {
        return eventHandlers;
    }

    public void create(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_CREATE, handler);
    }

    public void update(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_UPDATE, handler);
    }

    public void delete(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_DELETE, handler);
    }
}
