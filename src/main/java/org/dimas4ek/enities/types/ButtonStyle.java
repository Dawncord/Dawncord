package org.dimas4ek.enities.types;

public enum ButtonStyle {
    PRIMARY(1),
    SECONDARY(2),
    SUCCESS(3),
    DANGER(4),
    LINK(5);
    
    private final int value;
    
    ButtonStyle(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
