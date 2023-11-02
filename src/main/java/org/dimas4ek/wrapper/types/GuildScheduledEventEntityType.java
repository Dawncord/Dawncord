package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuildScheduledEventEntityType {
    STAGE_INSTANCE(1),
    VOICE(2),
    EXTERNAL(3);

    private final int value;
}
