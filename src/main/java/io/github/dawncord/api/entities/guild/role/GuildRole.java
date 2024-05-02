package io.github.dawncord.api.entities.guild.role;

import io.github.dawncord.api.action.GuildRoleModifyAction;
import io.github.dawncord.api.entities.IMentionable;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.image.RoleIcon;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.PermissionType;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a role within a guild.
 */
public interface GuildRole extends ISnowflake, IMentionable {

    /**
     * Retrieves the name of the role.
     *
     * @return The name of the role.
     */
    String getName();

    /**
     * Retrieves the icon of the role.
     *
     * @return The icon of the role.
     */
    RoleIcon getIcon();

    /**
     * Retrieves the permissions granted to the role.
     *
     * @return The permissions granted to the role.
     */
    List<PermissionType> getPermissions();

    /**
     * Retrieves the position of the role in the role hierarchy.
     *
     * @return The position of the role.
     */
    int getPosition();

    /**
     * Retrieves the color of the role.
     *
     * @return The color of the role.
     */
    Color getColor();

    /**
     * Checks if the role is pinned.
     *
     * @return true if the role is pinned, false otherwise.
     */
    boolean isPinned();

    /**
     * Checks if the role is managed by an integration or bot.
     *
     * @return true if the role is managed, false otherwise.
     */
    boolean isManaged();

    /**
     * Checks if the role is mentionable.
     *
     * @return true if the role is mentionable, false otherwise.
     */
    boolean isMentionable();

    /**
     * Retrieves the tags associated with the role.
     *
     * @return The tags associated with the role.
     */
    Tags getTags();

    /**
     * Sets the position of the role in the role hierarchy.
     *
     * @param position The new position of the role.
     */
    void setPosition(int position);

    /**
     * Modifies the role using the provided action handler.
     *
     * @param handler The action handler for modifying the role.
     * @return A ModifyEvent representing the modification event.
     */
    ModifyEvent<GuildRole> modify(Consumer<GuildRoleModifyAction> handler);

    /**
     * Deletes the role.
     */
    void delete();
}
