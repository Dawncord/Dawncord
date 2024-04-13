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

    private WebhookModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public WebhookModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public WebhookModifyAction setAvatar(String path) {
        return setProperty("avatar", IOUtils.setImageData(path));
    }

    public WebhookModifyAction setChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    public WebhookModifyAction setChannel(long channelId) {
        return setChannel(String.valueOf(channelId));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/webhooks/" + webhookId);
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
