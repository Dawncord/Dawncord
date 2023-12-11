package org.dimas4ek.wrapper.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.types.CommandPermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;

public class CommandPermission implements ISnowflake {
    private final JsonNode permission;
    private String id;
    private CommandPermissionType type;
    private Boolean isPermission;

    public CommandPermission(JsonNode permission) {
        this.permission = permission;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = permission.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public CommandPermissionType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(permission, "type", CommandPermissionType.class);
        }
        return type;
    }

    public boolean isPermission() {
        if (isPermission == null) {
            isPermission = permission.get("permission").asBoolean();
        }
        return isPermission;
    }
}
