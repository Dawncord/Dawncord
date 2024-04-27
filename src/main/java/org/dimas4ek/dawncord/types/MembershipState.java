package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MembershipState {
    INVITED(1),
    ACCEPTED(2);

    private final int value;
}
