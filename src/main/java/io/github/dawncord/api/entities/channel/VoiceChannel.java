package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a voice channel for voice communication.
 */
public class VoiceChannel extends MessageChannel {
    private final LazyLoader loader;
    private final JsonNode channel;
    private final Guild guild;
    private Integer userLimit;
    private Integer bitrate;
    private GuildCategory category;

    /**
     * Constructs a new VoiceChannel object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public VoiceChannel(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
        loader = new LazyLoader(channel);
    }

    public int getUserLimit() {
        userLimit = loader.loadInt(userLimit, "user_limit");
        return userLimit != null ? userLimit : 0;
    }

    public int getBitrate() {
        bitrate = loader.loadInt(bitrate, "bitrate");
        return bitrate != null ? bitrate : 0;
    }

    public GuildCategory getCategory() {
        category = loader.loadIfExists(category, "parent_id", () -> guild.getCategoryById(channel.get("parent_id").asText()));
        return category;
    }
}
