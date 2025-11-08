package io.github.dawncord.api.entities.guild.welcomescreen;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents the welcome channel configuration for a guild.
 */
public class GuildWelcomeChannel {
    private final LazyLoader loader;
    private final JsonNode welcomeChannel;
    private final Guild guild;
    private GuildChannel channel;
    private String description;
    private String emoji;

    /**
     * Constructs a new GuildWelcomeChannel object with the provided JSON node and guild.
     *
     * @param welcomeChannel The JSON node representing the welcome channel configuration.
     * @param guild          The guild to which the welcome channel belongs.
     */
    public GuildWelcomeChannel(JsonNode welcomeChannel, Guild guild) {
        this.welcomeChannel = welcomeChannel;
        this.guild = guild;
        loader = new LazyLoader(welcomeChannel);
    }

    private GuildWelcomeChannel(String channelId, String description, String emojiIdOrName, Guild guild) {
        loader = null;
        this.welcomeChannel = null;
        this.guild = guild;
        this.channel = guild.getChannelById(channelId);
        this.description = description;
        this.emoji = emojiIdOrName;
    }

    /**
     * Retrieves the welcome channel.
     *
     * @return The welcome channel.
     */
    public GuildChannel getChannel() {
        channel = loader.loadIfExists(channel, "channel_id",
                () -> guild.getChannelById(welcomeChannel.get("channel_id").asText()));
        return channel;
    }

    /**
     * Retrieves the description of the welcome channel.
     *
     * @return The description of the welcome channel.
     */
    public String getDescription() {
        description = loader.loadString(description, "description");
        return description;
    }

    /**
     * Retrieves the emoji used in the welcome message.
     *
     * @return The emoji used in the welcome message.
     */
    public String getEmoji() {
        emoji = loader.load(emoji, () -> welcomeChannel.has("emoji_id")
                ? welcomeChannel.get("emoji_id").asText()
                : welcomeChannel.get("emoji_name").asText()
        );
        return emoji;
    }
}
