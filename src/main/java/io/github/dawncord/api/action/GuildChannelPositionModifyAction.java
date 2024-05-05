package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;

/**
 * Represents an action for updating a guild channel position.
 *
 * @see GuildChannel
 */
public class GuildChannelPositionModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildChannelPositionModifyAction}
     *
     * @param guildId   The ID of the guild where the channel belongs.
     * @param channelId The ID of the channel whose position will be modified.
     */
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

    /**
     * Sets the position property for the guild channel
     *
     * @param position the position to set
     * @return the modified GuildChannelPositionModifyAction object
     */
    public GuildChannelPositionModifyAction setPosition(int position) {
        return setProperty("position", position);
    }

    /**
     * Sets the lock_permissions property for the guild channel
     *
     * @param lockPermissions the lock_permissions to set
     * @return the modified GuildChannelPositionModifyAction object
     */
    public GuildChannelPositionModifyAction lockPermissions(boolean lockPermissions) {
        return setProperty("lock_permissions", lockPermissions);
    }

    /**
     * Sets the parent ID property for the guild channel
     *
     * @param parentId the ID of the parent channel to set
     * @return the modified GuildChannelPositionModifyAction object
     */
    public GuildChannelPositionModifyAction setParent(String parentId) {
        return setProperty("parent_id", parentId);
    }

    /**
     * Sets the parent ID property for the guild channel
     *
     * @param parentId the ID of the parent channel to set
     * @return the modified GuildChannelPositionModifyAction object
     */
    public GuildChannelPositionModifyAction setParent(long parentId) {
        return setParent(String.valueOf(parentId));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(mapper.createArrayNode().add(jsonObject), Routes.Guild.Channels(guildId));
            hasChanges = false;
        }
    }
}
