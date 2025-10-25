package io.github.dawncord.api.action.webhook;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.utils.IOUtils;

public abstract class WebhookAction extends Action<WebhookAction> {
    /**
     * Sets the name for the webhook.
     *
     * @param name the name to set
     * @return the modified WebhookAction object
     */
    public WebhookAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the avatar for the webhook.
     *
     * @param path the path of the avatar image file
     * @return the modified WebhookAction object
     */
    public WebhookAction setAvatar(String path) {
        return setProperty("avatar", IOUtils.setImageData(path));
    }
}
