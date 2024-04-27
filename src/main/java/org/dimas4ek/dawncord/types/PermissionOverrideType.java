package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionOverrideType {
    ROLE(0),
    MEMBER(1);

    private final int value;
}
