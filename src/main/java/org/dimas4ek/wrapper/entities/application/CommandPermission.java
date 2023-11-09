package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.types.CommandPermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

public class CommandPermission {
    private final JSONObject permission;

    public CommandPermission(JSONObject permission) {
        this.permission = permission;
    }

    public String getId() {
        return permission.getString("id");
    }

    public CommandPermissionType getType() {
        return EnumUtils.getEnumObject(permission, "type", CommandPermissionType.class);
    }

    public boolean getPermission() {
        return permission.getBoolean("permission");
    }
}
