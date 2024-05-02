package io.github.dawncord.api.entities;

/**
 * Implementation of the {@link Emoji} interface representing a default emoji.
 */
public class DefaultEmoji implements Emoji {
    private final String name;

    /**
     * Constructs a DefaultEmoji object with the provided emoji name.
     *
     * @param name The name of the default emoji.
     */
    public DefaultEmoji(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return name;
    }
}
