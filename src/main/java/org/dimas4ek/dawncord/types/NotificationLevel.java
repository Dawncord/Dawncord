package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationLevel {
    ALL_MESSAGES(0, "members will receive notifications for all messages by default"),
    ONLY_MENTIONS(1, "members will receive notifications only for messages that @mention them by default");

    private final int value;
    private final String description;
}
