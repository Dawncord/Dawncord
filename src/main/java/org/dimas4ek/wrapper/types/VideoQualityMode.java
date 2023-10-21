package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoQualityMode {
    AUTO(1, "Discord chooses the quality for optimal performance"),
    FULL(2, "720p");

    private final int value;
    private final String description;
}
