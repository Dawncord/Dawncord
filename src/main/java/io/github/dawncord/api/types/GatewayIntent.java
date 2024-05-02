package io.github.dawncord.api.types;

/**
 * Represents different intents for gateway events.
 */
public enum GatewayIntent {
    /**
     * Allows access to guilds.
     */
    GUILDS(1 << 0),

    /**
     * Allows access to guild members.
     */
    GUILD_MEMBERS(1 << 1),

    /**
     * Allows access to guild moderation features.
     */
    GUILD_MODERATION(1 << 2),

    /**
     * Allows access to guild emojis and stickers.
     */
    GUILD_EMOJIS_AND_STICKERS(1 << 3),

    /**
     * Allows access to guild integrations.
     */
    GUILD_INTEGRATIONS(1 << 4),

    /**
     * Allows access to guild webhooks.
     */
    GUILD_WEBHOOKS(1 << 5),

    /**
     * Allows access to guild invites.
     */
    GUILD_INVITES(1 << 6),

    /**
     * Allows access to guild voice states.
     */
    GUILD_VOICE_STATES(1 << 7),

    /**
     * Allows access to guild presences.
     */
    GUILD_PRESENCES(1 << 8),

    /**
     * Allows access to guild messages.
     */
    GUILD_MESSAGES(1 << 9),

    /**
     * Allows access to reactions on guild messages.
     */
    GUILD_MESSAGE_REACTIONS(1 << 10),

    /**
     * Allows access to typing events on guild messages.
     */
    GUILD_MESSAGE_TYPING(1 << 11),

    /**
     * Allows access to direct messages.
     */
    DIRECT_MESSAGES(1 << 12),

    /**
     * Allows access to reactions on direct messages.
     */
    DIRECT_MESSAGE_REACTIONS(1 << 13),

    /**
     * Allows access to typing events on direct messages.
     */
    DIRECT_MESSAGE_TYPING(1 << 14),

    /**
     * Allows access to message content in gateway events.
     */
    MESSAGE_CONTENT(1 << 15),

    /**
     * Allows access to guild scheduled events.
     */
    GUILD_SCHEDULED_EVENTS(1 << 16),

    /**
     * Allows access to auto-moderation configuration.
     */
    AUTO_MODERATION_CONFIGURATION(1 << 20),

    /**
     * Allows access to auto-moderation execution.
     */
    AUTO_MODERATION_EXECUTION(1 << 21),

    /**
     * Represents all intents.
     */
    ALL(0);

    private final long value;

    GatewayIntent(long value) {
        this.value = value;
    }

    /**
     * Gets the value of the intent.
     *
     * @return The value of the intent.
     */
    public long getValue() {
        return value;
    }
}
