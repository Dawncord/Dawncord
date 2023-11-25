package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StagePrivacyLevel {
    GUILD_ONLY(2);

    private final int value;
}
