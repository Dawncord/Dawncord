package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.utils.IOUtils;

public class WebhookCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private boolean hasChanges = false;
    private String createdId;

    public WebhookCreateAction(String channelId) {
        this.channelId = channelId;
        this.jsonObject = mapper.createObjectNode();
    }

    private WebhookCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public WebhookCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public WebhookCreateAction setAvatar(String path) {
        return setProperty("avatar", IOUtils.setImageData(path));
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Channel.Webhooks(channelId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
