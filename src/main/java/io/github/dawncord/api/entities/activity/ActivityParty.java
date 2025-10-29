package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a party associated with an activity.
 * ActivityParty is a class providing methods to access properties of activity parties.
 */
public class ActivityParty {
    private final LazyLoader loader;
    private String id;
    private Integer currentSize;
    private Integer maxSize;

    /**
     * Constructs an ActivityParty object with the provided JSON node containing party information.
     *
     * @param party The JSON node containing party information.
     */
    public ActivityParty(JsonNode party) {
        loader = new LazyLoader(party);
    }

    /**
     * Retrieves the ID of the activity party.
     *
     * @return The ID of the activity party.
     */
    public String getId() {
        id = loader.loadStringIfExistsAndNull(id, "id");
        return id;
    }

    /**
     * Retrieves the current size of the activity party.
     *
     * @return The current size of the activity party.
     */
    public int getCurrentSize() {
        currentSize = loader.loadIntFromArrayIfNull(currentSize, "size", 0);
        return currentSize;
    }

    /**
     * Retrieves the maximum size of the activity party.
     *
     * @return The maximum size of the activity party.
     */
    public int getMaxSize() {
        maxSize = loader.loadIntFromArrayIfNull(maxSize, "size", 1);
        return maxSize;
    }
}
