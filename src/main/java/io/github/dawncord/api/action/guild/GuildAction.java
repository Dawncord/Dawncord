package io.github.dawncord.api.action.guild;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.types.ContentFilterLevel;
import io.github.dawncord.api.types.MessageNotificationLevel;
import io.github.dawncord.api.types.SystemChannelFlag;
import io.github.dawncord.api.types.VerificationLevel;
import io.github.dawncord.api.utils.IOUtils;

public abstract class GuildAction extends Action<GuildAction> {
    /**
     * Sets the name property for the guild.
     *
     * @param name the name to be set
     * @return the modified GuildAction object
     */
    public GuildAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the guild.
     *
     * @param description the new description for the guild.
     * @return the modified GuildAction object
     */
    public GuildAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the icon for the guild.
     *
     * @param path the path of the icon image file
     * @return the modified GuildAction object
     */
    public GuildAction setIcon(String path) {
        if (!path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the verification level for the guild.
     *
     * @param level the verification level to be set
     * @return the modified GuildAction object
     */
    public GuildAction setVerificationLevel(VerificationLevel level) {
        return setProperty("verification_level", level.getValue());
    }

    /**
     * Sets the notification level for the guild.
     *
     * @param level the notification level to be set
     * @return the modified GuildAction object
     */
    public GuildAction setNotificationLevel(MessageNotificationLevel level) {
        return setProperty("default_message_notifications", level.getValue());
    }

    /**
     * Sets the content filter level for the guild.
     *
     * @param level the content filter level to be set
     * @return the modified GuildAction object
     */
    public GuildAction setContentFilterLevel(ContentFilterLevel level) {
        return setProperty("explicit_content_filter", level.getValue());
    }


    /**
     * Sets the AFK timeout for the guild.
     *
     * @param seconds the AFK timeout in seconds
     * @return the modified GuildAction object
     */
    public GuildAction setAfkTimeout(int seconds) {
        return setProperty("afk_timeout", seconds);
    }

    /**
     * Sets the system channel flags for the guild.
     *
     * @param flags the system channel flags to set
     * @return the modified GuildAction object
     */
    public GuildAction setSystemChannelFlags(SystemChannelFlag... flags) {
        long value = 0;
        for (SystemChannelFlag flag : flags) {
            value |= flag.getValue();
        }
        return setProperty("system_channel_flags", value);
    }
}
