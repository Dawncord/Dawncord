package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.StageEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for stage events.
 */
public class StageEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<StageEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<StageEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the stage creation event with the provided handler.
     *
     * @param handler The handler for the stage creation event.
     */
    public void create(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_CREATE, handler);
    }

    /**
     * Associates the stage update event with the provided handler.
     *
     * @param handler The handler for the stage update event.
     */
    public void update(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_UPDATE, handler);
    }

    /**
     * Associates the stage deletion event with the provided handler.
     *
     * @param handler The handler for the stage deletion event.
     */
    public void delete(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_DELETE, handler);
    }
}
