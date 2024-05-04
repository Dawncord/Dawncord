package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for time-related operations.
 */
public class TimeUtils {
    /**
     * Parses a timestamp from a JSON object.
     *
     * @param object    The JSON object.
     * @param timestamp The timestamp field name.
     * @return The parsed ZonedDateTime object.
     */
    public static ZonedDateTime getZonedDateTime(JsonNode object, String timestamp) {
        return ZonedDateTime.parse(object.get(timestamp).asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * Converts an entity ID into a ZonedDateTime representing the time the entity was created.
     *
     * @param entityId the ID of the entity to convert
     * @return the ZonedDateTime representing the time the entity was created
     */
    public static ZonedDateTime getTimeCreated(String entityId) {
        long timestamp = (Long.parseLong(entityId) >>> 22) + 1420070400000L;
        Instant instant = Instant.ofEpochMilli(timestamp);
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
