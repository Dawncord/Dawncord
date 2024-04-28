package org.dimas4ek.dawncord.types;

public enum VoiceRegion {
    BRAZIL("brazil"),
    HONG_KONG("hongkong"),
    INDIA("india"),
    JAPAN("japan"),
    ROTTERDAM("rotterdam"),
    RUSSIA("russia"),
    SINGAPORE("singapore"),
    SOUTH_AFRICA("southafrica"),
    SYDNEY("sydney"),
    US_CENTRAL("us-central"),
    US_EAST("us-east"),
    US_SOUTH("us-south"),
    US_WEST("us-west");

    private final String value;

    VoiceRegion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
