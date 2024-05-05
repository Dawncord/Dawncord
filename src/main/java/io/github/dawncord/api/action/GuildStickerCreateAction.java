package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.utils.AttachmentUtils;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.List;

/**
 * Represents an action for creating a guild sticker.
 *
 * @see Sticker
 */
public class GuildStickerCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Guild guild;
    private File file;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link GuildStickerCreateAction}
     *
     * @param guild The guild in which the sticker is being created.
     */
    public GuildStickerCreateAction(Guild guild) {
        this.guild = guild;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildStickerCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the guild sticker.
     *
     * @param name the name to set
     * @return the modified GuildStickerCreateAction object
     */
    public GuildStickerCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the guild sticker.
     *
     * @param description the description to set
     * @return the modified GuildStickerCreateAction object
     */
    public GuildStickerCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the emoji for the guild sticker.
     *
     * @param emojiId the emoji id to set
     * @return the modified GuildStickerCreateAction object
     */
    public GuildStickerCreateAction setEmoji(String emojiId) {
        return setProperty("tags", emojiId);
    }

    /**
     * Sets the emoji for the guild sticker.
     *
     * @param emojiId the emoji id to set
     * @return the modified GuildStickerCreateAction object
     */
    public GuildStickerCreateAction setEmoji(long emojiId) {
        return setProperty("tags", emojiId);
    }

    /**
     * Sets the image file for the guild sticker.
     *
     * @param file the image file to set
     * @return the modified GuildStickerCreateAction object
     * @throws IllegalArgumentException if the file format is not supported
     */
    public GuildStickerCreateAction setImage(File file) {
        String format = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        if (format.matches("png|jpg|jpeg")) {
            this.file = file;
        } else if (format.matches("gif|json|lottie")) {
            if (guild.getFeatures().contains("VERIFIED") || guild.getFeatures().contains("PARTNER")) {
                this.file = file;
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + format);
        }
        return this;
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, List.of(file));
            JsonNode jsonNode = ApiClient.postAttachments(multipartBuilder, Routes.Guild.Sticker.All(guild.getId()));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
