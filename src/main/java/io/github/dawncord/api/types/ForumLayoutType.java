package io.github.dawncord.api.types;

/**
 * Represents the layout type for a forum channel.
 */
public enum ForumLayoutType {
    /**
     * No default layout has been set for the forum channel.
     */
    NOT_SET(0),

    /**
     * Display posts in list view.
     */
    LIST_VIEW(1),

    /**
     * Display posts in gallery view.
     */
    GALLERY_VIEW(2);

    private final int value;

    ForumLayoutType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the layout type.
     *
     * @return The value associated with the layout type.
     */
    public int getValue() {
        return value;
    }
}
