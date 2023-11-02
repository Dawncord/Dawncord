package org.dimas4ek.wrapper.slashcommand.option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dimas4ek.wrapper.entities.Mentionable;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.message.Attachment;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.Map;

@AllArgsConstructor
@Getter
public class OptionData {
    private Map<String, Object> data;

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
        return new UserImpl(JsonUtils.fetchEntity("/users/" + getAsString()));
    }

    public GuildChannel getAsChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + getAsString()));
    }

    public GuildRole getAsRole() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + data.get("guildId"))).getRoleById(getAsString());
    }

    public Mentionable getAsMentionable() {
        return new Mentionable(getAsString(), (JSONObject) data.get("resolved"));
    }

    public Attachment getAsAttachment() {
        if (data.get("resolved") != null) {
            JSONObject resolved = (JSONObject) data.get("resolved");
            JSONObject attachment = resolved.getJSONObject("attachments").getJSONObject(getAsString());
            return new Attachment(attachment);
        }
        return null;
    }
}
