package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.action.GuildMemberModifyAction;
import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.guild.role.GuildRole;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.GuildMemberFlag;
import org.dimas4ek.wrapper.types.PermissionType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface GuildMember extends IMentionable {
    String getNickname();

    Avatar getAvatar();

    List<GuildMemberFlag> getFlags();

    ZonedDateTime getTimeJoined();

    Set<PermissionType> getPermissions();

    List<GuildRole> getRoles();

    List<GuildRole> getRolesByName(String roleName);

    void deleteRoleById(String roleId);

    void deleteRoleById(long roleId);

    boolean isPending();

    boolean isOwner();

    boolean isBoosting();

    boolean isMuted();

    boolean isDeafened();

    Guild getGuild();

    void addToGuild(String guildId);

    void addToGuild(long guildId);

    User getUser();

    void addRole(String roleId);

    void addRole(long roleId);

    void removeRole(String roleId);

    void removeRole(long roleId);

    void modify(Consumer<GuildMemberModifyAction> handler);
}
