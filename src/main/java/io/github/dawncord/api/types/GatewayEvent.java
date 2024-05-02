package io.github.dawncord.api.types;

/**
 * Represents various events received from the Discord Gateway.
 */
public enum GatewayEvent {
    /**
     * Defines the heartbeat interval.
     */
    HELLO(),

    /**
     * Contains the initial state information.
     */
    READY(),

    /**
     * Response to Resume.
     */
    RESUMED(),

    /**
     * Server is going away, a client should reconnect to gateway and resume.
     */
    RECONNECT(),

    /**
     * Failure response to Identify or Resume or invalid active session.
     */
    INVALID_SESSION(),

    /**
     * Application command permission was updated.
     */
    APPLICATION_COMMAND_PERMISSIONS_UPDATE(),

    /**
     * Auto Moderation rule was created.
     */
    AUTO_MODERATION_RULE_CREATE(),

    /**
     * Auto Moderation rule was updated.
     */
    AUTO_MODERATION_RULE_UPDATE(),

    /**
     * Auto Moderation rule was deleted.
     */
    AUTO_MODERATION_RULE_DELETE(),

    /**
     * Auto Moderation rule was triggered, and an action was executed (e.g., a message was blocked).
     */
    AUTO_MODERATION_ACTION_EXECUTION(),

    /**
     * New guild channel created.
     */
    CHANNEL_CREATE(),

    /**
     * Channel was updated.
     */
    CHANNEL_UPDATE(),

    /**
     * Channel was deleted.
     */
    CHANNEL_DELETE(),

    /**
     * Message was pinned or unpinned.
     */
    CHANNEL_PINS_UPDATE(),

    /**
     * Thread created, also sent when being added to a private thread.
     */
    THREAD_CREATE(),

    /**
     * Thread was updated.
     */
    THREAD_UPDATE(),

    /**
     * Thread was deleted.
     */
    THREAD_DELETE(),

    /**
     * Sent when gaining access to a channel, contains all active threads in that channel.
     */
    THREAD_LIST_SYNC(),

    /**
     * Thread member for the current user was updated.
     */
    THREAD_MEMBER_UPDATE(),

    /**
     * Some user(s) were added to or removed from a thread.
     */
    THREAD_MEMBERS_UPDATE(),

    /**
     * Entitlement was created.
     */
    ENTITLEMENT_CREATE(),

    /**
     * Entitlement was updated or renewed.
     */
    ENTITLEMENT_UPDATE(),

    /**
     * Entitlement was deleted.
     */
    ENTITLEMENT_DELETE(),

    /**
     * Lazy-load for unavailable guild, guild became available, or user joined a new guild.
     */
    GUILD_CREATE(),

    /**
     * Guild was updated.
     */
    GUILD_UPDATE(),

    /**
     * Guild became unavailable, or user left/was removed from the guild.
     */
    GUILD_DELETE(),

    /**
     * A guild audit log entry was created.
     */
    GUILD_AUDIT_LOG_ENTRY_CREATE(),

    /**
     * User was banned from a guild.
     */
    GUILD_BAN_ADD(),

    /**
     * User was unbanned from a guild.
     */
    GUILD_BAN_REMOVE(),

    /**
     * Guild emojis were updated.
     */
    GUILD_EMOJIS_UPDATE(),

    /**
     * Guild stickers were updated.
     */
    GUILD_STICKERS_UPDATE(),

    /**
     * Guild integration was updated.
     */
    GUILD_INTEGRATIONS_UPDATE(),

    /**
     * New user joined a guild.
     */
    GUILD_MEMBER_ADD(),

    /**
     * User was removed from a guild.
     */
    GUILD_MEMBER_REMOVE(),

    /**
     * Guild member was updated.
     */
    GUILD_MEMBER_UPDATE(),

    /**
     * Response to Request Guild Members.
     */
    GUILD_MEMBERS_CHUNK(),

    /**
     * Guild role was created.
     */
    GUILD_ROLE_CREATE(),

    /**
     * Guild role was updated.
     */
    GUILD_ROLE_UPDATE(),

    /**
     * Guild role was deleted.
     */
    GUILD_ROLE_DELETE(),

    /**
     * Guild scheduled event was created.
     */
    GUILD_SCHEDULED_EVENT_CREATE(),

    /**
     * Guild scheduled event was updated.
     */
    GUILD_SCHEDULED_EVENT_UPDATE(),

    /**
     * Guild scheduled event was deleted.
     */
    GUILD_SCHEDULED_EVENT_DELETE(),

    /**
     * User subscribed to a guild scheduled event.
     */
    GUILD_SCHEDULED_EVENT_USER_ADD(),

    /**
     * User unsubscribed from a guild scheduled event.
     */
    GUILD_SCHEDULED_EVENT_USER_REMOVE(),

    /**
     * Guild integration was created.
     */
    INTEGRATION_CREATE(),

    /**
     * Guild integration was updated.
     */
    INTEGRATION_UPDATE(),

    /**
     * Guild integration was deleted.
     */
    INTEGRATION_DELETE(),

    /**
     * User used an interaction, such as an Application Command.
     */
    INTERACTION_CREATE(),

    /**
     * Invite to a channel was created.
     */
    INVITE_CREATE(),

    /**
     * Invite to a channel was deleted.
     */
    INVITE_DELETE(),

    /**
     * Message was created.
     */
    MESSAGE_CREATE(),

    /**
     * Message was edited.
     */
    MESSAGE_UPDATE(),

    /**
     * Message was deleted.
     */
    MESSAGE_DELETE(),

    /**
     * Multiple messages were deleted at once.
     */
    MESSAGE_DELETE_BULK(),

    /**
     * User reacted to a message.
     */
    MESSAGE_REACTION_ADD(),

    /**
     * User removed a reaction from a message.
     */
    MESSAGE_REACTION_REMOVE(),

    /**
     * All reactions were explicitly removed from a message.
     */
    MESSAGE_REACTION_REMOVE_ALL(),

    /**
     * All reactions for a given emoji were explicitly removed from a message.
     */
    MESSAGE_REACTION_REMOVE_EMOJI(),

    /**
     * User was updated.
     */
    PRESENCE_UPDATE(),

    /**
     * Stage instance was created.
     */
    STAGE_INSTANCE_CREATE(),

    /**
     * Stage instance was updated.
     */
    STAGE_INSTANCE_UPDATE(),

    /**
     * Stage instance was deleted or closed.
     */
    STAGE_INSTANCE_DELETE(),

    /**
     * User started typing in a channel.
     */
    TYPING_START(),

    /**
     * Properties about the user changed.
     */
    USER_UPDATE(),

    /**
     * Someone joined, left, or moved a voice channel.
     */
    VOICE_STATE_UPDATE(),

    /**
     * Guild's voice server was updated.
     */
    VOICE_SERVER_UPDATE(),

    /**
     * Guild channel webhook was created, update, or deleted.
     */
    WEBHOOKS_UPDATE()
}