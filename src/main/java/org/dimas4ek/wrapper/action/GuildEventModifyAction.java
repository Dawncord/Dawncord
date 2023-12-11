package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.GuildEventEntityType;
import org.dimas4ek.wrapper.types.GuildEventStatus;
import org.dimas4ek.wrapper.utils.IOUtils;

import java.time.ZonedDateTime;

public class GuildEventModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String eventId;
    private boolean hasChanges = false;

    public GuildEventModifyAction(String guildId, String eventId) {
        this.guildId = guildId;
        this.eventId = eventId;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
    }

    public GuildEventModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildEventModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public GuildEventModifyAction setLocation(String location) {
        setProperty("location", location);
        return this;
    }

    public GuildEventModifyAction setImage(String path) {
        setProperty("image", IOUtils.setImageData(path));
        return this;
    }

    public GuildEventModifyAction setStatus(GuildEventStatus status) {
        setProperty("status", status.getValue());
        return this;
    }

    public GuildEventModifyAction setChannelEntityType(GuildEventEntityType entityType, String channelId) {
        setProperty("entity_type", entityType.getValue());
        setProperty("channel_id", channelId);
        return this;
    }

    public GuildEventModifyAction setChannelEntityType(GuildEventEntityType entityType, long channelId) {
        setProperty("entity_type", entityType.getValue());
        setProperty("channel_id", channelId);
        return this;
    }

    public GuildEventModifyAction setExternalEntityType(String location, ZonedDateTime endTimestamp) {
        setProperty("entity_type", GuildEventEntityType.EXTERNAL.getValue());
        setProperty("channel_id", null);
        setProperty("entity_metadata", mapper.createObjectNode().put("location", location));
        setProperty("scheduled_end_time", endTimestamp);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/scheduled-events/" + eventId);
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
