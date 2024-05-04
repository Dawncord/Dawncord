package io.github.dawncord.api.command.option;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.Mentionable;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRoleImpl;
import io.github.dawncord.api.entities.message.Attachment;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.Map;


/**
 * Represents the data of an option.
 */
public class OptionData {
    private final Map<String, Object> data;
    private final Guild guild;

    /**
     * Constructs a new OptionData object.
     *
     * @param data  The data of the option.
     * @param guild The guild of the option.
     */
    public OptionData(Map<String, Object> data, Guild guild) {
        this.data = data;
        this.guild = guild;
    }

    /**
     * Retrieves the data of the option.
     *
     * @return The data of the option.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Retrieves the value of option
     *
     * @return the value of the option as string
     */
    public String getAsString() {
        return ((JsonNode) data.get("value")).asText();
    }

    /**
     * Retrieves the value of the option as an integer.
     *
     * @return the value of the option as an integer
     */
    public int getAsInt() {
        return Integer.parseInt(getAsString());
    }

    /**
     * Retrieves the value of the option as a boolean.
     *
     * @return the value of the option as a boolean
     */
    public boolean getAsBoolean() {
        return Boolean.parseBoolean(getAsString());
    }

    /**
     * Retrieves the User associated with the given option value by fetching the user data from the server.
     *
     * @return the User object representing the user associated with the option value
     */
    public User getAsUser() {
        return new UserImpl(JsonUtils.fetch(Routes.User(getAsString())));
    }

    /**
     * Retrieves the GuildChannel associated with the given option value.
     *
     * @return the GuildChannel associated with the option value
     */
    public GuildChannel getAsChannel() {
        return guild.getChannelById(getAsString());
    }

    /**
     * Retrieves the GuildRole associated with the given option value.
     *
     * @return the GuildRole associated with the option value
     */
    public GuildRoleImpl getAsRole() {
        return guild.getRoleById(getAsString());
    }

    /**
     * Retrieves the Mentionable entity associated with the option value.
     *
     * @return the Mentionable entity associated with the option value
     */
    public String getAsMention() {
        return data.get("resolved") != null
                ? new Mentionable(getAsString(), (JsonNode) data.get("resolved")).getAsMention()
                : null;
    }

    /**
     * Retrieves the Attachment associated with the option value.
     *
     * @return the Attachment associated with the option value
     */
    public Attachment getAsAttachment() {
        if (data.get("resolved") != null) {
            JsonNode resolved = (JsonNode) data.get("resolved");
            JsonNode attachment = resolved.get("attachments").get(getAsString());
            return new Attachment(attachment);
        }
        return null;
    }
}
