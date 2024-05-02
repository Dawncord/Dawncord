package io.github.dawncord.api.entities.guild.event;

import io.github.dawncord.api.action.GuildEventModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.image.GuildEventImage;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.GuildEventEntityType;
import io.github.dawncord.api.types.GuildEventPrivacyLevel;
import io.github.dawncord.api.types.GuildEventStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a scheduled event in a guild.
 */
public interface GuildScheduledEvent extends ISnowflake {

    /**
     * Gets the name of the scheduled event.
     *
     * @return The name of the event.
     */
    String getName();

    /**
     * Gets the description of the scheduled event.
     *
     * @return The description of the event.
     */
    String getDescription();

    /**
     * Gets the guild to which the scheduled event belongs.
     *
     * @return The guild of the event.
     */
    Guild getGuild();

    /**
     * Gets the channel where the event will take place.
     *
     * @return The channel of the event.
     */
    GuildChannel getChannel();

    /**
     * Gets the creator of the scheduled event.
     *
     * @return The creator of the event.
     */
    User getCreator();

    /**
     * Gets the start timestamp of the scheduled event.
     *
     * @return The start timestamp of the event.
     */
    ZonedDateTime getStartTimestamp();

    /**
     * Gets the end timestamp of the scheduled event.
     *
     * @return The end timestamp of the event.
     */
    ZonedDateTime getEndTimestamp();

    /**
     * Gets the privacy level of the scheduled event.
     *
     * @return The privacy level of the event.
     */
    GuildEventPrivacyLevel getPrivacyLevel();

    /**
     * Gets the status of the scheduled event.
     *
     * @return The status of the event.
     */
    GuildEventStatus getStatus();

    /**
     * Gets the entity type of the scheduled event.
     *
     * @return The entity type of the event.
     */
    GuildEventEntityType getEntityType();

    /**
     * Checks if the event is scheduled to occur in a channel.
     *
     * @return True if the event is scheduled in a channel, false otherwise.
     */
    boolean inChannel();

    /**
     * Gets the ID of the entity associated with the event.
     *
     * @return The ID of the entity.
     */
    String getEntityId();

    /**
     * Gets the ID of the entity associated with the event as a long.
     *
     * @return The ID of the entity as a long.
     */
    long getEntityIdLong();

    /**
     * Gets the location of the event.
     *
     * @return The location of the event.
     */
    String getLocation();

    /**
     * Gets the count of members attending the event.
     *
     * @return The count of attending members.
     */
    int getMemberCount();

    /**
     * Gets a list of guild members attending the event.
     *
     * @return A list of attending guild members.
     */
    List<GuildMember> getGuildEventMembers();

    /**
     * Gets a limited list of guild members attending the event.
     *
     * @param limit The maximum number of members to retrieve.
     * @return A limited list of attending guild members.
     */
    List<GuildMember> getGuildEventMembers(int limit);

    /**
     * Gets the image associated with the event.
     *
     * @return The image of the event.
     */
    GuildEventImage getImage();

    /**
     * Modifies the scheduled event using the provided handler.
     *
     * @param handler The action to be performed on the event.
     * @return An event representing the modification.
     */
    ModifyEvent<GuildScheduledEvent> modify(Consumer<GuildEventModifyAction> handler);

    /**
     * Deletes the scheduled event.
     */
    void delete();
}
