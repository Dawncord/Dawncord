package org.dimas4ek.dawncord.event.handler;

import lombok.Getter;
import org.dimas4ek.dawncord.event.IntegrationEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class IntegrationEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<IntegrationEvent>> eventHandlers = new HashMap<>();

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
