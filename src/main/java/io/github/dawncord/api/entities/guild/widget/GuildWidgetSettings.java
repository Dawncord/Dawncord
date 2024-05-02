package io.github.dawncord.api.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.action.GuildWidgetSettingsModifyAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.utils.ActionExecutor;

import java.util.function.Consumer;

/**
 * Represents the settings of a guild's widget.
 */
public class GuildWidgetSettings {
    private final JsonNode settings;
    private final Guild guild;
    private Boolean isEnabled;
    private GuildChannel channel;

    /**
     * Constructs a new GuildWidgetSettings object with the provided JSON node and guild.
     *
     * @param settings The JSON node representing the widget settings.
     * @param guild    The guild to which the settings belong.
     */
    public GuildWidgetSettings(JsonNode settings, Guild guild) {
        this.settings = settings;
        this.guild = guild;
    }

    /**
     * Checks if the guild's widget is enabled.
     *
     * @return true if the widget is enabled, false otherwise.
     */
    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = settings.get("enabled").asBoolean();
        }
        return isEnabled;
    }

    /**
     * Retrieves the channel associated with the guild's widget.
     *
     * @return The channel associated with the widget.
     */
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(settings.get("channel_id").asText());
        }
        return channel;
    }

    /**
     * Modifies the settings of the guild's widget using the provided handler.
     *
     * @param handler The handler to modify the widget settings.
     * @return A ModifyEvent representing the modification action.
     */
    public ModifyEvent<GuildWidgetSettings> modify(Consumer<GuildWidgetSettingsModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getWidgetSettings());
    }
}

