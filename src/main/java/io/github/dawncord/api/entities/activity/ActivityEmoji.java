package io.github.dawncord.api.entities.activity;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents an emoji associated with an activity.
 * ActivityEmoji is a class providing methods to access properties of activity emojis.
 */
public class ActivityEmoji implements ISnowflake {
    private final LazyLoader loader;
    private String id;
    private String name;
    private Boolean isAnimated;

    /**
     * Constructs an ActivityEmoji object with the provided JSON node containing emoji information.
     *
     * @param emoji The JSON node containing emoji information.
     */
    public ActivityEmoji(JsonNode emoji) {
        loader = new LazyLoader(emoji);
    }

    /**
     * Retrieves the ID of the activity emoji.
     *
     * @return The ID of the activity emoji.
     */
    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    /**
     * Retrieves the name of the activity emoji.
     *
     * @return The name of the activity emoji.
     */
    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    /**
     * Checks if the activity emoji is animated.
     *
     * @return True if the activity emoji is animated, false otherwise.
     */
    public boolean isAnimated() {
        isAnimated = loader.loadBoolean(isAnimated, "animated");
        return isAnimated;
    }
}