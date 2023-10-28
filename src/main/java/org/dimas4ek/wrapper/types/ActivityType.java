package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityType {
    JOIN(1),
    SPECTATE(2),
    LISTEN(3),
    JOIN_REQUEST(5);

    private final int value;
}
