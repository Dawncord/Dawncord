package org.dimas4ek.dawncord.types;

public enum MembershipState {
    INVITED(1),
    ACCEPTED(2);

    private final int value;

    MembershipState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
