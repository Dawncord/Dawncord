package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserFlag {
    STAFF(1L << 0),
    PARTNER(1L << 1),
    HYPESQUAD(1L << 2),
    BUG_HUNTER_LEVEL_1(1L << 3),
    HYPESQUAD_ONLINE_HOUSE_1(1L << 6),
    HYPESQUAD_ONLINE_HOUSE_2(1L << 7),
    HYPESQUAD_ONLINE_HOUSE_3(1L << 8),
    PREMIUM_EARLY_SUPPORTER(1L << 9),
    TEAM_PSEUDO_USER(1L << 10),
    BUG_HUNTER_LEVEL_2(1L << 14),
    VERIFIED_BOT(1L << 16),
    VERIFIED_DEVELOPER(1L << 17),
    CERTIFIED_MODERATOR(1L << 18),
    BOT_HTTP_INTERACTIONS(1L << 19),
    ACTIVE_DEVELOPER(1L << 22);

    private final long value;
}
