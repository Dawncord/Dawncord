package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationExpireBehavior {
    REMOVE_ROLE(0, "Remove Role"),
    KICK(1, "Kick");

    private final int value;
    private final String description;
}
