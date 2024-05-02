package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents metadata associated with an action, such as moderation actions.
 */
public class ActionMetadata {
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
    }

    /**
     * Retrieves the channel associated with the action.
     *
     * @return The channel associated with the action.
     */
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(metadata.get("channel_id").asText());
        }
        return channel;
    }

    /**
     * Retrieves the duration of the action in seconds.
     *
     * @return The duration of the action in seconds.
     */
    public int getDuration() {
        if (duration == null) {
            duration = metadata.get("duration_seconds").asInt();
        }
        return duration;
    }

    /**
     * Retrieves the custom message associated with the action.
     *
     * @return The custom message associated with the action, or null if not available.
     */
    public String getCustomMessage() {
        if (customMessage == null) {
            customMessage = metadata.has("custom_message") && metadata.hasNonNull("custom_message")
                    ? metadata.get("custom_message").asText()
                    : null;
        }
        return customMessage;
    }
}
