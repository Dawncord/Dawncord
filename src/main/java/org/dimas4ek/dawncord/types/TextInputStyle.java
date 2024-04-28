package org.dimas4ek.dawncord.types;

public enum TextInputStyle {
    SHORT(1, "Single-line input"),
    PARAGRAPH(2, "Multi-line input");

    private final int value;
    private final String description;

    TextInputStyle(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
