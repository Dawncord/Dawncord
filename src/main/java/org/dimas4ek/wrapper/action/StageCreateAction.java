package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.StagePrivacyLevel;

public class StageCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    public StageCreateAction() {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("privacy_level", StagePrivacyLevel.GUILD_ONLY.getValue());
    }

    private StageCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public StageCreateAction setTopic(String topic) {
        return setProperty("topic", topic);
    }

    public StageCreateAction setChannelId(String channelId) {
        return setProperty("channel_id", channelId);
    }

    private StageCreateAction sendStartNotification(boolean enabled) {
        return setProperty("send_start_notification", enabled);
    }

    public StageCreateAction setGuildEventId(String guildEventId) {
        return setProperty("guild_scheduled_event_id", guildEventId);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.StageInstances());
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
