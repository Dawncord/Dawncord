package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;

public class GuildStickerModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String stickerId;
    private boolean hasChanges = false;

    public GuildStickerModifyAction(String guildId, String stickerId) {
        this.guildId = guildId;
        this.stickerId = stickerId;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
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
        jsonObject.removeAll();
    }
}
