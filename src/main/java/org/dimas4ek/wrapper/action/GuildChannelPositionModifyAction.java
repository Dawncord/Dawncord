package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;

public class GuildChannelPositionModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    public GuildChannelPositionModifyAction(String guildId, String channelId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("id", channelId);
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
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
            ApiClient.patchArray(mapper.createArrayNode().add(jsonObject), "/guilds/" + guildId + "/channels");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
