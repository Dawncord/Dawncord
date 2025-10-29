package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;

/**
 * Represents the start and end timestamps associated with an activity.
 */
public class ActivityTimestamp {
    private final LazyLoader loader;
    private ZonedDateTime start;
    private ZonedDateTime end;

    /**
     * Constructs an ActivityTimestamp object with the provided timestamps JSON object.
     *
     * @param timestamps The JSON object containing the timestamps of the activity.
     */
    public ActivityTimestamp(JsonNode timestamps) {
        loader = new LazyLoader(timestamps);
    }

    /**
     * Retrieves the start timestamp of the activity.
     *
     * @return The start timestamp of the activity.
     */
    public ZonedDateTime getStartTimestamp() {
        start = loader.loadZonedDateTimeIfNull(start, "start");
        return start;
    }

    /**
     * Retrieves the end timestamp of the activity.
     *
     * @return The end timestamp of the activity.
     */
    public ZonedDateTime getEndTimestamp() {
        end = loader.loadZonedDateTimeIfNull(end, "end");
        return end;
    }
}
