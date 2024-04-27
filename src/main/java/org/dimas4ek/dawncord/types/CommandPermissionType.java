package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandPermissionType {
    ROLE(1),
    USER(2),
    CHANNEL(3);

    private final int value;
}
