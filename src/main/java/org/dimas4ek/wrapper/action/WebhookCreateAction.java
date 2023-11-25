package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.utils.IOUtils;
import org.json.JSONObject;

public class WebhookCreateAction {
    private final String channelId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public WebhookCreateAction(String channelId) {
        this.channelId = channelId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    public WebhookCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public WebhookCreateAction setAvatar(String path) {
        setProperty("avatar", IOUtils.setImageData(path));
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/channels/" + channelId + "/webhooks");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
