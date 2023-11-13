package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeChannel;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class GuildWelcomeScreenModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;

    public GuildWelcomeScreenModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    public GuildWelcomeScreenModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    public GuildWelcomeScreenModifyAction setChannels(GuildWelcomeChannel... channels) {
        JSONArray channelsArray = new JSONArray();
        for (GuildWelcomeChannel channel : channels) {
            channelsArray.put(
                    new JSONObject()
                            .put("channel_id", channel.getChannel().getId())
                            .put("description", channel.getDescription())
                            .put("emoji_id", MessageUtils.isEmojiLong(channel.getEmoji()) ? channel.getEmoji() : null)
                            .put("emoji_name", !MessageUtils.isEmojiLong(channel.getEmoji()) ? channel.getEmoji() : null)
            );
        }
        jsonObject.put("welcome_channels", channelsArray);
        return this;
    }

    public GuildWelcomeScreenModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public void submit() {
        ApiClient.patch(jsonObject, "/guilds/" + guildId + "/welcome-screen");
        jsonObject.clear();
    }
}
