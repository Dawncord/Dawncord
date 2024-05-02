package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.ThreadEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for auto moderation events.
 */
public class ThreadEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<ThreadEvent>> eventHandlers = new HashMap<>();

    private Map<GatewayEvent, Consumer<ThreadEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Associates the thread creation event with the provided handler.
     *
     * @param handler The handler for the thread creation event.
     */
    public void create(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_CREATE, handler);
    }

    /**
     * Associates the thread update event with the provided handler.
     *
     * @param handler The handler for the thread update event.
     */
    public void update(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_UPDATE, handler);
    }

    /**
     * Associates the thread deletion event with the provided handler.
     *
     * @param handler The handler for the thread deletion event.
     */
    public void delete(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_DELETE, handler);
    }

    /**
     * Associates the thread list synchronization event with the provided handler.
     *
     * @param handler The handler for the thread list synchronization event.
     */
    public void listSync(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_LIST_SYNC, handler);
    }

    /**
     * Associates the thread member update event with the provided handler.
     *
     * @param handler The handler for the thread member update event.
     */
    public void memberUpdate(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_MEMBER_UPDATE, handler);
    }

    /**
     * Associates the thread members update event with the provided handler.
     *
     * @param handler The handler for the thread members update event.
     */
    public void membersUpdate(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_MEMBERS_UPDATE, handler);
    }
}
