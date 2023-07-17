package org.dimas4ek.other;

public enum InteractionType {
    PING(1),
    APPLICATION_COMMAND(2),
    MESSAGE_COMPONENT(3),
    APPLICATION_COMMAND_AUTOCOMPLETE(4),
    MODAL_SUBMIT(5);
    
    private final int type;
    
    InteractionType(int type) {
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
}
