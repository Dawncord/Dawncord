package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;

public class GuildWidgetSettingsModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildWidgetSettingsModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildWidgetSettingsModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    public GuildWidgetSettingsModifyAction setChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    public GuildWidgetSettingsModifyAction setChannel(long channelId) {
        setChannel(String.valueOf(channelId));
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/widget");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
