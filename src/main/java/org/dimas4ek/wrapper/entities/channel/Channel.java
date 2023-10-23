package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.ChannelType;

import java.util.List;

public interface Channel extends IMentionable {
    String getId();

    long getIdLong();

    String getName();

    String getType();

    ChannelType getTypeRaw();

    Guild getGuild();

    List<String> getFlags();
}
