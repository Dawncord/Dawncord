package io.github.dawncord.api.utils;

import io.github.dawncord.api.types.GatewayEvent;

/**
 * Utility class for determining the type of Discord gateway events.
 */
public class Events {

    /**
     * Checks if the event is a READY event.
     *
     * @param type The gateway event type.
     * @return True if the event is a READY event, false otherwise.
     */
    public static boolean isReadyEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.READY);
    }

    /**
     * Checks if the event is a RESUMED event.
     *
     * @param type The gateway event type.
     * @return True if the event is a RESUMED event, false otherwise.
     */
    public static boolean isResumedEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.RESUMED);
    }

    /**
     * Checks if the event is a RECONNECT event.
     *
     * @param type The gateway event type.
     * @return True if the event is a RECONNECT event, false otherwise.
     */
    public static boolean isReconnectEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.RECONNECT);
    }

    /**
     * Checks if the event is an INVALID_SESSION event.
     *
     * @param type The gateway event type.
     * @return True if the event is an INVALID_SESSION event, false otherwise.
     */
    public static boolean isInvalidSessionEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.INVALID_SESSION);
    }

    /**
     * Checks if the event is an APPLICATION_COMMAND_PERMISSIONS_UPDATE event.
     *
     * @param type The gateway event type.
     * @return True if the event is an APPLICATION_COMMAND_PERMISSIONS_UPDATE event, false otherwise.
     */
    public static boolean isApplicationEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.APPLICATION_COMMAND_PERMISSIONS_UPDATE);
    }

    /**
     * Checks if the event is an AUTO_MODERATION event.
     *
     * @param type The gateway event type.
     * @return True if the event is an AUTO_MODERATION event, false otherwise.
     */
    public static boolean isAutoModEvent(GatewayEvent type) {
        return type.toString().matches("^AUTO_MODERATION_.*");
    }

    /**
     * Checks if the event is a CHANNEL event.
     *
     * @param type The gateway event type.
     * @return True if the event is a CHANNEL event, false otherwise.
     */
    public static boolean isChannelEvent(GatewayEvent type) {
        return type.toString().matches("^CHANNEL_.*");
    }

    /**
     * Checks if the event is a THREAD event.
     *
     * @param type The gateway event type.
     * @return True if the event is a THREAD event, false otherwise.
     */
    public static boolean isThreadEvent(GatewayEvent type) {
        return type.toString().matches("^THREAD_.*");
    }

    /**
     * Checks if the event is an ENTITLEMENT event.
     *
     * @param type The gateway event type.
     * @return True if the event is an ENTITLEMENT event, false otherwise.
     */
    public static boolean isEntitlementEvent(GatewayEvent type) {
        return type.toString().matches("^ENTITLMENT_.*");
    }

    /**
     * Checks if the event is a GUILD event.
     *
     * @param type The gateway event type.
     * @return True if the event is a GUILD event, false otherwise.
     */
    public static boolean isGuildEvent(GatewayEvent type) {
        return type.toString().matches("^GUILD_.*");
    }

    /**
     * Checks if the event is an INTEGRATION event.
     *
     * @param type The gateway event type.
     * @return True if the event is an INTEGRATION event, false otherwise.
     */
    public static boolean isIntegrationEvent(GatewayEvent type) {
        return type.toString().matches("^INTEGRATION_.*");
    }

    /**
     * Checks if the event is an INTERACTION_CREATE event.
     *
     * @param type The gateway event type.
     * @return True if the event is an INTERACTION_CREATE event, false otherwise.
     */
    public static boolean isInteractionEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.INTERACTION_CREATE);
    }

    /**
     * Checks if the event is an INVITE event.
     *
     * @param type The gateway event type.
     * @return True if the event is an INVITE event, false otherwise.
     */
    public static boolean isInviteEvent(GatewayEvent type) {
        return type.toString().matches("^INVITE_.*");
    }

    /**
     * Checks if the event is a MESSAGE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a MESSAGE event, false otherwise.
     */
    public static boolean isMessageEvent(GatewayEvent type) {
        return type.toString().matches("^MESSAGE_.*");
    }

    /**
     * Checks if the event is a PRESENCE_UPDATE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a PRESENCE_UPDATE event, false otherwise.
     */
    public static boolean isPresenceEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.PRESENCE_UPDATE);
    }

    /**
     * Checks if the event is a STAGE_INSTANCE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a STAGE_INSTANCE event, false otherwise.
     */
    public static boolean isStageInstanceEvent(GatewayEvent type) {
        return type.toString().matches("^STAGE_INSTANCE_.*");
    }

    /**
     * Checks if the event is a TYPING_START event.
     *
     * @param type The gateway event type.
     * @return True if the event is a TYPING_START event, false otherwise.
     */
    public static boolean isTypingEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.TYPING_START);
    }

    /**
     * Checks if the event is a USER_UPDATE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a USER_UPDATE event, false otherwise.
     */
    public static boolean isBotUpdateEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.USER_UPDATE);
    }

    /**
     * Checks if the event is a VOICE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a VOICE event, false otherwise.
     */
    public static boolean isVoiceEvent(GatewayEvent type) {
        return type.toString().matches("^VOICE_.*");
    }

    /**
     * Checks if the event is a WEBHOOKS_UPDATE event.
     *
     * @param type The gateway event type.
     * @return True if the event is a WEBHOOKS_UPDATE event, false otherwise.
     */
    public static boolean isWebhookEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.WEBHOOKS_UPDATE);
    }
}
