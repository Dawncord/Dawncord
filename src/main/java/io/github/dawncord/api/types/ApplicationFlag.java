package io.github.dawncord.api.types;

/**
 * Represents flags associated with Discord applications.
 */
public enum ApplicationFlag {
    /**
     * If an app uses the Auto Moderation API.
     */
    APPLICATION_AUTO_MODERATION_RULE_CREATE_BADGE(1 << 6),

    /**
     * Required for bots in 100 or more servers to receive presence_update events.
     */
    GATEWAY_PRESENCE(1 << 12),

    /**
     * Required for bots in under 100 servers to receive presence_update events, found on the Bot page in your app's settings.
     */
    GATEWAY_PRESENCE_LIMITED(1 << 13),

    /**
     * Required for bots in 100 or more servers to receive member-related events like guild_member_add. See the list of member-related events under GUILD_MEMBERS.
     */
    GATEWAY_GUILD_MEMBERS(1 << 14),

    /**
     * Required for bots in under 100 servers to receive member-related events like guild_member_add, found on the Bot page in your app's settings. See the list of member-related events under GUILD_MEMBERS.
     */
    GATEWAY_GUILD_MEMBERS_LIMITED(1 << 15),

    /**
     * Unusual growth of an app that prevents verification.
     */
    VERIFICATION_PENDING_GUILD_LIMIT(1 << 16),

    /**
     * If an app is embedded within the Discord client (currently unavailable publicly).
     */
    EMBEDDED(1 << 17),

    /**
     * Required for bots in 100 or more servers to receive message content.
     */
    GATEWAY_MESSAGE_CONTENT(1 << 18),

    /**
     * Required for bots in under 100 servers to receive message content, found on the Bot page in your app's settings.
     */
    GATEWAY_MESSAGE_CONTENT_LIMITED(1 << 19),

    /**
     * If an app has registered global application commands.
     */
    APPLICATION_COMMAND_BADGE(1 << 23);

    private final long value;

    ApplicationFlag(long value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the flag.
     *
     * @return The value associated with the flag.
     */
    public long getValue() {
        return value;
    }
}
