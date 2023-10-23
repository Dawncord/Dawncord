package org.dimas4ek.wrapper.entities.channel;

public interface VoiceChannel extends MessageChannel {
    int getBitrate();

    int getUserLimit();

    GuildCategory getCategory();
}
