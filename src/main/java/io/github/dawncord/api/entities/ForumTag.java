package io.github.dawncord.api.entities;

/**
 * Represents a forum tag.
 */
public class ForumTag {
    private String id;
    private final String name;
    private final boolean isModerated;
    private final String emojiIdOrName;

    /**
     * Constructs a ForumTag object with the specified attributes.
     *
     * @param id            The ID of the forum tag.
     * @param name          The name of the forum tag.
     * @param isModerated   A boolean indicating whether the forum tag is moderated.
     * @param emojiIdOrName The ID or name of the emoji associated with the forum tag.
     */
    public ForumTag(String id, String name, boolean isModerated, String emojiIdOrName) {
        this.id = id;
        this.name = name;
        this.isModerated = isModerated;
        this.emojiIdOrName = emojiIdOrName;
    }

    /**
     * Constructs a ForumTag object with the specified attributes.
     *
     * @param name          The name of the forum tag.
     * @param isModerated   A boolean indicating whether the forum tag is moderated.
     * @param emojiIdOrName The ID or name of the emoji associated with the forum tag.
     */
    public ForumTag(String name, boolean isModerated, String emojiIdOrName) {
        this.name = name;
        this.isModerated = isModerated;
        this.emojiIdOrName = emojiIdOrName;
    }

    /**
     * Retrieves the ID of the forum tag.
     *
     * @return The ID of the forum tag.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the name of the forum tag.
     *
     * @return The name of the forum tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks whether the forum tag is moderated.
     *
     * @return true if the forum tag is moderated, false otherwise.
     */
    public boolean isModerated() {
        return isModerated;
    }

    /**
     * Retrieves the ID or name of the emoji associated with the forum tag.
     *
     * @return The ID or name of the emoji associated with the forum tag.
     */
    public String getEmojiIdOrName() {
        return emojiIdOrName;
    }
}
