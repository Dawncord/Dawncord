package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ButtonEvent;
import io.github.dawncord.api.event.SelectMenuEvent;
import io.github.dawncord.api.event.SlashCommandEvent;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.IOUtils;

import java.util.List;

/**
 * Represents an action for updating a guild.
 *
 * @see Guild
 */
public class GuildModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final List<String> guildFeatures;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildModifyAction}
     *
     * @param guildId       The ID of the guild to be modified.
     * @param guildFeatures The list of features to modify for the guild.
     */
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

    /**
     * Sets the name of the GuildModifyAction.
     *
     * @param name the new name for the guild.
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the guild.
     *
     * @param description the new description for the guild.
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the owner for the guild.
     *
     * @param user the new owner for the guild.
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setOwner(User user) {
        return setProperty("owner_id", user.getId());
    }

    /**
     * Sets the AFK channel for the guild.
     *
     * @param channel the new AFK channel for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setAfkChannel(GuildChannel channel) {
        return setProperty("afk_channel_id", channel.getId());
    }

    /**
     * Sets the AFK timeout for the guild.
     *
     * @param seconds the new AFK timeout in seconds
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setAfkTimeout(int seconds) {
        return setProperty("afk_timeout", seconds);
    }

    /**
     * Sets the system channel for the guild.
     *
     * @param channel the new system channel for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setSystemChannel(GuildChannel channel) {
        return setProperty("system_channel_id", channel.getId());
    }

    /**
     * Sets the system channel flags for the guild.
     *
     * @param flag  the first SystemChannelFlag to set
     * @param flags additional SystemChannelFlags to set
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setSystemChannelFlags(SystemChannelFlag flag, SystemChannelFlag... flags) {
        int value = flag.getValue();
        for (SystemChannelFlag f : flags) {
            value |= f.getValue();
        }
        return setProperty("system_channel_flags", value);
    }

    /**
     * Sets the rules channel for the guild.
     *
     * @param channel the guild channel to set as the rules channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setRulesChannel(GuildChannel channel) {
        return setProperty("rules_channel_id", channel.getId());
    }

    /**
     * Sets the mod channel for the guild.
     *
     * @param channel the guild channel to set as the mod channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setModChannel(GuildChannel channel) {
        return setProperty("public_updates_channel_id", channel.getId());
    }

    /**
     * Sets the alerts channel for the guild.
     *
     * @param channel the guild channel to set as the alerts channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setAlertsChannel(GuildChannel channel) {
        return setProperty("safety_alerts_channel_id", channel.getId());
    }

    /**
     * Sets the locale for the guild.
     *
     * @param locale the new locale for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setLocale(Locale locale) {
        return setProperty("preferred_locale", locale.getLocaleCode());
    }

    /**
     * Sets the boost progress bar for the guild.
     *
     * @param enabled a boolean indicating whether the boost progress bar should be enabled or disabled
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setBoostProgressBar(boolean enabled) {
        return setProperty("premium_progress_bar_enabled", enabled);
    }

    /**
     * Sets the verification level for the guild.
     *
     * @param level the new verification level to set
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setVerificationLevel(VerificationLevel level) {
        return setProperty("verification_level", level.getValue());
    }

    /**
     * Sets the notification level for the guild.
     *
     * @param level the new notification level to set
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setNotificationLevel(NotificationLevel level) {
        return setProperty("default_message_notifications", level.getValue());
    }

    /**
     * Sets the content filter level for the guild.
     *
     * @param level the new content filter level to set
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setContentFilterLevel(ContentFilterLevel level) {
        return setProperty("explicit_content_filter", level.getValue());
    }

    /**
     * Sets the icon for the guild.
     *
     * @param path the path to the icon image
     * @return the modified GuildModifyAction object
     */
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

    /**
     * Sets the splash image for the guild.
     *
     * @param path the path to the splash image
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setSplash(String path) {
        if (guildFeatures.contains(GuildFeature.INVITE_SPLASH.name())) {
            setProperty("splash", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the discovery splash image for the guild if it is discoverable.
     *
     * @param path the path to the discovery splash image
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setDiscoverySplash(String path) {
        if (guildFeatures.contains(GuildFeature.DISCOVERABLE.name())) {
            setProperty("discovery_splash", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the banner for the guild.
     *
     * @param path the path to the banner image
     * @return the modified GuildModifyAction object
     */
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

