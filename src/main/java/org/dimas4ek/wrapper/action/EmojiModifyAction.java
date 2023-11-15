package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

import java.util.List;

public class EmojiModifyAction {
    private final String guildId;
    private final String emojiId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public EmojiModifyAction(String guildId, String emojiId) {
        this.guildId = guildId;
        this.emojiId = emojiId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    public EmojiModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public EmojiModifyAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/emojis/" + emojiId);
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
