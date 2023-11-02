package org.dimas4ek.wrapper.entities.role;

import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.types.PermissionType;

import java.awt.*;
import java.util.List;

public interface GuildRole extends IMentionable {
    String getId();

    String getName();

    String getDescription();

    List<PermissionType> getPermissions();

    int getPosition();

    Color getColor();

    boolean isPinned();

    boolean isManaged();

    boolean isMentionable();

    Tags getTags();
}
