package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.utils.IOUtils;
import org.json.JSONObject;

public class WebhookModifyAction {
    private final String webhookId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public WebhookModifyAction(String webhookId) {
        this.webhookId = webhookId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
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
        jsonObject.clear();
    }
}
