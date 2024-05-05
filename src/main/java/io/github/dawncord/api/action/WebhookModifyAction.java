package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.utils.IOUtils;

/**
 * Represents an action for updating a webhook.
 *
 * @see Webhook
 */
public class WebhookModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String webhookId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link WebhookModifyAction}
     *
     * @param webhookId The ID of the webhook to modify.
     */
    public WebhookModifyAction(String webhookId) {
        this.webhookId = webhookId;
        this.jsonObject = mapper.createObjectNode();
    }

    private WebhookModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the webhook.
     *
     * @param name the name to set
     * @return the modified WebhookModifyAction object
     */
    public WebhookModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the avatar for the webhook.
     *
     * @param path the path of the image file
     * @return the modified WebhookModifyAction object
     */
    public WebhookModifyAction setAvatar(String path) {
        return setProperty("avatar", IOUtils.setImageData(path));
    }

    /**
     * Sets the channel ID for the webhook.
     *
     * @param channelId the ID of the channel to set
     * @return the modified WebhookModifyAction object
     */
    public WebhookModifyAction setChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    /**
     * Sets the channel ID for the webhook.
     *
     * @param channelId the ID of the channel to set
     * @return the modified WebhookModifyAction object
     */
    public WebhookModifyAction setChannel(long channelId) {
        return setChannel(String.valueOf(channelId));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Webhook.ById(webhookId));
            hasChanges = false;
        }
    }
}
