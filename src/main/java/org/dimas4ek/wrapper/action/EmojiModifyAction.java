package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;

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

    private EmojiModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public EmojiModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public EmojiModifyAction setRoles(List<String> roleIds) {
        return setProperty("roles", roleIds);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Emoji.Get(guildId, emojiId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
