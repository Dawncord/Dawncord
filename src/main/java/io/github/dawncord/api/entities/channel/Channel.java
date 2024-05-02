package io.github.dawncord.api.entities.channel;

import io.github.dawncord.api.entities.IMentionable;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.GuildMemberFlag;

import java.util.List;

/**
 * Represents a channel in a guild or DM conversation.
 */
public interface Channel extends ISnowflake, IMentionable {

    /**
     * Gets the name of the channel.
     *
     * @return The name of the channel.
     */
    String getName();

    /**
     * Gets the type of the Channel.
     *
     * @return The type of the channel.
     */
    ChannelType getType();

    /**
     * Gets the Guild to which this channel belongs.
     *
     * @return The guild to which this channel belongs, or null if it's a DM channel.
     */
    Guild getGuild();

    /**
     * Gets the GuildMemberFlag list associated with this channel.
     *
     * @return The flags associated with this channel.
     */
    List<GuildMemberFlag> getFlags();

    /**
     * Deletes this channel.
     */
    void delete();
}