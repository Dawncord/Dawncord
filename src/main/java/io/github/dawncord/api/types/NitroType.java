package io.github.dawncord.api.types;

/**
 * Represents types of Nitro subscription.
 */
public enum NitroType {
    /**
     * No Nitro subscription.
     */
    NONE(0),

    /**
     * Nitro Classic subscription.
     */
    NITRO_CLASSIC(1),

    /**
     * Nitro subscription.
     */
    NITRO(2),

    /**
     * Nitro Basic subscription.
     */
    NITRO_BASIC(3);

    private final int value;

    NitroType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the Nitro type.
     *
     * @return The value of the Nitro type.
     */
    public int getValue() {
        return value;
    }
}
