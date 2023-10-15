package org.dimas4ek.wrapper.entities.channel;

public interface GuildChannel {
    TextChannel asText();
    VoiceChannel asVoice();
}
