package io.github.dawncord.api.action.emoji;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.utils.IOUtils;

/**
 * Represents an action for creating an emoji.
 *
 * @see Emoji
 */
public class EmojiCreateAction extends EmojiAction {
    /**
     * Create a new {@link EmojiCreateAction}
     *
     * @param guildId The ID of the guild where the emoji will be created.
     */
    public EmojiCreateAction(String guildId) {
        super(guildId);
    }

    /**
     * Sets the image property for the emoji.
     *
     * @param path the path of the image file
     * @return the modified EmojiCreateAction object
     */
    public EmojiCreateAction setImage(String path) {
        return (EmojiCreateAction) setProperty("image", IOUtils.setImageData(path));
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Emoji.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
