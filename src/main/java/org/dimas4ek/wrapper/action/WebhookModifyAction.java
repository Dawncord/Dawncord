package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.utils.IOUtils;

public class WebhookModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String webhookId;
    private boolean hasChanges = false;

    public WebhookModifyAction(String webhookId) {
        this.webhookId = webhookId;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public WebhookModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public WebhookModifyAction setAvatar(String path) {
        setProperty("avatar", IOUtils.setImageData(path));
        return this;
    }

    public WebhookModifyAction setChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    public WebhookModifyAction setChannel(long channelId) {
        setChannel(String.valueOf(channelId));
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/webhooks/" + webhookId);
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
