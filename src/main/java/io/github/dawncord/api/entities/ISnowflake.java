package io.github.dawncord.api.entities;

import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;

/**
 * An interface representing objects with unique identifiers.
 */
public interface ISnowflake {
    /**
     * Retrieves the unique identifier as a string.
     *
     * @return The unique identifier as a string.
     */
    String getId();

    /**
     * Retrieves the unique identifier as a long value.
     *
     * @return The unique identifier as a long value.
     */
    long getIdLong();


    /**
     * Retrieves the timestamp when the message was sent.
     *
     * @return The timestamp when the message was sent.
     */
    default ZonedDateTime getTimeCreated() {
        return TimeUtils.getTimeCreated(getId());
    }
}

