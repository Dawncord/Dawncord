package io.github.dawncord.api.entities.guild.welcomescreen;

/**
 * Represents data used to configure a guild welcome screen channel.
 */
public class GuildWelcomeChannelData {
    private String channelId;
    private String description;
    private String emojiIdOrName;

    public GuildWelcomeChannelData(String channelId, String description, String emojiIdOrName) {
        this.channelId = channelId;
        this.description = description;
        this.emojiIdOrName = emojiIdOrName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmojiIdOrName() {
        return emojiIdOrName;
    }

    public void setEmojiIdOrName(String emojiIdOrName) {
        this.emojiIdOrName = emojiIdOrName;
    }
}
