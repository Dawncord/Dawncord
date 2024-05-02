package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.IntegrationEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for integration events.
 */
public class IntegrationEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<IntegrationEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<IntegrationEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the integration creation event with the provided handler.
     *
     * @param handler The handler for the integration creation event.
     */
    public void create(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_CREATE, handler);
    }

    /**
     * Associates the integration update event with the provided handler.
     *
     * @param handler The handler for the integration update event.
     */
    public void update(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_UPDATE, handler);
    }

    /**
     * Associates the integration deletion event with the provided handler.
     *
     * @param handler The handler for the integration deletion event.
     */
    public void delete(Consumer<IntegrationEvent> handler) {
        eventHandlers.put(GatewayEvent.INTEGRATION_DELETE, handler);
    }
}
