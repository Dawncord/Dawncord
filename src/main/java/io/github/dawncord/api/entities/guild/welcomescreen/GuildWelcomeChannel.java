package io.github.dawncord.api.entities.guild.welcomescreen;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents the welcome channel configuration for a guild.
 */
public class GuildWelcomeChannel {

    private final JsonNode welcomeChannel;
    private static Guild guild;
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
        GuildWelcomeChannel.guild = guild;
    }

    private GuildWelcomeChannel(String channelId, String description, String emojiIdOrName, Guild guild) {
        this.welcomeChannel = null;
        GuildWelcomeChannel.guild = guild;
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
        if (channel == null) {
            channel = guild.getChannelById(welcomeChannel.get("channel_id").asText());
        }
        return channel;
    }

    /**
     * Retrieves the description of the welcome channel.
     *
     * @return The description of the welcome channel.
     */
    public String getDescription() {
        if (description == null) {
            description = welcomeChannel.get("description").asText();
        }
        return description;
    }

    /**
     * Retrieves the emoji used in the welcome message.
     *
     * @return The emoji used in the welcome message.
     */
    public String getEmoji() {
        if (emoji == null) {
            emoji = welcomeChannel.has("emoji_id")
                    ? welcomeChannel.get("emoji_id").asText()
                    : welcomeChannel.get("emoji_name").asText();
        }
        return emoji;
    }

    /**
     * Constructs a new GuildWelcomeChannel object with the provided channel ID, description, emoji, and guild.
     * This is a static factory method.
     *
     * @param channelId     The ID of the welcome channel.
     * @param description   The description of the welcome channel.
     * @param emojiIdOrName The ID or name of the emoji used in the welcome message.
     * @return A GuildWelcomeChannel object.
     */
    public static GuildWelcomeChannel of(String channelId, String description, String emojiIdOrName) {
        return new GuildWelcomeChannel(channelId, description, emojiIdOrName, guild);
    }
}
