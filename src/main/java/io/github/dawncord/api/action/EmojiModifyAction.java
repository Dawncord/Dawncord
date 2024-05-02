package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Emoji;

/**
 * Represents an action for updating an emoji.
 *
 * @see Emoji
 */
public class EmojiModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String emojiId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link EmojiModifyAction}
     *
     * @param guildId The ID of the guild where the emoji exists.
     * @param emojiId The ID of the emoji to modify.
     */
    public EmojiModifyAction(String guildId, String emojiId) {
        this.guildId = guildId;
        this.emojiId = emojiId;
        this.jsonObject = mapper.createObjectNode();
    }

    private EmojiModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name property for the emoji.
     *
     * @param name the name to set
     * @return the modified EmojiModifyAction object
     */
    public EmojiModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the roles property for the emoji.
     *
     * @param roleIds the IDs of the roles to set
     * @return the modified EmojiModifyAction object
     */
    public EmojiModifyAction setRoles(String... roleIds) {
        return setProperty("roles", roleIds);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Emoji.Get(guildId, emojiId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
