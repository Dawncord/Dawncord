package org.dimas4ek.wrapper.entities.role;

import org.dimas4ek.wrapper.action.GuildRoleModifyAction;
import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.types.PermissionType;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

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

    void setPosition(int position);

    void modify(Consumer<GuildRoleModifyAction> handler);

    void delete();
}
