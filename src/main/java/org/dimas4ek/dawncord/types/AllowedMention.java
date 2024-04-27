package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AllowedMention {
    ROLE("roles", "Controls role mentions"),
    USER("users", "Controls user mentions"),
    EVERYONE("everyone", "Controls @everyone and @here mentions"),
    EMPTY(null, "Suppress all mentions");

    private final String value;
    private final String description;
}
