package io.github.dawncord.api.action.guild;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ButtonEvent;
import io.github.dawncord.api.event.SelectMenuEvent;
import io.github.dawncord.api.event.SlashCommandEvent;
import io.github.dawncord.api.types.GuildFeature;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.utils.IOUtils;

import java.util.List;

/**
 * Represents an action for updating a guild.
 *
 * @see Guild
 */
public class GuildModifyAction extends GuildAction {
    private final String guildId;
    private final List<String> guildFeatures;

    /**
     * Create a new {@link GuildModifyAction}
     *
     * @param guildId       The ID of the guild to be modified.
     * @param guildFeatures The list of features to modify for the guild.
     */
    public GuildModifyAction(String guildId, List<String> guildFeatures) {
        super();
        this.guildId = guildId;
        this.guildFeatures = guildFeatures;
    }

    /**
     * Sets the owner for the guild.
     *
     * @param user the new owner for the guild.
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setOwner(User user) {
        setProperty("owner_id", user.getId());
        return this;
    }

    /**
     * Sets the AFK channel for the guild.
     *
     * @param channel the new AFK channel for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setAfkChannel(GuildChannel channel) {
        setProperty("afk_channel_id", channel.getId());
        return this;
    }

    /**
     * Sets the system channel for the guild.
     *
     * @param channel the new system channel for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setSystemChannel(GuildChannel channel) {
        setProperty("system_channel_id", channel.getId());
        return this;
    }

    /**
     * Sets the rules channel for the guild.
     *
     * @param channel the guild channel to set as the rules channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setRulesChannel(GuildChannel channel) {
        setProperty("rules_channel_id", channel.getId());
        return this;
    }

    /**
     * Sets the mod channel for the guild.
     *
     * @param channel the guild channel to set as the mod channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setModChannel(GuildChannel channel) {
        setProperty("public_updates_channel_id", channel.getId());
        return this;
    }

    /**
     * Sets the alerts channel for the guild.
     *
     * @param channel the guild channel to set as the alerts channel
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setAlertsChannel(GuildChannel channel) {
        setProperty("safety_alerts_channel_id", channel.getId());
        return this;
    }

    /**
     * Sets the locale for the guild.
     *
     * @param locale the new locale for the guild
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setLocale(Locale locale) {
        setProperty("preferred_locale", locale.getLocaleCode());
        return this;
    }

    /**
     * Sets the boost progress bar for the guild.
     *
     * @param enabled a boolean indicating whether the boost progress bar should be enabled or disabled
     * @return the modified GuildModifyAction object
     */
    public GuildModifyAction setBoostProgressBar(boolean enabled) {
        setProperty("premium_progress_bar_enabled", enabled);
        return this;
    }

    /**
     * Sets the icon for the guild.
     *
     * @param path the path to the icon image
     * @return the modified GuildModifyAction object
     */
    @Override
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

    @Override
    protected void submit() {
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

