package io.github.dawncord.api.types;

/**
 * Represents flags associated with a user.
 */
public enum UserFlag {
    /**
     * Staff user flag.
     */
    STAFF(1L << 0),

    /**
     * Partner user flag.
     */
    PARTNER(1L << 1),

    /**
     * HypeSquad user flag.
     */
    HYPESQUAD(1L << 2),

    /**
     * Bug Hunter Level 1 user flag.
     */
    BUG_HUNTER_LEVEL_1(1L << 3),

    /**
     * HypeSquad Online House 1 user flag.
     */
    HYPESQUAD_ONLINE_HOUSE_1(1L << 6),

    /**
     * HypeSquad Online House 2 user flag.
     */
    HYPESQUAD_ONLINE_HOUSE_2(1L << 7),

    /**
     * HypeSquad Online House 3 user flag.
     */
    HYPESQUAD_ONLINE_HOUSE_3(1L << 8),

    /**
     * Premium early supporter user flag.
     */
    PREMIUM_EARLY_SUPPORTER(1L << 9),

    /**
     * Team pseudo user flag.
     */
    TEAM_PSEUDO_USER(1L << 10),

    /**
     * Bug Hunter Level 2 user flag.
     */
    BUG_HUNTER_LEVEL_2(1L << 14),

    /**
     * Verified bot user flag.
     */
    VERIFIED_BOT(1L << 16),

    /**
     * Verified developer user flag.
     */
    VERIFIED_DEVELOPER(1L << 17),

    /**
     * Certified moderator user flag.
     */
    CERTIFIED_MODERATOR(1L << 18),

    /**
     * Bot HTTP interactions user flag.
     */
    BOT_HTTP_INTERACTIONS(1L << 19),

    /**
     * Active developer user flag.
     */
    ACTIVE_DEVELOPER(1L << 22);

    private final long value;

    UserFlag(long value) {
        this.value = value;
    }

    /**
     * Gets the value representing the user flag.
     *
     * @return The value representing the user flag.
     */
    public long getValue() {
        return value;
    }
}
