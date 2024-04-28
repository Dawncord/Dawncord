package org.dimas4ek.dawncord.types;

public enum ForumLayoutType {
    NOT_SET(0, "No default has been set for forum channel"),
    LIST_VIEW(1, "Display posts as a list"),
    GALLERY_VIEW(2, "Display posts as a collection of tiles");

    private final int value;
    private final String description;

    ForumLayoutType(int value, String description) {
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
