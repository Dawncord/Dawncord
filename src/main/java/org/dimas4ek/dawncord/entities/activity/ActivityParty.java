package org.dimas4ek.dawncord.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

public class ActivityParty {
    private final JsonNode party;
    private String id;
    private Integer currentSize;
    private Integer maxSize;

    public ActivityParty(JsonNode party) {
        this.party = party;
    }

    public String getId() {
        if (id == null) {
            id = party.get("id").asText();
        }
        return id;
    }

    public int getCurrentSize() {
        if (currentSize == null) {
            currentSize = party.path("size").get(0).asInt();
        }
        return currentSize;
    }

    public int getMaxSize() {
        if (maxSize == null) {
            maxSize = party.path("size").get(1).asInt();
        }
        return maxSize;
    }
}
