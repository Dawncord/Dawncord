package org.dimas4ek.wrapper.event.handler;

import lombok.Getter;
import org.dimas4ek.wrapper.event.ThreadEvent;
import org.dimas4ek.wrapper.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ThreadEventHandler {
    @Getter
    private static final Map<GatewayEvent, Consumer<ThreadEvent>> eventHandlers = new HashMap<>();

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
