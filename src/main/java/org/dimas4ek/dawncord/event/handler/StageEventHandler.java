package org.dimas4ek.dawncord.event.handler;

import lombok.Getter;
import org.dimas4ek.dawncord.event.StageEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class StageEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<StageEvent>> eventHandlers = new HashMap<>();

    public void create(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_CREATE, handler);
    }

    public void update(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_UPDATE, handler);
    }

    public void delete(Consumer<StageEvent> handler) {
        eventHandlers.put(GatewayEvent.STAGE_INSTANCE_DELETE, handler);
    }
}
