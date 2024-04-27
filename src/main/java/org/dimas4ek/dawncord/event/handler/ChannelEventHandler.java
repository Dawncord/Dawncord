package org.dimas4ek.dawncord.event.handler;

import lombok.Getter;
import org.dimas4ek.dawncord.event.ChannelEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ChannelEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<ChannelEvent>> eventHandlers = new HashMap<>();

    public void create(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_CREATE, handler);
    }

    public void update(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_UPDATE, handler);
    }

    public void delete(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_DELETE, handler);
    }

    public void pinsUpdate(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_PINS_UPDATE, handler);
    }
}
