package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.IOUtils;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class GuildRoleModifyAction {
    private final String guildId;
    private final String roleId;
    private final boolean roleIcons;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildRoleModifyAction(String guildId, String roleId, boolean hasRoleIcons) {
        this.guildId = guildId;
        this.roleId = roleId;
        this.roleIcons = hasRoleIcons;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
        hasChanges = true;
    }

    public GuildRoleModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildRoleModifyAction setIcon(String path) {
        if (roleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildRoleModifyAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        setProperty("permissions", value);
        return this;
    }

    public GuildRoleModifyAction setColor(Color color) {
        setProperty("color", color.getRGB());
        return this;
    }

    public GuildRoleModifyAction setHoist(boolean hoist) {
        setProperty("hoist", hoist);
        return this;
    }

    public GuildRoleModifyAction setMentionable(boolean mentionable) {
        setProperty("mentionable", mentionable);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/roles/" + roleId);
            hasChanges = false;
        }
        jsonObject.clear();
    }
}
