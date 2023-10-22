package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

    private final String id;
}
