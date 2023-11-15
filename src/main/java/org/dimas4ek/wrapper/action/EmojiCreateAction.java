package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

import java.util.List;

public class EmojiCreateAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public EmojiCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    public EmojiCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public EmojiCreateAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    //todo setImage

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds/" + guildId + "/emojis");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
