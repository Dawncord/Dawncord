package io.github.dawncord.api.entities;

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
}

