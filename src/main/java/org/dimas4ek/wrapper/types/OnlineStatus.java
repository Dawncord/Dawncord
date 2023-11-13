package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlineStatus {
    OFFLINE("offline"),
    DND("dnd"),
    IDLE("idle"),
    ONLINE("online");

    private final String status;
}
