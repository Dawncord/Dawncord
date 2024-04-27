package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuildMemberFlag {
    DID_REJOIN(1L << 0),

    COMPLETED_ONBOARDING(1L << 1),

    BYPASSES_VERIFICATION(1L << 2),

    STARTED_ONBOARDING(1L << 3);

    private final long value;
}
