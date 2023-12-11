package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;

import java.util.List;

public class EmojiModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String emojiId;
    private boolean hasChanges = false;

    public EmojiModifyAction(String guildId, String emojiId) {
        this.guildId = guildId;
        this.emojiId = emojiId;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
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
        jsonObject.removeAll();
    }
}
