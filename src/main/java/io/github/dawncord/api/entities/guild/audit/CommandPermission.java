package io.github.dawncord.api.entities.guild.audit;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.types.CommandPermissionType;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a command permission.
 * CommandPermission is a class providing methods to access properties of a command permission.
 */
public class CommandPermission implements ISnowflake {
    private final LazyLoader loader;
    private String id;
    private CommandPermissionType type;
    private Boolean isPermission;

    /**
     * Constructs a CommandPermission object with the provided JSON node.
     *
     * @param permission The JSON node representing the command permission.
     */
    public CommandPermission(JsonNode permission) {
        loader = new LazyLoader(permission);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    /**
     * Retrieves the type of the command permission.
     *
     * @return The type of the command permission.
     */
    public CommandPermissionType getType() {
        type = loader.loadEnumObject(type, "type", CommandPermissionType.class);
        return type;
    }

    /**
     * Checks if the permission is granted.
     *
     * @return True if the permission is granted, otherwise false.
     */
    public boolean isPermission() {
        isPermission = loader.loadBoolean(isPermission, "permission");
        return isPermission;
    }
}
