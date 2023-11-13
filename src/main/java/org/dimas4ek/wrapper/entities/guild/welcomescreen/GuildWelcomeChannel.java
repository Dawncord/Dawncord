package org.dimas4ek.wrapper.entities.guild.welcomescreen;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

public class GuildWelcomeChannel {
    private final JSONObject channel;

    public GuildWelcomeChannel(JSONObject channel) {
        this.channel = channel;
    }

    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channel.getString("channel_id")));
    }

    public String getDescription() {
        return channel.optString("description", null);
    }

    public String getEmoji() {
        return channel.optString("emoji_id", channel.optString("emoji_name", null));
    }

    public static GuildWelcomeChannel of(String channelId, String description, String emojiIdOrName) {
        JSONObject json = new JSONObject();
        json.put("channel_id", channelId);
        json.put("description", description);
        json.put("emoji_id", MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
        json.put("emoji_name", !MessageUtils.isEmojiLong(emojiIdOrName) ? emojiIdOrName : null);
        return new GuildWelcomeChannel(json);
    }
}
