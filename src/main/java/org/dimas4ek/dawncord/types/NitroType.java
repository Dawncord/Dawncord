package org.dimas4ek.dawncord.types;

public enum NitroType {
    NONE(0, "None"),
    NITRO_CLASSIC(1, "Nitro Classic"),
    NITRO(2, "Nitro"),
    NITRO_BASIC(3, "Nitro Basic");

    private final int value;
    private final String name;

    NitroType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
