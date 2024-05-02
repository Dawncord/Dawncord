package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a party associated with an activity.
 * ActivityParty is a class providing methods to access properties of activity parties.
 */
public class ActivityParty {
    private final JsonNode party;
    private String id;
    private Integer currentSize;
    private Integer maxSize;

    /**
     * Constructs an ActivityParty object with the provided JSON node containing party information.
     *
     * @param party The JSON node containing party information.
     */
    public ActivityParty(JsonNode party) {
        this.party = party;
    }

    /**
     * Retrieves the ID of the activity party.
     *
     * @return The ID of the activity party.
     */
    public String getId() {
        if (id == null) {
            id = party.get("id").asText();
        }
        return id;
    }

    /**
     * Retrieves the current size of the activity party.
     *
     * @return The current size of the activity party.
     */
    public int getCurrentSize() {
        if (currentSize == null) {
            currentSize = party.path("size").get(0).asInt();
        }
        return currentSize;
    }

    /**
     * Retrieves the maximum size of the activity party.
     *
     * @return The maximum size of the activity party.
     */
    public int getMaxSize() {
        if (maxSize == null) {
            maxSize = party.path("size").get(1).asInt();
        }
        return maxSize;
    }
}
