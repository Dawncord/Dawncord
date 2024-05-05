package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.Stage;
import io.github.dawncord.api.types.StagePrivacyLevel;

/**
 * Represents an action for creating a stage.
 *
 * @see Stage
 */
public class StageCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link StageCreateAction}
     */
    public StageCreateAction() {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("privacy_level", StagePrivacyLevel.GUILD_ONLY.getValue());
    }

    private StageCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the topic property for the stage.
     *
     * @param topic the topic to set
     * @return the modified StageCreateAction object
     */
    public StageCreateAction setTopic(String topic) {
        return setProperty("topic", topic);
    }

    /**
     * Sets the channel ID property for the stage.
     *
     * @param channelId the channel ID to set
     * @return the modified StageCreateAction object
     */
    public StageCreateAction setChannelId(String channelId) {
        return setProperty("channel_id", channelId);
    }

    private StageCreateAction sendStartNotification(boolean enabled) {
        return setProperty("send_start_notification", enabled);
    }

    /**
     * Sets the guild scheduled event ID property for the stage.
     *
     * @param guildEventId the guild scheduled event ID to set
     * @return the modified StageCreateAction object
     */
    public StageCreateAction setGuildEventId(String guildEventId) {
        return setProperty("guild_scheduled_event_id", guildEventId);
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.StageInstances());
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("channel_id").asText();
            }
            hasChanges = false;
        }
    }
}
