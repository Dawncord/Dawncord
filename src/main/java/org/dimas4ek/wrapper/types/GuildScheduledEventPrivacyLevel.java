package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuildScheduledEventPrivacyLevel {
    GUILD_ONLY(2, "the scheduled event is only accessible to guild members");

    private final int value;
    private final String description;
}
