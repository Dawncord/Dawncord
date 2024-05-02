package io.github.dawncord.api.types;

/**
 * Represents integration expire behaviors.
 */
public enum IntegrationExpireBehavior {
    /**
     * The role associated with the integration will be removed upon expiration.
     */
    REMOVE_ROLE(0),

    /**
     * The user associated with the integration will be kicked upon expiration.
     */
    KICK(1);

    private final int value;

    IntegrationExpireBehavior(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value representing the expire behavior.
     *
     * @return The numerical value.
     */
    public int getValue() {
        return value;
    }
}
