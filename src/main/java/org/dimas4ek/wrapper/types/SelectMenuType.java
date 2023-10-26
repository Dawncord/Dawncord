package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SelectMenuType {
    TEXT(3),
    USER(5),
    ROLE(6),
    MENTIONABLE(7),
    CHANNEL(8);

    private final int value;
}
