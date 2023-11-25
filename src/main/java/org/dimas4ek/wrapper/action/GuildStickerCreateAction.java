package org.dimas4ek.wrapper.action;

import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.AttachmentUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class GuildStickerCreateAction {
    private final Guild guild;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;
    private File file;

    public GuildStickerCreateAction(Guild guild) {
        this.guild = guild;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
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
            MultipartBody.Builder multipartBuilder = AttachmentUtils.creteMultipartBuilder(jsonObject, List.of(file));
            ApiClient.postAttachments(multipartBuilder, "/guilds/" + guild.getId() + "/stickers");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
