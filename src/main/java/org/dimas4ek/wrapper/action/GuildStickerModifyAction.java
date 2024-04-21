package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;

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

    private GuildStickerModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildStickerModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildStickerModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    public GuildStickerModifyAction setEmoji(String emojiId) {
        return setProperty("tags", emojiId);
    }

    public GuildStickerModifyAction setEmoji(long emojiId) {
        return setProperty("tags", emojiId);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Sticker.Get(guildId, stickerId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
