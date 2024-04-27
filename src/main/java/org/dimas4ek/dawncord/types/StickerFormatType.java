package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StickerFormatType {
    PNG(1),
    APNG(2),
    LOTTIE(3),
    GIF(4);

    private final int value;
}
