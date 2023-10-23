package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.action.ChannelModifyAction;

public interface GuildChannel extends Channel {
    TextChannel asText();

    VoiceChannel asVoice();

    ChannelModifyAction modify();
}
