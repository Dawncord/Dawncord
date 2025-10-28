package io.github.dawncord.api.action.guildsticker;

import io.github.dawncord.api.action.Action;

public abstract class GuildStickerAction extends Action<GuildStickerAction> {
    protected final String guildId;

    /**
     * Create a new {@link GuildStickerAction}
     *
     * @param guildId The ID of the guild in which the sticker is being modified.
     */
    public GuildStickerAction(String guildId) {
        super();
        this.guildId = guildId;
    }

    /**
     * Sets the name property for the guild sticker.
     *
     * @param name the name to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description property for the guild sticker.
     *
     * @param description the description to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the emoji property for the guild sticker.
     *
     * @param emojiId the emoji ID to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerAction setEmoji(String emojiId) {
        return setProperty("tags", emojiId);
    }

    /**
     * Sets the emoji property for the guild sticker.
     *
     * @param emojiId the emoji ID to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerAction setEmoji(long emojiId) {
        return setProperty("tags", emojiId);
    }

}
