package org.dimas4ek.wrapper.entities.guild.widget;

import org.dimas4ek.wrapper.action.GuildWidgetSettingsModifyAction;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class GuildWidgetSettings {
    private final Guild guild;
    private final JSONObject settings;

    public GuildWidgetSettings(Guild guild, JSONObject settings) {
        this.guild = guild;
        this.settings = settings;
    }

    public boolean isEnabled() {
        return settings.getBoolean("enabled");
    }

    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + settings.getString("channel_id")));
    }

    public GuildWidgetSettingsModifyAction modify() {
        return new GuildWidgetSettingsModifyAction(guild.getId());
    }

}
