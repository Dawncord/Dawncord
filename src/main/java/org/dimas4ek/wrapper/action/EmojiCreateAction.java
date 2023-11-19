package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.utils.IOUtils;
import org.json.JSONObject;

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

    public EmojiCreateAction setRoles(String... roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    public EmojiCreateAction setImage(String path) {
        setProperty("image", IOUtils.setImageData(path));
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds/" + guildId + "/emojis");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
