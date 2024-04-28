package org.dimas4ek.dawncord.event.handler;

import org.dimas4ek.dawncord.event.AutoModEvent;
import org.dimas4ek.dawncord.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AutoModEventHandler implements EventHandler<AutoModEvent> {
    private static final Map<GatewayEvent, Consumer<AutoModEvent>> eventHandlers = new HashMap<>();
    private final Rule rule = new Rule();

    @Override
    public Map<GatewayEvent, Consumer<AutoModEvent>> getEventHandlers() {
        return eventHandlers;
    }

    public Rule rule() {
        return rule;
    }

    public void actionExecution(Consumer<AutoModEvent> handler) {
        eventHandlers.put(GatewayEvent.AUTO_MODERATION_ACTION_EXECUTION, handler);
    }

    public static class Rule {
        public void create(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_CREATE, handler);
        }

        public void update(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_UPDATE, handler);
        }

        public void delete(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_DELETE, handler);
        }
    }
}
