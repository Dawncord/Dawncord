package org.dimas4ek.dawncord.entities.guild.role;

import org.dimas4ek.dawncord.action.GuildRoleModifyAction;
import org.dimas4ek.dawncord.entities.IMentionable;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.image.RoleIcon;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.PermissionType;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public interface GuildRole extends ISnowflake, IMentionable {
    String getName();

    RoleIcon getIcon();

    List<PermissionType> getPermissions();

    int getPosition();

    Color getColor();

    boolean isPinned();

    boolean isManaged();

    boolean isMentionable();

    Tags getTags();

    void setPosition(int position);

    ModifyEvent<GuildRole> modify(Consumer<GuildRoleModifyAction> handler);

    void delete();
}
