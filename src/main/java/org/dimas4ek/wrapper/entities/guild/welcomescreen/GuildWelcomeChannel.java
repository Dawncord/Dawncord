package org.dimas4ek.wrapper.entities.guild.welcomescreen;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;

public class GuildWelcomeChannel {
    private final JsonNode welcomeChannel;
    private static Guild guild;
    private GuildChannel channel;
    private String description;
    private String emoji;

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

    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(welcomeChannel.get("channel_id").asText());
        }
        return channel;
    }

    public String getDescription() {
        if (description == null) {
            description = welcomeChannel.get("description").asText();
        }
        return description;
    }

    public String getEmoji() {
        if (emoji == null) {
            emoji = welcomeChannel.has("emoji_id")
                    ? welcomeChannel.get("emoji_id").asText()
                    : welcomeChannel.get("emoji_name").asText();
        }
        return emoji;
    }

    public static GuildWelcomeChannel of(String channelId, String description, String emojiIdOrName) {
        return new GuildWelcomeChannel(channelId, description, emojiIdOrName, guild);
    }
}
