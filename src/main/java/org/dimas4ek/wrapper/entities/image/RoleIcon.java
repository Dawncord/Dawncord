package org.dimas4ek.wrapper.entities.image;

import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.ImageFormat;

public class RoleIcon implements Icon {
    private final String roleId;
    private final String hash;

    public RoleIcon(String roleId, String hash) {
        this.roleId = roleId;
        this.hash = hash;
    }

    @Override
    public String getUrl(ImageFormat format) {
        return Routes.Icon.RoleIcon(roleId, hash, format.toString());
    }
}
