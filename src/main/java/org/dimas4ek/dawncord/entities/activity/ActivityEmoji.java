package org.dimas4ek.dawncord.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

public class ActivityEmoji {
    private final JsonNode emoji;
    private String id;
    private String name;
    private Boolean isAnimated;

    public ActivityEmoji(JsonNode emoji) {
        this.emoji = emoji;
    }

    public String getId() {
        if (id == null) {
            id = emoji.get("id").asText();
        }
        return id;
    }

    public String getName() {
        if (name == null) {
            name = emoji.get("name").asText();
        }
        return name;
    }

    public boolean isAnimated() {
        if (isAnimated == null) {
            isAnimated = emoji.get("animated").asBoolean();
        }
        return isAnimated;
    }
}
