package org.dimas4ek.wrapper.command.option;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.Mentionable;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.role.GuildRoleImpl;
import org.dimas4ek.wrapper.entities.message.Attachment;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.Map;

//todo add getters
public class OptionData {
    private final Map<String, Object> data;
    private final Guild guild;

    public OptionData(Map<String, Object> data, Guild guild) {
        this.data = data;
        this.guild = guild;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getAsString() {
        return String.valueOf(data.get("value"));
    }

    public int getAsInt() {
        return Integer.parseInt(getAsString());
    }

    public boolean getAsBoolean() {
        return Boolean.parseBoolean(getAsString());
    }

    public User getAsUser() {
        return new UserImpl(JsonUtils.fetch(Routes.User(getAsString())));
    }

    public GuildChannel getAsChannel() {
        return guild.getChannelById(getAsString());
    }

    public GuildRoleImpl getAsRole() {
        return guild.getRoleById(getAsString());
    }

    public Mentionable getAsMentionable() {
        return data.get("resolved") != null
                ? new Mentionable(getAsString(), (JsonNode) data.get("resolved"))
                : null;
    }

    public Attachment getAsAttachment() {
        if (data.get("resolved") != null) {
            JsonNode resolved = (JsonNode) data.get("resolved");
            JsonNode attachment = resolved.get("attachments").get(getAsString());
            return new Attachment(attachment);
        }
        return null;
    }
}
