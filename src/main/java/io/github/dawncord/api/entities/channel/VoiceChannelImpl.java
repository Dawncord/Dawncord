package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Implementation of a voice channel for voice communication.
 */
public class VoiceChannelImpl extends MessageChannelImpl implements VoiceChannel {
    private final JsonNode channel;
    private final Guild guild;
    private Integer userLimit;
    private Integer bitrate;
    private GuildCategory category;

    /**
     * Constructs a new VoiceChannelImpl object.
     *
     * @param channel The JSON representation of the channel.
     * @param guild   The guild to which this channel belongs.
     */
    public VoiceChannelImpl(JsonNode channel, Guild guild) {
        super(channel, guild);
        this.channel = channel;
        this.guild = guild;
    }

    @Override
    public int getUserLimit() {
        if (userLimit != null && channel.has("user_limit")) {
            userLimit = channel.get("user_limit").asInt();
        }
        return userLimit != null ? userLimit : 0;
    }

    @Override
    public int getBitrate() {
        if (bitrate != null && channel.has("bitrate")) {
            bitrate = channel.get("bitrate").asInt();
        }
        return bitrate != null ? bitrate : 0;
    }

    @Override
    public GuildCategory getCategory() {
        if (category == null) {
            if (channel.has("parent_id")) {
                category = guild.getCategoryById(channel.get("parent_id").asText());
            }
        }
        return category;
    }
}
