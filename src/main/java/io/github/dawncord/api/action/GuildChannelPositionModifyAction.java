package io.github.dawncord.api.action;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;

/**
 * Represents an action for updating a guild channel position.
 *
 * @see GuildChannel
 */
public class GuildChannelPositionModifyAction extends Action<GuildChannelPositionModifyAction> {
    private final String guildId;

    /**
     * Create a new {@link GuildChannelPositionModifyAction}
     *
     * @param guildId   The ID of the guild where the channel belongs.
     * @param channelId The ID of the channel whose position will be modified.
     */
    public GuildChannelPositionModifyAction(String guildId, String channelId) {
        super();
        this.guildId = guildId;
        this.jsonObject.put("id", channelId);
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

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(mapper.createArrayNode().add(jsonObject), Routes.Guild.Channels(guildId));
            hasChanges = false;
        }
    }
}
