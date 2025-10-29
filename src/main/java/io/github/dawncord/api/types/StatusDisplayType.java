package io.github.dawncord.api.types;

public enum StatusDisplayType {
    NAME(0),
    STATE(1),
    DETAILS(2);
    
    private final int value;
    
    StatusDisplayType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
