package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.event.ButtonEvent;
import org.dimas4ek.dawncord.event.SelectMenuEvent;
import org.dimas4ek.dawncord.event.SlashCommandEvent;
import org.dimas4ek.dawncord.types.*;
import org.dimas4ek.dawncord.utils.IOUtils;

import java.util.List;

public class GuildModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final List<String> guildFeatures;
    private boolean hasChanges = false;

    public GuildModifyAction(String guildId, List<String> guildFeatures) {
        this.guildId = guildId;
        this.guildFeatures = guildFeatures;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    public GuildModifyAction setOwner(User user) {
        return setProperty("owner_id", user.getId());
    }

    public GuildModifyAction setAfkChannel(GuildChannel channel) {
        return setProperty("afk_channel_id", channel.getId());
    }

    public GuildModifyAction setAfkTimeout(int seconds) {
        return setProperty("afk_timeout", seconds);
    }

    public GuildModifyAction setSystemChannel(GuildChannel channel) {
        return setProperty("system_channel_id", channel.getId());
    }

    public GuildModifyAction setSystemChannelFlags(SystemChannelFlag flag, SystemChannelFlag... flags) {
        int value = flag.getValue();
        for (SystemChannelFlag f : flags) {
            value |= f.getValue();
        }
        return setProperty("system_channel_flags", value);
    }

    public GuildModifyAction setRulesChannel(GuildChannel channel) {
        return setProperty("rules_channel_id", channel.getId());
    }

    public GuildModifyAction setModChannel(GuildChannel channel) {
        return setProperty("public_updates_channel_id", channel.getId());
    }

    public GuildModifyAction setAlertsChannel(GuildChannel channel) {
        return setProperty("safety_alerts_channel_id", channel.getId());
    }

    public GuildModifyAction setLocale(Locale locale) {
        return setProperty("preferred_locale", locale.getLocaleCode());
    }

    public GuildModifyAction setBoostProgressBar(boolean enabled) {
        return setProperty("premium_progress_bar_enabled", enabled);
    }

    public GuildModifyAction setVerificationLevel(VerificationLevel level) {
        return setProperty("verification_level", level.getValue());
    }

    public GuildModifyAction setNotificationLevel(NotificationLevel level) {
        return setProperty("default_message_notifications", level.getValue());
    }

    public GuildModifyAction setContentFilterLevel(ContentFilterLevel level) {
        return setProperty("explicit_content_filter", level.getValue());
    }

    public GuildModifyAction setIcon(String path) {
        if (path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            if (guildFeatures.contains(GuildFeature.ANIMATED_ICON.name())) {
                setProperty("icon", IOUtils.setImageData(path));
            }
        } else {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildModifyAction setSplash(String path) {
        if (guildFeatures.contains(GuildFeature.INVITE_SPLASH.name())) {
            setProperty("splash", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildModifyAction setDiscoverySplash(String path) {
        if (guildFeatures.contains(GuildFeature.DISCOVERABLE.name())) {
            setProperty("discovery_splash", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildModifyAction setBanner(String path) {
        if (path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            if (guildFeatures.contains(GuildFeature.ANIMATED_BANNER.name())) {
                setProperty("banner", IOUtils.setImageData(path));
            }
        } else {
            setProperty("banner", IOUtils.setImageData(path));
        }
        return this;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Get(guildId));
            hasChanges = false;
        }
        jsonObject.removeAll();

        if (SlashCommandEvent.getData() != null) {
            SlashCommandEvent.UpdateData();
        }
        if (ButtonEvent.getData() != null) {
            ButtonEvent.UpdateData();
        }
        if (SelectMenuEvent.getData() != null) {
            SelectMenuEvent.UpdateData();
        }
    }
}

