package io.github.dawncord.api.action.webhook;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Webhook;

/**
 * Represents an action for updating a webhook.
 *
 * @see Webhook
 */
public class WebhookModifyAction extends WebhookAction {
    private final String webhookId;

    /**
     * Create a new {@link WebhookModifyAction}
     *
     * @param webhookId The ID of the webhook to modify.
     */
    public WebhookModifyAction(String webhookId) {
        super();
        this.webhookId = webhookId;
    }
    
    /**
     * Sets the channel ID for the webhook.
     *
     * @param channelId the ID of the channel to set
     * @return the modified WebhookModifyAction object
     */
    public WebhookModifyAction setChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
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

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Webhook.ById(webhookId));
            hasChanges = false;
        }
    }
}
