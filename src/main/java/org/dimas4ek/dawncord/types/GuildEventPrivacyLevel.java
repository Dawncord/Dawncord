package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuildEventPrivacyLevel {
    GUILD_ONLY(2, "the scheduled event is only accessible to guild members");

    private final int value;
    private final String description;
}
