package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.AttachmentUtils;

import java.io.File;
import java.util.List;

public class GuildStickerCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Guild guild;
    private File file;
    private boolean hasChanges = false;

    public GuildStickerCreateAction(Guild guild) {
        this.guild = guild;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public GuildStickerCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildStickerCreateAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public GuildStickerCreateAction setEmoji(String emojiId) {
        setProperty("tags", emojiId);
        return this;
    }

    public GuildStickerCreateAction setEmoji(long emojiId) {
        setProperty("tags", emojiId);
        return this;
    }

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

    private void submit() {
        if (hasChanges) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, List.of(file));
            ApiClient.postAttachments(multipartBuilder, "/guilds/" + guild.getId() + "/stickers");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
