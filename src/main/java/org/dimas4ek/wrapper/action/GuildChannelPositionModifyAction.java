package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class GuildChannelPositionModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildChannelPositionModifyAction(String guildId, String channelId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("id", channelId);
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildChannelPositionModifyAction setPosition(int position) {
        setProperty("position", position);
        return this;
    }

    public GuildChannelPositionModifyAction lockPermissions(boolean lockPermissions) {
        setProperty("lock_permissions", lockPermissions);
        return this;
    }

    public GuildChannelPositionModifyAction setParent(String parentId) {
        setProperty("parent_id", parentId);
        return this;
    }

    public GuildChannelPositionModifyAction setParent(long parentId) {
        setParent(String.valueOf(parentId));
        return this;
    }

    public void submit() {
        if (hasChanges) {
            ApiClient.patchArray(new JSONArray().put(jsonObject), "/guilds/" + guildId + "/channels");
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
