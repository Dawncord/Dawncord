package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {
    STREAM(1),
    EMBEDDED_APPLICATION(2);

    private final int value;
}
