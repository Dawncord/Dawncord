package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AutoModActionType {
    BLOCK_MESSAGE(1, "blocks a member's message and prevents it from being posted. A custom explanation can be specified and shown to members whenever their message is blocked."),
    SEND_ALERT_MESSAGE(2, "logs user content to a specified channel"),
    TIMEOUT(3, "timeout user for a specified duration *");

    private final int value;
    private final String description;
}
