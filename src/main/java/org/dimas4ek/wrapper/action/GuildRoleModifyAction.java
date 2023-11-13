package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.PermissionType;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class GuildRoleModifyAction {
    private final String guildId;
    private final String roleId;
    private final JSONObject jsonObject;

    public GuildRoleModifyAction(String guildId, String roleId) {
        this.guildId = guildId;
        this.roleId = roleId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    public GuildRoleModifyAction setName(String name) {
        setProperty("name", name);
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

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/roles/" + roleId);
        jsonObject.clear();
    }
}
