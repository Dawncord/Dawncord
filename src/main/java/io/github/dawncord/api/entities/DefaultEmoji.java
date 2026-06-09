package io.github.dawncord.api.entities;

/**
 * Implementation of the {@link Emoji} interface representing a default emoji.
 */
public record DefaultEmoji(String name) implements Emoji {
    /**
     * Constructs a DefaultEmoji object with the provided emoji name.
     *
     * @param name The name of the default emoji.
     */
    public DefaultEmoji {
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }
}
