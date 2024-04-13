package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.utils.IOUtils;

public class WebhookCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private boolean hasChanges = false;

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

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/channels/" + channelId + "/webhooks");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
