package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.util.List;

public class EmojiCreateAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public EmojiCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
    }

    @CheckReturnValue
    public EmojiCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    @CheckReturnValue
    public EmojiCreateAction setRoles(List<String> roleIds) {
        setProperty("roles", roleIds);
        return this;
    }

    //todo setImage

    public void submit() {
        ApiClient.post(jsonObject, "/guilds/" + guildId + "/emojis");
        jsonObject.clear();
    }
}
