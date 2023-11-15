package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.EmojiModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EmojiImpl implements Emoji {
    private final Guild guild;
    private final JSONObject emoji;

    public EmojiImpl(Guild guild, JSONObject emoji) {
        this.guild = guild;
        this.emoji = emoji;
    }

    @Override
    public String getId() {
        return emoji.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return emoji.getString("name");
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public List<GuildRole> getRoles() {
        List<GuildRole> roles = new ArrayList<>();
        JSONArray rolesArray = emoji.optJSONArray("roles");
        if (rolesArray != null) {
            for (int i = 0; i < rolesArray.length(); i++) {
                roles.add(getGuild().getRoleById(rolesArray.getString(i)));
            }
        }
        return roles;
    }

    @Override
    public User getCreator() {
        return emoji.optJSONObject("user") != null
                ? new UserImpl(emoji.getJSONObject("user"))
                : null;
    }

    @Override
    public boolean isRequiredColons() {
        return emoji.optBoolean("require_colons", false);
    }

    @Override
    public boolean isManaged() {
        return emoji.optBoolean("managed", false);
    }

    @Override
    public boolean isAnimated() {
        return emoji.optBoolean("animated", false);
    }

    @Override
    public boolean isAvailable() {
        return emoji.optBoolean("available", false);
    }

    @Override
    public void modify(Consumer<EmojiModifyAction> handler) {
        ActionExecutor.modifyEmoji(handler, getGuild().getId(), getId());
    }

    @Override
    public void delete() {
        ApiClient.delete("guilds/" + getGuild().getId() + "/emojis/" + getId());
    }
}
