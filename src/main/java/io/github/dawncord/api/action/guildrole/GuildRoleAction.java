package io.github.dawncord.api.action.guildrole;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.IOUtils;

import java.awt.*;
import java.util.List;

public abstract class GuildRoleAction extends Action<GuildRoleAction> {
    protected final String guildId;
    protected final boolean hasRoleIcons;

    /**
     * Create a new {@link GuildRoleAction}
     *
     * @param guildId      The ID of the guild in which the role is being created.
     * @param hasRoleIcons A boolean flag indicating whether role icons are enabled.
     */
    public GuildRoleAction(String guildId, boolean hasRoleIcons) {
        super();
        this.guildId = guildId;
        this.hasRoleIcons = hasRoleIcons;
    }

    /**
     * Sets the name for the guild role.
     *
     * @param name the name to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setName(String name) {
        return setProperty("name", name);
    }


    /**
     * Sets the icon for the guild role.
     *
     * @param path the path of the icon image file
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setIcon(String path) {
        if (hasRoleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the permissions for the guild role.
     *
     * @param permissions list of permissions to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setPermissions(List<PermissionType> permissions) {
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
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setColor(Color color) {
        return setProperty("color", color.getRGB());
    }

    /**
     * Sets the hoist for the guild role.
     *
     * @param hoist the hoist value to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setHoist(boolean hoist) {
        return setProperty("hoist", hoist);
    }

    /**
     * Sets the mentionable for the guild role.
     *
     * @param mentionable the mentionable value to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleAction setMentionable(boolean mentionable) {
        return setProperty("mentionable", mentionable);
    }

}
