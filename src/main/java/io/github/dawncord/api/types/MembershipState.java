package io.github.dawncord.api.types;

/**
 * Represents membership states.
 */
public enum MembershipState {
    /**
     * Invited membership state.
     */
    INVITED(1),

    /**
     * Accepted membership state.
     */
    ACCEPTED(2);

    private final int value;

    MembershipState(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the membership state.
     *
     * @return The value of the membership state.
     */
    public int getValue() {
        return value;
    }
}
