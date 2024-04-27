package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityType {
    GAME(0),
    STREAMING(1),
    LISTENING(2),
    WATCHING(3),
    CUSTOM(4),
    COMPETING(5);

    private final int value;
}
