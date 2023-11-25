package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class GuildStickerModifyAction {
    private final String guildId;
    private final String stickerId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildStickerModifyAction(String guildId, String stickerId) {
        this.guildId = guildId;
        this.stickerId = stickerId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildStickerModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildStickerModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public GuildStickerModifyAction setEmoji(String emojiId) {
        setProperty("tags", emojiId);
        return this;
    }

    public GuildStickerModifyAction setEmoji(long emojiId) {
        setProperty("tags", emojiId);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/stickers/" + stickerId);
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
