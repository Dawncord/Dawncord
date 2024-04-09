package org.dimas4ek.wrapper.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.time.ZonedDateTime;

public class ActivityTimestamp {
    private final JsonNode timestamps;
    private ZonedDateTime start;
    private ZonedDateTime end;

    public ActivityTimestamp(JsonNode timestamps) {
        this.timestamps = timestamps;
    }

    public ZonedDateTime getStartTimestamp() {
        if (start == null) {
            start = MessageUtils.getZonedDateTime(timestamps, "start");
        }
        return start;
    }

    public ZonedDateTime getEndTimestamp() {
        if (end == null) {
            end = MessageUtils.getZonedDateTime(timestamps, "end");
        }
        return end;
    }
}
