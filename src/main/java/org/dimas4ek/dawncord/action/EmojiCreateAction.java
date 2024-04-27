package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.utils.IOUtils;

public class EmojiCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String guildId;
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;
    private String createdId;

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

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Emoji.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
