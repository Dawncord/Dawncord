package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.util.List;

public class EmojiModifyAction {
    private final String guildId;
    private final String emojiId;
    private final JSONObject jsonObject;

    public EmojiModifyAction(String guildId, String emojiId) {
        this.guildId = guildId;
        this.emojiId = emojiId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
    }

    @CheckReturnValue
    public EmojiModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    @CheckReturnValue
    public EmojiModifyAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/emojis/" + emojiId);
        jsonObject.clear();
    }
}
