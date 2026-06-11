package io.github.dawncord.api.action.guildsticker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.Sticker;
import io.github.dawncord.api.types.GuildFeature;
import io.github.dawncord.api.utils.AttachmentUtils;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.List;

/**
 * Represents an action for creating a guild sticker.
 *
 * @see Sticker
 */
public class GuildStickerCreateAction extends GuildStickerAction {
    private final Guild guild;
    private File file;

    /**
     * Create a new {@link GuildStickerCreateAction}
     *
     * @param guild The guild in which the sticker is being created.
     */
    public GuildStickerCreateAction(Guild guild) {
        this.guild = guild;
        super(guild.getId());
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
            //todo check
            if (guild.getFeatures().contains(GuildFeature.VERIFIED) || guild.getFeatures().contains(GuildFeature.PARTNERED)) {
                this.file = file;
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + format);
        }
        return this;
    }

    @Override
    protected void submit() {
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
