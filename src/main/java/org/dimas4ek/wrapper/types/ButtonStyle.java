package org.dimas4ek.wrapper.types;

public enum ButtonStyle {
    Primary(1),
    Secondary(2),
    Success(3),
    Danger(4),
    Link(5);

    private final int value;

    ButtonStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
