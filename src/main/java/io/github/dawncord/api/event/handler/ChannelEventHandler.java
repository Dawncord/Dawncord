package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.ChannelEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for channel events.
 */
public class ChannelEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<ChannelEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<ChannelEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the channel creation event with the provided handler.
     *
     * @param handler The handler for the channel creation event.
     */
    public void create(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_CREATE, handler);
    }

    /**
     * Associates the channel update event with the provided handler.
     *
     * @param handler The handler for the channel update event.
     */
    public void update(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_UPDATE, handler);
    }

    /**
     * Associates the channel deletion event with the provided handler.
     *
     * @param handler The handler for the channel deletion event.
     */
    public void delete(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_DELETE, handler);
    }

    /**
     * Associates the channel pins update event with the provided handler.
     *
     * @param handler The handler for the channel pins update event.
     */
    public void pinsUpdate(Consumer<ChannelEvent> handler) {
        eventHandlers.put(GatewayEvent.CHANNEL_PINS_UPDATE, handler);
    }
}

