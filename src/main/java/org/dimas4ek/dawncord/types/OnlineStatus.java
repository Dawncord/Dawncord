package org.dimas4ek.dawncord.types;

public enum OnlineStatus {
    OFFLINE("offline"),
    DND("dnd"),
    IDLE("idle"),
    ONLINE("online");

    private final String value;

    OnlineStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
