package io.github.dawncord.api.types;

/**
 * Represents the voice regions available in Discord.
 */
public enum VoiceRegion {
    /**
     * Brazil voice region.
     */
    BRAZIL("brazil"),

    /**
     * Hong Kong voice region.
     */
    HONG_KONG("hongkong"),

    /**
     * India voice region.
     */
    INDIA("india"),

    /**
     * Japan voice region.
     */
    JAPAN("japan"),

    /**
     * Rotterdam voice region.
     */
    ROTTERDAM("rotterdam"),

    /**
     * Russia voice region.
     */
    RUSSIA("russia"),

    /**
     * Singapore voice region.
     */
    SINGAPORE("singapore"),

    /**
     * South Africa voice region.
     */
    SOUTH_AFRICA("southafrica"),

    /**
     * Sydney voice region.
     */
    SYDNEY("sydney"),

    /**
     * US Central voice region.
     */
    US_CENTRAL("us-central"),

    /**
     * US East voice region.
     */
    US_EAST("us-east"),

    /**
     * US South voice region.
     */
    US_SOUTH("us-south"),

    /**
     * US West voice region.
     */
    US_WEST("us-west");

    private final String value;

    VoiceRegion(String value) {
        this.value = value;
    }

    /**
     * Gets the value representing the voice region.
     *
     * @return The value representing the voice region.
     */
    public String getValue() {
        return value;
    }
}
