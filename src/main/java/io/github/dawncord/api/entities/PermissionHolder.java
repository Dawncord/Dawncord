package io.github.dawncord.api.entities;

import io.github.dawncord.api.types.PermissionType;

import java.util.Collection;
import java.util.List;


/**
 * Represents a permission holder.
 */
public interface PermissionHolder {
    /**
     * Gets the permissions of the permission holder.
     *
     * @return The permissions of the permission holder.
     */
    Collection<PermissionType> getPermissions();

    /**
     * Checks if the permission holder has the specified permission.
     *
     * @param permission the permission to check for
     * @return true if the permission holder has the permission, false otherwise
     */
    default boolean hasPermission(PermissionType... permission) {
        return getPermissions().containsAll(List.of(permission));
    }

    /**
     * Checks if the permission holder has any of the specified permissions.
     *
     * @param permissions the permissions to check for
     * @return true if the permission holder has any of the permissions, false otherwise
     */
    default boolean hasPermission(Collection<PermissionType> permissions) {
        return getPermissions().containsAll(permissions);
    }
}
