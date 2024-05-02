package io.github.dawncord.api.types;

/**
 * Represents sticker format types.
 */
public enum StickerFormatType {
    /**
     * PNG format.
     */
    PNG(1),
    /**
     * APNG format.
     */
    APNG(2),
    /**
     * Lottie format.
     */
    LOTTIE(3),
    /**
     * GIF Format.
     */
    GIF(4);

    private final int value;

    StickerFormatType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the sticker format type.
     *
     * @return The value of the sticker format type.
     */
    public int getValue() {
        return value;
    }
}
