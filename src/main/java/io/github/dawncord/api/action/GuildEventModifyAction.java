package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.GuildEventEntityType;
import io.github.dawncord.api.types.GuildEventStatus;
import io.github.dawncord.api.utils.IOUtils;

import java.time.ZonedDateTime;

/**
 * Represents an action for updating a guild.
 *
 * @see Guild
 */
public class GuildEventModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String eventId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildEventModifyAction}
     *
     * @param guildId The ID of the guild where the event belongs.
     * @param eventId The ID of the event to be modified.
     */
    public GuildEventModifyAction(String guildId, String eventId) {
        this.guildId = guildId;
        this.eventId = eventId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildEventModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the guild scheduled event.
     *
     * @param name the name to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the guild scheduled event.
     *
     * @param description the description to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the location for the guild scheduled event.
     *
     * @param location the location to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setLocation(String location) {
        return setProperty("location", location);
    }

    /**
     * Sets the image for the guild scheduled event.
     *
     * @param path the path of the image file
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setImage(String path) {
        return setProperty("image", IOUtils.setImageData(path));
    }

    /**
     * Sets the status of the guild scheduled event.
     *
     * @param status the status to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setStatus(GuildEventStatus status) {
        return setProperty("status", status.getValue());
    }

    /**
     * Sets the channel entity type for the guild scheduled event.
     *
     * @param entityType the type of the entity to set
     * @param channelId  the channel ID to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setChannelEntityType(GuildEventEntityType entityType, String channelId) {
        setProperty("entity_type", entityType.getValue());
        setProperty("channel_id", channelId);
        return this;
    }

    /**
     * Sets the channel entity type for the guild scheduled event.
     *
     * @param entityType the type of the entity to set
     * @param channelId  the channel ID to set
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setChannelEntityType(GuildEventEntityType entityType, long channelId) {
        setProperty("entity_type", entityType.getValue());
        setProperty("channel_id", channelId);
        return this;
    }

    /**
     * Sets the external entity type for the guild scheduled event.
     *
     * @param location     the location of the external entity
     * @param endTimestamp the end timestamp of the scheduled event
     * @return the modified GuildEventModifyAction object
     */
    public GuildEventModifyAction setExternalEntityType(String location, ZonedDateTime endTimestamp) {
        setProperty("entity_type", GuildEventEntityType.EXTERNAL.getValue());
        setProperty("channel_id", null);
        setProperty("entity_metadata", mapper.createObjectNode().put("location", location));
        setProperty("scheduled_end_time", endTimestamp);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.ScheduledEvent.Get(guildId, eventId));
            hasChanges = false;
        }
    }
}
