package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.utils.IOUtils;

/**
 * Represents an action for creating a webhook.
 *
 * @see Webhook
 */
public class WebhookCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link WebhookCreateAction}
     *
     * @param channelId The ID of the channel to create the webhook for.
     */
    public WebhookCreateAction(String channelId) {
        this.channelId = channelId;
        this.jsonObject = mapper.createObjectNode();
    }

    private WebhookCreateAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the webhook.
     *
     * @param name the name to set
     * @return the modified WebhookCreateAction object
     */
    public WebhookCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the avatar for the webhook.
     *
     * @param path the path of the avatar image file
     * @return the modified WebhookCreateAction object
     */
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
    }
}
