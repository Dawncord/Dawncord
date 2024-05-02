package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.IOUtils;

import java.awt.*;
import java.util.List;

/**
 * Represents an action for updating a guild role.
 *
 * @see GuildRole
 */
public class GuildRoleModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String roleId;
    private final boolean roleIcons;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildRoleModifyAction}
     *
     * @param guildId      The ID of the guild in which the role is being modified.
     * @param roleId       The ID of the role being modified.
     * @param hasRoleIcons A boolean flag indicating whether role icons are enabled.
     */
    public GuildRoleModifyAction(String guildId, String roleId, boolean hasRoleIcons) {
        this.guildId = guildId;
        this.roleId = roleId;
        this.roleIcons = hasRoleIcons;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildRoleModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the guild role.
     *
     * @param name the new name of the guild role
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setName(String name) {
        return setProperty("name", name);

    }

    /**
     * Sets the icon for the guild role.
     *
     * @param path the path of the icon image file
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setIcon(String path) {
        if (roleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the permissions for the guild role.
     *
     * @param permissions the list of permissions to set
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        return setProperty("permissions", value);
    }

    /**
     * Sets the color for the guild role.
     *
     * @param color the color to set
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setColor(Color color) {
        return setProperty("color", color.getRGB());
    }

    /**
     * Sets the hoist property for the guild role.
     *
     * @param hoist the new value of the hoist property
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setHoist(boolean hoist) {
        return setProperty("hoist", hoist);
    }

    /**
     * Sets the mentionable for the guild role.
     *
     * @param mentionable the new value of the mentionable property
     * @return the modified GuildRoleModifyAction object
     */
    public GuildRoleModifyAction setMentionable(boolean mentionable) {
        return setProperty("mentionable", mentionable);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Role.Get(guildId, roleId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
