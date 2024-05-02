package io.github.dawncord.api.types;

/**
 * Represents flags that can be associated with a guild member.
 */
public enum GuildMemberFlag {
    /**
     * Member rejoined the guild.
     */
    DID_REJOIN(1L << 0),

    /**
     * Member completed onboarding.
     */
    COMPLETED_ONBOARDING(1L << 1),

    /**
     * Member bypasses verification.
     */
    BYPASSES_VERIFICATION(1L << 2),

    /**
     * Member started onboarding.
     */
    STARTED_ONBOARDING(1L << 3);

    private final long value;

    GuildMemberFlag(long value) {
        this.value = value;
    }

    /**
     * Gets the value of the flag.
     *
     * @return The value of the flag.
     */
    public long getValue() {
        return value;
    }
}
