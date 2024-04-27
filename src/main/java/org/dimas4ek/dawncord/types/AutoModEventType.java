package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AutoModEventType {
    MESSAGE_SEND(1, "when a member sends or edits a message in the guild");

    private final int value;
    private final String description;
}
