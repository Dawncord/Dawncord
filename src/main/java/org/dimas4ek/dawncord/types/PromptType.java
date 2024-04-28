package org.dimas4ek.dawncord.types;

public enum PromptType {
    MULTIPLE_CHOICE(0),
    DROPDOWN(1);

    private final int value;

    PromptType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
