package org.dimas4ek.dawncord.types;

public enum ContentFilterLevel {
    DISABLED(0, "media content will not be scanned"),
    MEMBERS_WITHOUT_ROLES(1, "media content sent by members without roles will be scanned"),
    ALL_MEMBERS(2, "media content sent by all members will be scanned");

    private final int value;
    private final String description;

    ContentFilterLevel(int value, String description) {
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
