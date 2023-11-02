package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NitroType {
    NONE(0, "None"),
    NITRO_CLASSIC(1, "Nitro Classic"),
    NITRO(2, "Nitro"),
    NITRO_BASIC(3, "Nitro Basic");

    private final int value;
    private final String name;
}
