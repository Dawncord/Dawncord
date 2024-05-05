package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.widget.GuildWidgetSettings;

/**
 * Represents an action for updating a guild widget settings.
 *
 * @see GuildWidgetSettings
 */
public class GuildWidgetSettingsModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildWidgetSettingsModifyAction}
     *
     * @param guildId The ID of the guild in which the guild widget settings are being modified.
     */
    public GuildWidgetSettingsModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildWidgetSettingsModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the enabled for the guild widget settings.
     *
     * @param enabled the new value for the enabled property
     * @return the modified GuildWidgetSettingsModifyAction object
     */
    public GuildWidgetSettingsModifyAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the channel ID for the guild widget settings.
     *
     * @param channelId the ID of the channel to set
     * @return the modified GuildWidgetSettingsModifyAction object
     */
    public GuildWidgetSettingsModifyAction setChannel(String channelId) {
        return setProperty("channel_id", channelId);
    }

    /**
     * Sets the channel ID for the guild widget settings.
     *
     * @param channelId the ID of the channel to set
     * @return the modified GuildWidgetSettingsModifyAction object
     */
    public GuildWidgetSettingsModifyAction setChannel(long channelId) {
        return setChannel(String.valueOf(channelId));
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Widget.Settings(guildId));
            hasChanges = false;
        }
    }
}
