package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.types.TargetType;

import java.time.ZonedDateTime;

/**
 * Represents an invite to a guild or channel.
 */
public interface Invite {
    /**
     * Gets the guild associated with this invite.
     *
     * @return The guild associated with this invite.
     */
    Guild getGuild();

    /**
     * Gets the code of this invite.
     *
     * @return The code of this invite.
     */
    String getCode();

    /**
     * Gets the channel associated with this invite.
     *
     * @return The channel associated with this invite.
     */
    GuildChannel getChannel();

    /**
     * Gets the user who created this invite.
     *
     * @return The user who created this invite.
     */
    User getInviter();

    /**
     * Gets the target type of this invite.
     *
     * @return The target type of this invite.
     */
    TargetType getTargetType();

    /**
     * Gets the target user associated with this invite.
     *
     * @return The target user associated with this invite.
     */
    User getTargetUser();

    /**
     * Gets the target application associated with this invite.
     *
     * @return The target application associated with this invite.
     */
    Application getTargetApplication();

    /**
     * Gets the count of online members associated with this invite.
     *
     * @return The count of online members associated with this invite.
     */
    int getOnlineMembersCount();

    /**
     * Gets the total count of members associated with this invite.
     *
     * @return The total count of members associated with this invite.
     */
    int getTotalMembersCount();

    /**
     * Gets the creation timestamp of this invite.
     *
     * @return The creation timestamp of this invite.
     */
    ZonedDateTime getCreationTimestamp();

    /**
     * Gets the expiration timestamp of this invite.
     *
     * @return The expiration timestamp of this invite.
     */
    ZonedDateTime getExpirationTimestamp();

    /**
     * Gets the guild scheduled event associated with this invite.
     *
     * @return The guild scheduled event associated with this invite.
     */
    GuildScheduledEvent getGuildEvent();

    /**
     * Gets the maximum age of this invite in seconds.
     *
     * @return The maximum age of this invite in seconds.
     */
    int getMaxAge();

    /**
     * Gets the number of times this invite has been used.
     *
     * @return The number of times this invite has been used.
     */
    int getUses();

    /**
     * Checks if this invite is temporary.
     *
     * @return True if this invite is temporary, otherwise false.
     */
    boolean isTemporary();

    /**
     * Deletes this invite.
     */
    void delete();
}
