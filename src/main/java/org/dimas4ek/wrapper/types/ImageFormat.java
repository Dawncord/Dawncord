package org.dimas4ek.wrapper.types;

public enum ImageFormat {
    JPEG(".jpeg"),
    PNG(".png"),
    WEBP(".webp"),
    GIF(".gif");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
