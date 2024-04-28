package org.dimas4ek.dawncord.utils;

import org.dimas4ek.dawncord.types.GatewayEvent;

public class Events {
    public static boolean isReadyEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.READY);
    }

    public static boolean isResumedEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.RESUMED);
    }

    public static boolean isReconnectEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.RECONNECT);
    }

    public static boolean isInvalidSessionEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.INVALID_SESSION);
    }

    public static boolean isApplicationEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.APPLICATION_COMMAND_PERMISSIONS_UPDATE);
    }

    public static boolean isAutoModEvent(GatewayEvent type) {
        return type.toString().matches("^AUTO_MODERATION_.*");
    }

    public static boolean isChannelEvent(GatewayEvent type) {
        return type.toString().matches("^CHANNEL_.*");
    }

    public static boolean isThreadEvent(GatewayEvent type) {
        return type.toString().matches("^THREAD_.*");
    }

    public static boolean isEntitlementEvent(GatewayEvent type) {
        return type.toString().matches("^ENTITLMENT_.*");
    }

    public static boolean isGuildEvent(GatewayEvent type) {
        return type.toString().matches("^GUILD_.*");
    }

    public static boolean isIntegrationEvent(GatewayEvent type) {
        return type.toString().matches("^INTEGRATION_.*");
    }

    public static boolean isInteractionEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.INTERACTION_CREATE);
    }

    public static boolean isInviteEvent(GatewayEvent type) {
        return type.toString().matches("^INVITE_.*");
    }

    public static boolean isMessageEvent(GatewayEvent type) {
        return type.toString().matches("^MESSAGE_.*");
    }

    public static boolean isPresenceEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.PRESENCE_UPDATE);
    }

    public static boolean isStageInstanceEvent(GatewayEvent type) {
        return type.toString().matches("^STAGE_INSTANCE_.*");
    }

    public static boolean isTypingEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.TYPING_START);
    }

    public static boolean isBotUpdateEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.USER_UPDATE);
    }

    public static boolean isVoiceEvent(GatewayEvent type) {
        return type.toString().matches("^VOICE_.*");
    }

    public static boolean isWebhookEvent(GatewayEvent type) {
        return type.equals(GatewayEvent.WEBHOOKS_UPDATE);
    }
}
