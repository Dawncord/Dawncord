package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;

/**
 * Represents the start and end timestamps associated with an activity.
 */
public class ActivityTimestamp {

    private final JsonNode timestamps;
    private ZonedDateTime start;
    private ZonedDateTime end;

    /**
     * Constructs an ActivityTimestamp object with the provided timestamps JSON object.
     *
     * @param timestamps The JSON object containing the timestamps of the activity.
     */
    public ActivityTimestamp(JsonNode timestamps) {
        this.timestamps = timestamps;
    }

    /**
     * Retrieves the start timestamp of the activity.
     *
     * @return The start timestamp of the activity.
     */
    public ZonedDateTime getStartTimestamp() {
        if (start == null) {
            start = TimeUtils.getZonedDateTime(timestamps, "start");
        }
        return start;
    }

    /**
     * Retrieves the end timestamp of the activity.
     *
     * @return The end timestamp of the activity.
     */
    public ZonedDateTime getEndTimestamp() {
        if (end == null) {
            end = TimeUtils.getZonedDateTime(timestamps, "end");
        }
        return end;
    }
}
