package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.utils.IOUtils;

public class EmojiCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String guildId;
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    public EmojiCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private EmojiCreateAction setProperty(String key, JsonNode value) {
        jsonObject.set(key, value);
        hasChanges = true;
        return this;
    }

    public EmojiCreateAction setName(String name) {
        return setProperty("name", mapper.createObjectNode().textNode(name));
    }

    public EmojiCreateAction setRoles(String... roleIds) {
        return setProperty("roles", mapper.valueToTree(roleIds));
    }

    public EmojiCreateAction setImage(String path) {
        return setProperty("image", mapper.createObjectNode().textNode(IOUtils.setImageData(path)));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Guild.Emoji.All(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
