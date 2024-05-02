package io.github.dawncord.api.entities.image;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.types.ImageFormat;

/**
 * Represents an icon associated with a role.
 */
public class RoleIcon implements Icon {
    private final String roleId;
    private final String hash;

    /**
     * Constructs a RoleIcon object associated with a role.
     *
     * @param roleId The ID of the role.
     * @param hash   The hash value of the role icon.
     */
    public RoleIcon(String roleId, String hash) {
        this.roleId = roleId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.RoleIcon(roleId, hash, format.toString());
    }
}
