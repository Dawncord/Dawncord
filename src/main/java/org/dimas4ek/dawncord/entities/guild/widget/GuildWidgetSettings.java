package org.dimas4ek.dawncord.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.action.GuildWidgetSettingsModifyAction;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.utils.ActionExecutor;

import java.util.function.Consumer;

public class GuildWidgetSettings {
    private final JsonNode settings;
    private final Guild guild;
    private Boolean isEnabled;
    private GuildChannel channel;

    public GuildWidgetSettings(JsonNode settings, Guild guild) {
        this.settings = settings;
        this.guild = guild;
    }

    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = settings.get("enabled").asBoolean();
        }
        return isEnabled;
    }

    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(settings.get("channel_id").asText());
        }
        return channel;
    }

    public ModifyEvent<GuildWidgetSettings> modify(Consumer<GuildWidgetSettingsModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getWidgetSettings());
    }

}
