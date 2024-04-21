package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;

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

    private GuildChannelPositionModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildChannelPositionModifyAction setPosition(int position) {
        return setProperty("position", position);
    }

    public GuildChannelPositionModifyAction lockPermissions(boolean lockPermissions) {
        return setProperty("lock_permissions", lockPermissions);
    }

    public GuildChannelPositionModifyAction setParent(String parentId) {
        return setProperty("parent_id", parentId);
    }

    public GuildChannelPositionModifyAction setParent(long parentId) {
        return setParent(String.valueOf(parentId));
    }

    public void submit() {
        if (hasChanges) {
            ApiClient.patchArray(mapper.createArrayNode().add(jsonObject), Routes.Guild.Channels(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
