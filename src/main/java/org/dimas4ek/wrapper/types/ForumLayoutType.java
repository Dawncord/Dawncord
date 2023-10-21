package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ForumLayoutType {
    NOT_SET(0, "No default has been set for forum channel"),
    LIST_VIEW(1, "Display posts as a list"),
    GALLERY_VIEW(2, "Display posts as a collection of tiles");

    private final int value;
    private final String description;
}
