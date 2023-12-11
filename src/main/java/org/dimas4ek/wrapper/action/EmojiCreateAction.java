package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
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

    private void setProperty(String key, JsonNode value) {
        jsonObject.set(key, value);
        hasChanges = true;
    }

    public EmojiCreateAction setName(String name) {
        setProperty("name", mapper.createObjectNode().textNode(name));
        return this;
    }

    public EmojiCreateAction setRoles(String... roleIds) {
        setProperty("roles", mapper.valueToTree(roleIds));
        return this;
    }

    public EmojiCreateAction setImage(String path) {
        setProperty("image", mapper.createObjectNode().textNode(IOUtils.setImageData(path)));
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds/" + guildId + "/emojis");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
