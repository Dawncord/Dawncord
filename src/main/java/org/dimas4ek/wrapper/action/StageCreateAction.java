package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.StagePrivacyLevel;
import org.json.JSONObject;

public class StageCreateAction {
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public StageCreateAction() {
        this.jsonObject = new JSONObject();
        this.jsonObject.put("privacy_level", StagePrivacyLevel.GUILD_ONLY.getValue());
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
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
        jsonObject.clear();
    }
}
