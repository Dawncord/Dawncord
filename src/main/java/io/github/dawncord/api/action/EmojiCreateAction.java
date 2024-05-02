package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.utils.IOUtils;

/**
 * Represents an action for creating an emoji.
 *
 * @see Emoji
 */
public class EmojiCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String guildId;
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link EmojiCreateAction}
     *
     * @param guildId The ID of the guild where the emoji will be created.
     */
    public EmojiCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private EmojiCreateAction setProperty(String key, JsonNode value) {
        jsonObject.set(key, value);
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name property for the emoji.
     *
     * @param name the name to set
     * @return the modified EmojiCreateAction object
     */
    public EmojiCreateAction setName(String name) {
        return setProperty("name", mapper.createObjectNode().textNode(name));
    }

    /**
     * Sets the roles property for the emoji.
     *
     * @param roleIds the IDs of the roles to set
     * @return the modified EmojiCreateAction object
     */
    public EmojiCreateAction setRoles(String... roleIds) {
        return setProperty("roles", mapper.valueToTree(roleIds));
    }

    /**
     * Sets the image property for the emoji.
     *
     * @param path the path of the image file
     * @return the modified EmojiCreateAction object
     */
    public EmojiCreateAction setImage(String path) {
        return setProperty("image", mapper.createObjectNode().textNode(IOUtils.setImageData(path)));
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Emoji.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
