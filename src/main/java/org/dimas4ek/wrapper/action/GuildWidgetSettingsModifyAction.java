package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

public class GuildWidgetSettingsModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public GuildWidgetSettingsModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    @CheckReturnValue
    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    @CheckReturnValue
    public GuildWidgetSettingsModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    @CheckReturnValue
    public GuildWidgetSettingsModifyAction setChannel(String channelId) {
        setProperty("channel_id", channelId);
        return this;
    }

    @CheckReturnValue
    public GuildWidgetSettingsModifyAction setChannel(long channelId) {
        setChannel(String.valueOf(channelId));
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/widget");
    }
}
