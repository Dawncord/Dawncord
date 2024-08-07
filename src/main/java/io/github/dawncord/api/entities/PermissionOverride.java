package io.github.dawncord.api.entities;

import io.github.dawncord.api.types.ChannelPermissionType;
import io.github.dawncord.api.types.PermissionOverrideType;

import java.util.List;

/**
 * A class representing a permission override.
 */
public class PermissionOverride {
    private final String id;
    private final PermissionOverrideType type;
    private final List<ChannelPermissionType> denied;
    private final List<ChannelPermissionType> allowed;

    /**
     * Constructs a permission override with the specified parameters.
     *
     * @param id      The ID of the entity for which the permission override is applied.
     * @param type    The type of the entity (role or member).
     * @param denied  The list of denied permissions.
     * @param allowed The list of allowed permissions.
     */
    public PermissionOverride(String id, PermissionOverrideType type, List<ChannelPermissionType> denied, List<ChannelPermissionType> allowed) {
        this.id = id;
        this.type = type;
        this.denied = denied;
        this.allowed = allowed;
    }

    /**
     * Retrieves the ID of the entity for which the permission override is applied.
     *
     * @return The ID of the entity.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the type of the entity (role or member).
     *
     * @return The type of the entity.
     */
    public PermissionOverrideType getType() {
        return type;
    }

    /**
     * Retrieves the list of denied permissions.
     *
     * @return The list of denied permissions.
     */
    public List<ChannelPermissionType> getDenied() {
        return denied;
    }

    /**
     * Retrieves the list of allowed permissions.
     *
     * @return The list of allowed permissions.
     */
    public List<ChannelPermissionType> getAllowed() {
        return allowed;
    }
}
