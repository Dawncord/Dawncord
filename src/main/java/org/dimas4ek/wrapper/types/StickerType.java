package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StickerType {
    STANDARD(1, "an official sticker in a pack"),
    GUILD(2, "a sticker uploaded to a guild for the guild's members");

    private final int value;
    private final String description;
}
