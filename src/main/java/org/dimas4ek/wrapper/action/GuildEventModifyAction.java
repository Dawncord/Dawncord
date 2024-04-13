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

    private GuildEventModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildEventModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildEventModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    public GuildEventModifyAction setLocation(String location) {
        return setProperty("location", location);
    }

    public GuildEventModifyAction setImage(String path) {
        return setProperty("image", IOUtils.setImageData(path));
    }

    public GuildEventModifyAction setStatus(GuildEventStatus status) {
        return setProperty("status", status.getValue());
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
