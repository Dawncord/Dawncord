package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.StagePrivacyLevel;

public class StageCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    public StageCreateAction() {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("privacy_level", StagePrivacyLevel.GUILD_ONLY.getValue());
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public StageCreateAction setTopic(String topic) {
        setProperty("topic", topic);
        return this;
    }

    public StageCreateAction setChannelId(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    private StageCreateAction sendStartNotification(boolean enabled) {
        jsonObject.put("send_start_notification", enabled);
        return this;
    }

    public StageCreateAction setGuildEventId(String guildEventId) {
        setProperty("guild_scheduled_event_id", guildEventId);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/stage-instances");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
