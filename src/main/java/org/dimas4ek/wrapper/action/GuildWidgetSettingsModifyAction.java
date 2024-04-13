package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;

public class GuildWidgetSettingsModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    public GuildWidgetSettingsModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildWidgetSettingsModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildWidgetSettingsModifyAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    public GuildWidgetSettingsModifyAction setChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    public GuildWidgetSettingsModifyAction setChannel(long channelId) {
        return setChannel(String.valueOf(channelId));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/widget");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
