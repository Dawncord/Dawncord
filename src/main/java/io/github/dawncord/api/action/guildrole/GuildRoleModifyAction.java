package io.github.dawncord.api.action.guildrole;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.role.GuildRole;

/**
 * Represents an action for updating a guild role.
 *
 * @see GuildRole
 */
public class GuildRoleModifyAction extends GuildRoleAction {
    private final String roleId;

    /**
     * Create a new {@link GuildRoleModifyAction}
     *
     * @param guildId      The ID of the guild in which the role is being modified.
     * @param roleId       The ID of the role being modified.
     * @param hasRoleIcons A boolean flag indicating whether role icons are enabled.
     */
    public GuildRoleModifyAction(String guildId, String roleId, boolean hasRoleIcons) {
        this.roleId = roleId;
        super(guildId, hasRoleIcons);
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Role.Get(guildId, roleId));
            hasChanges = false;
        }
    }
}
