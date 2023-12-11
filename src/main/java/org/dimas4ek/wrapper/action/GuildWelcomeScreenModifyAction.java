package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeChannel;
import org.dimas4ek.wrapper.utils.MessageUtils;

public class GuildWelcomeScreenModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    public GuildWelcomeScreenModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public GuildWelcomeScreenModifyAction setEnabled(boolean enabled) {
        setProperty("enabled", enabled);
        return this;
    }

    public GuildWelcomeScreenModifyAction setChannels(GuildWelcomeChannel... channels) {
        ArrayNode channelsArray = mapper.createArrayNode();
        for (GuildWelcomeChannel channel : channels) {
            channelsArray.add(
                    mapper.createObjectNode()
                            .put("channel_id", channel.getChannel().getId())
                            .put("description", channel.getDescription())
                            .put("emoji_id", MessageUtils.isEmojiLong(channel.getEmoji()) ? channel.getEmoji() : null)
                            .put("emoji_name", !MessageUtils.isEmojiLong(channel.getEmoji()) ? channel.getEmoji() : null)
            );
        }
        jsonObject.set("welcome_channels", channelsArray);
        return this;
    }

    public GuildWelcomeScreenModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId + "/welcome-screen");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
