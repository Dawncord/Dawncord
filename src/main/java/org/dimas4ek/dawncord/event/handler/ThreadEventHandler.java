package org.dimas4ek.dawncord.event.handler;

import org.dimas4ek.dawncord.event.ThreadEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ThreadEventHandler implements EventHandler<ThreadEvent> {
    private static final Map<GatewayEvent, Consumer<ThreadEvent>> eventHandlers = new HashMap<>();

    @Override
    public Map<GatewayEvent, Consumer<ThreadEvent>> getEventHandlers() {
        return eventHandlers;
    }

    public void create(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_CREATE, handler);
    }

    public void update(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_UPDATE, handler);
    }

    public void delete(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_DELETE, handler);
    }

    public void listSync(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_LIST_SYNC, handler);
    }

    public void memberUpdate(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_MEMBER_UPDATE, handler);
    }

    public void membersUpdate(Consumer<ThreadEvent> handler) {
        eventHandlers.put(GatewayEvent.THREAD_MEMBERS_UPDATE, handler);
    }
}
