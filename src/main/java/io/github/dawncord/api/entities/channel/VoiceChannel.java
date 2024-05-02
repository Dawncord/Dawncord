package io.github.dawncord.api.entities.channel;

/**
 * Represents a voice channel for voice communication.
 */
public interface VoiceChannel extends MessageChannel {

    /**
     * Retrieves the bitrate of the voice channel.
     *
     * @return The bitrate of the voice channel.
     */
    int getBitrate();

    /**
     * Retrieves the user limit of the voice channel.
     *
     * @return The user limit of the voice channel.
     */
    int getUserLimit();

    /**
     * Retrieves the category to which this voice channel belongs.
     *
     * @return The category to which this voice channel belongs.
     */
    GuildCategory getCategory();
}
