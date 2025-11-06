package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents metadata associated with an action, such as moderation actions.
 */
public class ActionMetadata {
    private final LazyLoader loader;
    private final JsonNode metadata;
    private final Guild guild;
    private GuildChannel channel;
    private Integer duration;
    private String customMessage;

    /**
     * Constructs an ActionMetadata object with the provided JSON node and guild.
     *
     * @param metadata The JSON node representing the metadata.
     * @param guild    The guild associated with the metadata.
     */
    public ActionMetadata(JsonNode metadata, Guild guild) {
        this.metadata = metadata;
        this.guild = guild;
        loader = new LazyLoader(metadata);
    }

    /**
     * Retrieves the channel associated with the action.
     *
     * @return The channel associated with the action.
     */
    public GuildChannel getChannel() {
        channel = loader.load(channel, () -> guild.getChannelById(metadata.get("channel_id").asText()));
        return channel;
    }

    /**
     * Retrieves the duration of the action in seconds.
     *
     * @return The duration of the action in seconds.
     */
    public int getDuration() {
        duration = loader.loadInt(duration, "duration_seconds");
        return duration;
    }

    /**
     * Retrieves the custom message associated with the action.
     *
     * @return The custom message associated with the action, or null if not available.
     */
    public String getCustomMessage() {
        customMessage = loader.loadString(customMessage, "custom_message");
        return customMessage;
    }
}
