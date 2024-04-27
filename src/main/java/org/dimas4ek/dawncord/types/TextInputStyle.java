package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TextInputStyle {
    SHORT(1, "Single-line input"),
    PARAGRAPH(2, "Multi-line input");

    private final int value;
    private final String description;
}
