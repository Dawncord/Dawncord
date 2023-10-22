package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerificationLevel {
    NONE(0, "unrestricted"),
    LOW(1, "must have verified email on account"),
    MEDIUM(2, "must be registered on Discord for longer than 5 minutes"),
    HIGH(3, "must be a member of the server for longer than 10 minutes"),
    VERY_HIGH(4, "must have a verified phone number");

    private final int value;
    private final String description;
}
