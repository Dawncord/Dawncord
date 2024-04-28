package org.dimas4ek.dawncord.entities;

public class DefaultEmoji implements Emoji {
    private final String name;

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
