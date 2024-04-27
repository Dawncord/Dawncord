package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    LATEST_ACTIVITY(0, "Sort forum posts by activity"),
    CREATION_DATE(1, "Sort forum posts by creation time (from most recent to oldest)");

    private final int value;
    private final String description;
}
