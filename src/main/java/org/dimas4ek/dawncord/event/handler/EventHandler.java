package org.dimas4ek.dawncord.event.handler;

import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.Map;
import java.util.function.Consumer;

public interface EventHandler<T> {
    Map<GatewayEvent, Consumer<T>> getEventHandlers();
}
