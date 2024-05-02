package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents an emoji associated with an activity.
 * ActivityEmoji is a class providing methods to access properties of activity emojis.
 */
public class ActivityEmoji {
    private final JsonNode emoji;
    private String id;
    private String name;
    private Boolean isAnimated;

    /**
     * Constructs an ActivityEmoji object with the provided JSON node containing emoji information.
     *
     * @param emoji The JSON node containing emoji information.
     */
    public ActivityEmoji(JsonNode emoji) {
        this.emoji = emoji;
    }

    /**
     * Retrieves the ID of the activity emoji.
     *
     * @return The ID of the activity emoji.
     */
    public String getId() {
        if (id == null) {
            id = emoji.get("id").asText();
        }
        return id;
    }

    /**
     * Retrieves the name of the activity emoji.
     *
     * @return The name of the activity emoji.
     */
    public String getName() {
        if (name == null) {
            name = emoji.get("name").asText();
        }
        return name;
    }

    /**
     * Checks if the activity emoji is animated.
     *
     * @return True if the activity emoji is animated, false otherwise.
     */
    public boolean isAnimated() {
        if (isAnimated == null) {
            isAnimated = emoji.get("animated").asBoolean();
        }
        return isAnimated;
    }
}