package io.github.dawncord.api.event.handler;

import io.github.dawncord.api.event.AutoModEvent;
import io.github.dawncord.api.types.GatewayEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An event handler for auto moderation events.
 */
public class AutoModEventHandler implements EventHandler {
    private static final Map<GatewayEvent, Consumer<AutoModEvent>> eventHandlers = new HashMap<>();
    private final Rule rule = new Rule();

    private Map<GatewayEvent, Consumer<AutoModEvent>> getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Gets the auto-moderation rule.
     *
     * @return The auto-moderation rule.
     */
    public Rule rule() {
        return rule;
    }

    /**
     * Associates the action execution event with the provided handler.
     *
     * @param handler The handler for the action execution event.
     */
    public void actionExecution(Consumer<AutoModEvent> handler) {
        eventHandlers.put(GatewayEvent.AUTO_MODERATION_ACTION_EXECUTION, handler);
    }

    /**
     * A nested class representing rules for auto moderation.
     */
    public static class Rule {

        /**
         * Associates the rule creation event with the provided handler.
         *
         * @param handler The handler for the rule creation event.
         */
        public void create(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_CREATE, handler);
        }

        /**
         * Associates the rule update event with the provided handler.
         *
         * @param handler The handler for the rule update event.
         */
        public void update(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_UPDATE, handler);
        }

        /**
         * Associates the rule deletion event with the provided handler.
         *
         * @param handler The handler for the rule deletion event.
         */
        public void delete(Consumer<AutoModEvent> handler) {
            eventHandlers.put(GatewayEvent.AUTO_MODERATION_RULE_DELETE, handler);
        }
    }
}
