package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MultipartBody;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.utils.AttachmentUtils;

import java.io.File;
import java.util.List;

public class GuildStickerCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Guild guild;
    private File file;
    private boolean hasChanges = false;
    private String createdId;

    public GuildStickerCreateAction(Guild guild) {
        this.guild = guild;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildStickerCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildStickerCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildStickerCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    public GuildStickerCreateAction setEmoji(String emojiId) {
        return setProperty("tags", emojiId);
    }

    public GuildStickerCreateAction setEmoji(long emojiId) {
        return setProperty("tags", emojiId);
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
        jsonObject.removeAll();
    }
}
