package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.entities.IMentionable;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.GuildMemberFlag;

import java.util.List;

public interface Channel extends ISnowflake, IMentionable {
    String getName();

    ChannelType getType();

    Guild getGuild();

    List<GuildMemberFlag> getFlags();

    void delete();
}
