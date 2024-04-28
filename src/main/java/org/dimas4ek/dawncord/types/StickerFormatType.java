package org.dimas4ek.dawncord.types;

public enum StickerFormatType {
    PNG(1),
    APNG(2),
    LOTTIE(3),
    GIF(4);

    private final int value;

    StickerFormatType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
