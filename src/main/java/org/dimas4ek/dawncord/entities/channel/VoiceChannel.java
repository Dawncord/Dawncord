package org.dimas4ek.dawncord.entities.channel;

public interface VoiceChannel extends MessageChannel {
    int getBitrate();

    int getUserLimit();

    GuildCategory getCategory();
}
