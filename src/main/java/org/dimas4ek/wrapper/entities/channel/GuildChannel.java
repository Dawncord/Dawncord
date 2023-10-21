package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ChannelModifyAction;
import org.dimas4ek.wrapper.entities.IMentionable;
import org.dimas4ek.wrapper.types.ChannelType;

public interface GuildChannel extends IMentionable {
    TextChannel asText();

    VoiceChannel asVoice();

    String getId();

    long getIdLong();

    String getName();

    String getType();

    ChannelType getTypeRaw();

    ChannelModifyAction modify();

    GuildCategory getCategory();
}
