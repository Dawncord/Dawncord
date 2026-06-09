package io.github.dawncord.api.entities;

import io.github.dawncord.api.types.PermissionOverrideType;
import io.github.dawncord.api.types.PermissionType;

import java.util.List;

/**
 * A class representing a permission override.
 */
public record PermissionOverride(String id, PermissionOverrideType type, List<PermissionType> denied,
                                 List<PermissionType> allowed) {
    /**
     * Constructs a permission override with the specified parameters.
     *
     * @param id      The ID of the entity for which the permission override is applied.
     * @param type    The type of the entity (role or member).
     * @param denied  The list of denied permissions.
     * @param allowed The list of allowed permissions.
     */
    public PermissionOverride {
    }

    /**
     * Retrieves the ID of the entity for which the permission override is applied.
     *
     * @return The ID of the entity.
     */
    @Override
    public String id() {
        return id;
    }

    /**
     * Retrieves the type of the entity (role or member).
     *
     * @return The type of the entity.
     */
    @Override
    public PermissionOverrideType type() {
        return type;
    }

    /**
     * Retrieves the list of denied permissions.
     *
     * @return The list of denied permissions.
     */
    @Override
    public List<PermissionType> denied() {
        return denied;
    }

    /**
     * Retrieves the list of allowed permissions.
     *
     * @return The list of allowed permissions.
     */
    @Override
    public List<PermissionType> allowed() {
        return allowed;
    }
}
