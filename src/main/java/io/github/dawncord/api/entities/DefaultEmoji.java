package io.github.dawncord.api.entities;

/**
 * Represents a default emoji.
 */
public record DefaultEmoji(String name) implements Emoji {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getIdLong() {
        return 0;
    }
}
