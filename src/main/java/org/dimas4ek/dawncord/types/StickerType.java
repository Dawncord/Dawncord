package org.dimas4ek.dawncord.types;

public enum StickerType {
    STANDARD(1, "an official sticker in a pack"),
    GUILD(2, "a sticker uploaded to a guild for the guild's members");

    private final int value;
    private final String description;

    StickerType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
