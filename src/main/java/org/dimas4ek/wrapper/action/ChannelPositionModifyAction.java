package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

public class ChannelPositionModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public ChannelPositionModifyAction(String guildId, String channelId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
        this.jsonObject.put("id", channelId);
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    @CheckReturnValue
    public ChannelPositionModifyAction setPosition(int position) {
        setProperty("position", position);
        return this;
    }

    @CheckReturnValue
    public ChannelPositionModifyAction lockPermissions(boolean lockPermissions) {
        setProperty("lock_permissions", lockPermissions);
        return this;
    }

    @CheckReturnValue
    public ChannelPositionModifyAction setParent(String parentId) {
        setProperty("parent_id", parentId);
        return this;
    }

    @CheckReturnValue
    public ChannelPositionModifyAction setParent(long parentId) {
        setParent(String.valueOf(parentId));
        return this;
    }

    public void submit() {
        ApiClient.patchArray(new JSONArray().put(jsonObject), "/guilds/" + guildId + "/channels");
        jsonObject.clear();
    }
}
