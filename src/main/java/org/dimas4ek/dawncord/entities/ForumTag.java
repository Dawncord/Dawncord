package org.dimas4ek.dawncord.entities;

public class ForumTag {
    private String id;
    private final String name;
    private final boolean isModerated;
    private final String emojiIdOrName;

    public ForumTag(String id, String name, boolean isModerated, String emojiIdOrName) {
        this.id = id;
        this.name = name;
        this.isModerated = isModerated;
        this.emojiIdOrName = emojiIdOrName;
    }

    public ForumTag(String name, boolean isModerated, String emojiIdOrName) {
        this.name = name;
        this.isModerated = isModerated;
        this.emojiIdOrName = emojiIdOrName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isModerated() {
        return isModerated;
    }

    public String getEmojiIdOrName() {
        return emojiIdOrName;
    }
}
