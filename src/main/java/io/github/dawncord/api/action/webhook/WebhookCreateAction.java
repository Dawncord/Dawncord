package io.github.dawncord.api.action.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Webhook;

/**
 * Represents an action for creating a webhook.
 *
 * @see Webhook
 */
public class WebhookCreateAction extends WebhookAction {
    private final String channelId;
    private String createdId;

    /**
     * Create a new {@link WebhookCreateAction}
     *
     * @param channelId The ID of the channel to create the webhook for.
     */
    public WebhookCreateAction(String channelId) {
        super();
        this.channelId = channelId;
    }

    private String getCreatedId() {
        return createdId;
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Channel.Webhooks(channelId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
