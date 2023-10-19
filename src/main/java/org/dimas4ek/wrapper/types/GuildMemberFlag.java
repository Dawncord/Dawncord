package org.dimas4ek.wrapper.types;

public enum GuildMemberFlag {
    DID_REJOIN(1L << 0),

    COMPLETED_ONBOARDING(1L << 1),

    BYPASSES_VERIFICATION(1L << 2),

    STARTED_ONBOARDING(1L << 3);

    private final long value;

    GuildMemberFlag(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
