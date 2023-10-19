package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.Mentionable;

public interface GuildChannel extends Mentionable {
    String getId();
    long getIdLong();
    String getName();
    String getType();
    TextChannel asText();
    VoiceChannel asVoice();
}
