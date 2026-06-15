package io.github.dawncord.api.types;

/**
 * Represents flags that can be associated with a guild member.
 * Each constant carries the bit value used in the Discord API and a flag indicating
 * whether the flag can be set or cleared via the API.
 */
public enum GuildMemberFlag {
    /**
     * Member has left and rejoined the guild.
     */
    DID_REJOIN(1L << 0, false),

    /**
     * Member has completed onboarding.
     */
    COMPLETED_ONBOARDING(1L << 1, false),

    /**
     * Member is exempt from guild verification requirements.
     */
    BYPASSES_VERIFICATION(1L << 2, true),

    /**
     * Member has started onboarding.
     */
    STARTED_ONBOARDING(1L << 3, false),

    /**
     * Member is a guest and can only access the voice channel they were invited to.
     */
    IS_GUEST(1L << 4, false),

    /**
     * Member has started Server Guide new member actions.
     */
    STARTED_HOME_ACTIONS(1L << 5, false),

    /**
     * Member has completed Server Guide new member actions.
     */
    COMPLETED_HOME_ACTIONS(1L << 6, false),

    /**
     * Member's username, display name, or nickname is blocked by AutoMod.
     */
    AUTOMOD_QUARANTINED_USERNAME(1L << 7, false),

    /**
     * Member has dismissed the DM settings upsell.
     * Note: bit 8 (1&lt;&lt;8) is not currently used by Discord.
     */
    DM_SETTINGS_UPSELL_ACKNOWLEDGED(1L << 9, false),

    /**
     * Member's guild tag is blocked by AutoMod.
     */
    AUTOMOD_QUARANTINED_GUILD_TAG(1L << 10, false);

    private final long value;
    private final boolean editable;

    GuildMemberFlag(long value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    /**
     * Gets the bit value of this flag as used in the Discord API.
     *
     * @return The bit value of the flag.
     */
    public long getValue() {
        return value;
    }

    /**
     * Returns whether this flag can be set or cleared via the Discord API.
     *
     * @return {@code true} if the flag is editable, {@code false} otherwise.
     */
    public boolean isEditable() {
        return editable;
    }
}
