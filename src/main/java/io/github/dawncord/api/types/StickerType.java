package io.github.dawncord.api.types;

/**
 * Represents sticker types.
 */
public enum StickerType {
    /**
     * Official sticker in a pack
     */
    STANDARD(1),
    /**
     * Sticker uploaded to a guild for the guild's members
     */
    GUILD(2);

    private final int value;

    StickerType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the sticker type.
     *
     * @return The value of the sticker type.
     */
    public int getValue() {
        return value;
    }
}
