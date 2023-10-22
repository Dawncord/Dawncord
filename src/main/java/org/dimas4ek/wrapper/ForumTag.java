package org.dimas4ek.wrapper;

import lombok.Getter;

@Getter
public class ForumTag {
    private final String name;
    private final boolean isModerated;
    private final String emojiIdOrName;

    public ForumTag(String name, boolean isModerated, String emojiIdOrName) {
        this.name = name;
        this.isModerated = isModerated;
        this.emojiIdOrName = emojiIdOrName;
    }
}
