package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.welcomescreen.GuildWelcomeChannel;
import io.github.dawncord.api.entities.guild.welcomescreen.GuildWelcomeScreen;
import io.github.dawncord.api.utils.MessageUtils;

/**
 * Represents an action for updating a guild welcome screen.
 *
 * @see GuildWelcomeScreen
 */
public class GuildWelcomeScreenModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildWelcomeScreenModifyAction}
     *
     * @param guildId The ID of the guild in which the welcome screen is being modified.
     */
    public GuildWelcomeScreenModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildWelcomeScreenModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the enabled for the guild welcome screen.
     *
     * @param enabled the boolean value to set the enabled property to
     * @return the modified GuildWelcomeScreenModifyAction object
     */
    public GuildWelcomeScreenModifyAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the channels for the guild welcome screen.
     *
     * @param channels the GuildWelcomeChannel objects to set
     * @return the modified GuildWelcomeScreenModifyAction object
     */
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

    /**
     * Sets the description for the guild welcome screen.
     *
     * @param description the description to set
     * @return the modified GuildWelcomeScreenModifyAction object
     */
    public GuildWelcomeScreenModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.WelcomeScreen(guildId));
            hasChanges = false;
        }
    }
}
