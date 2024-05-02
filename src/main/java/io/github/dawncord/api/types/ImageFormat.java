package io.github.dawncord.api.types;

/**
 * Represents different image formats with their corresponding file extensions.
 */
public enum ImageFormat {
    /**
     * JPEG image format.
     */
    JPEG(".jpeg"),

    /**
     * PNG image format.
     */
    PNG(".png"),

    /**
     * WEBP image format.
     */
    WEBP(".webp"),

    /**
     * GIF image format.
     */
    GIF(".gif");

    private final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }

    /**
     * Gets the file extension associated with the image format.
     *
     * @return The file extension.
     */
    public String getExtension() {
        return extension;
    }
}
