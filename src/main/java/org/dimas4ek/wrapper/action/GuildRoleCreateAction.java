package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.PermissionType;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class GuildRoleCreateAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public GuildRoleCreateAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    public GuildRoleCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildRoleCreateAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        setProperty("permissions", value);
        return this;
    }

    public GuildRoleCreateAction setColor(Color color) {
        setProperty("color", color.getRGB());
        return this;
    }

    public GuildRoleCreateAction setHoist(boolean hoist) {
        setProperty("hoist", hoist);
        return this;
    }

    public GuildRoleCreateAction setMentionable(boolean mentionable) {
        setProperty("mentionable", mentionable);
        return this;
    }

    public void submit() {
        ApiClient.post(jsonObject, "/guilds/" + guildId + "/roles");
        jsonObject.clear();
    }
}
