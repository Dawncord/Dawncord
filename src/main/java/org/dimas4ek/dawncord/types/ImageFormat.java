package org.dimas4ek.dawncord.types;

import lombok.Getter;

@Getter
public enum ImageFormat {
    JPEG(".jpeg"),
    PNG(".png"),
    WEBP(".webp"),
    GIF(".gif");

    private final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }
}
