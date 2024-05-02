package io.github.dawncord.api.entities.guild;

import io.github.dawncord.api.action.GuildMemberModifyAction;
import io.github.dawncord.api.entities.IMentionable;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.GuildMemberFlag;
import io.github.dawncord.api.types.PermissionType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents a member of a guild.
 */
public interface GuildMember extends IMentionable {

    /**
     * Gets the nickname of the guild member.
     *
     * @return The nickname of the guild member.
     */
    String getNickname();

    /**
     * Gets the avatar of the guild member.
     *
     * @return The avatar of the guild member.
     */
    Avatar getAvatar();

    /**
     * Gets the flags associated with the guild member.
     *
     * @return The flags associated with the guild member.
     */
    List<GuildMemberFlag> getFlags();

    /**
     * Gets the date and time when the guild member joined the guild.
     *
     * @return The date and time when the guild member joined the guild.
     */
    ZonedDateTime getTimeJoined();

    /**
     * Gets the permissions of the guild member.
     *
     * @return The permissions of the guild member.
     */
    Set<PermissionType> getPermissions();

    /**
     * Gets the roles assigned to the guild member.
     *
     * @return The roles assigned to the guild member.
     */
    List<GuildRole> getRoles();

    /**
     * Gets the roles assigned to the guild member by name.
     *
     * @param roleName The name of the role.
     * @return The roles assigned to the guild member with the specified name.
     */
    List<GuildRole> getRolesByName(String roleName);

    /**
     * Removes a role from the guild member by its ID.
     *
     * @param roleId The ID of the role to remove.
     */
    void deleteRoleById(String roleId);

    /**
     * Removes a role from the guild member by its ID.
     *
     * @param roleId The ID of the role to remove.
     */
    void deleteRoleById(long roleId);

    /**
     * Checks if the guild member is pending.
     *
     * @return true if the guild member is pending, false otherwise.
     */
    boolean isPending();

    /**
     * Checks if the guild member is the owner of the guild.
     *
     * @return true if the guild member is the owner, false otherwise.
     */
    boolean isOwner();

    /**
     * Checks if the guild member is boosting the guild.
     *
     * @return true if the guild member is boosting, false otherwise.
     */
    boolean isBoosting();

    /**
     * Checks if the guild member is muted.
     *
     * @return true if the guild member is muted, false otherwise.
     */
    boolean isMuted();

    /**
     * Checks if the guild member is deafened.
     *
     * @return true if the guild member is deafened, false otherwise.
     */
    boolean isDeafened();

    /**
     * Gets the guild to which the guild member belongs.
     *
     * @return The guild to which the guild member belongs.
     */
    Guild getGuild();

    /**
     * Adds the guild member to the specified guild.
     *
     * @param guildId The ID of the guild.
     */
    void addToGuild(String guildId);

    /**
     * Adds the guild member to the specified guild.
     *
     * @param guildId The ID of the guild.
     */
    void addToGuild(long guildId);

    /**
     * Gets the user associated with the guild member.
     *
     * @return The user associated with the guild member.
     */
    User getUser();

    /**
     * Adds a role to the guild member by its ID.
     *
     * @param roleId The ID of the role to add.
     */
    void addRole(String roleId);

    /**
     * Adds a role to the guild member by its ID.
     *
     * @param roleId The ID of the role to add.
     */
    void addRole(long roleId);

    /**
     * Removes a role from the guild member by its ID.
     *
     * @param roleId The ID of the role to remove.
     */
    void removeRole(String roleId);

    /**
     * Removes a role from the guild member by its ID.
     *
     * @param roleId The ID of the role to remove.
     */
    void removeRole(long roleId);

    /**
     * Modifies the guild member.
     *
     * @param handler The consumer to handle the modification action.
     * @return The modification event for the guild member.
     */
    ModifyEvent<GuildMember> modify(Consumer<GuildMemberModifyAction> handler);
}
