package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.message.sticker.Sticker;

/**
 * Represents an action for updating a guild sticker.
 *
 * @see Sticker
 */
public class GuildStickerModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String stickerId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildStickerModifyAction}
     *
     * @param guildId   The ID of the guild in which the sticker is being modified.
     * @param stickerId The ID of the sticker being modified.
     */
    public GuildStickerModifyAction(String guildId, String stickerId) {
        this.guildId = guildId;
        this.stickerId = stickerId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildStickerModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name property for the guild sticker.
     *
     * @param name the name to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description property for the guild sticker.
     *
     * @param description the description to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the emoji property for the guild sticker.
     *
     * @param emojiId the emoji ID to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerModifyAction setEmoji(String emojiId) {
        return setProperty("tags", emojiId);
    }

    /**
     * Sets the emoji property for the guild sticker.
     *
     * @param emojiId the emoji ID to set
     * @return the modified GuildStickerModifyAction object
     */
    public GuildStickerModifyAction setEmoji(long emojiId) {
        return setProperty("tags", emojiId);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Sticker.Get(guildId, stickerId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
