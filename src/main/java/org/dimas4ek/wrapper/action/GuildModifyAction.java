package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.types.*;
import org.json.JSONObject;

public class GuildModifyAction {
    private final String guildId;
    private final JSONObject jsonObject;
    private boolean hasChanges = false;

    public GuildModifyAction(String guildId) {
        this.guildId = guildId;
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    public GuildModifyAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public GuildModifyAction setOwner(User user) {
        setProperty("owner_id", user.getId());
        return this;
    }

    public GuildModifyAction setAfkChannel(GuildChannel channel) {
        setProperty("afk_channel_id", channel.getId());
        return this;
    }

    public GuildModifyAction setAfkTimeout(int seconds) {
        setProperty("afk_timeout", seconds);
        return this;
    }

    public GuildModifyAction setSystemChannel(GuildChannel channel) {
        setProperty("system_channel_id", channel.getId());
        return this;
    }

    public GuildModifyAction setSystemChannelFlags(SystemChannelFlag flag, SystemChannelFlag... flags) {
        int value = flag.getValue();
        for (SystemChannelFlag f : flags) {
            value |= f.getValue();
        }
        setProperty("system_channel_flags", value);
        return this;
    }

    public GuildModifyAction setRulesChannel(GuildChannel channel) {
        setProperty("rules_channel_id", channel.getId());
        return this;
    }

    public GuildModifyAction setModChannel(GuildChannel channel) {
        setProperty("public_updates_channel_id", channel.getId());
        return this;
    }

    public GuildModifyAction setAlertsChannel(GuildChannel channel) {
        setProperty("safety_alerts_channel_id", channel.getId());
        return this;
    }

    public GuildModifyAction setLocale(Locale locale) {
        setProperty("preferred_locale", locale.getLocaleCode());
        return this;
    }

    public GuildModifyAction setBoostProgressBar(boolean enabled) {
        setProperty("premium_progress_bar_enabled", enabled);
        return this;
    }

    public GuildModifyAction setVerificationLevel(VerificationLevel level) {
        setProperty("verification_level", level.getValue());
        return this;
    }

    public GuildModifyAction setNotificationLevel(NotificationLevel level) {
        setProperty("default_message_notifications", level.getValue());
        return this;
    }

    public GuildModifyAction setContentFilterLevel(ContentFilterLevel level) {
        setProperty("explicit_content_filter", level.getValue());
        return this;
    }

    public GuildModifyAction setIcon() {
        //TODO set icon
        return null;
    }

    public GuildModifyAction setSplash() {
        //TODO set splash
        return null;
    }

    public GuildModifyAction setDiscoverySplash() {
        //TODO set discovery splash
        return null;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, "/guilds/" + guildId);
            hasChanges = false;
        }
        jsonObject.clear();
    }
}

