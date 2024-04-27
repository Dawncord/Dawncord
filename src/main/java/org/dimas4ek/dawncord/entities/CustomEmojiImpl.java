package org.dimas4ek.dawncord.entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.EmojiModifyAction;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.utils.ActionExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomEmojiImpl implements CustomEmoji {
    private final JsonNode emoji;
    private final Guild guild;
    private String id;
    private String name;
    private List<GuildRole> roles = new ArrayList<>();
    private User creator;
    private Boolean isRequireColons;
    private Boolean isManaged;
    private Boolean isAnimated;
    private Boolean isAvailable;

    public CustomEmojiImpl(JsonNode emoji, Guild guild) {
        this.emoji = emoji;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = emoji.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = emoji.get("name").asText();
        }
        return name;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public List<GuildRole> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
            for (JsonNode role : emoji.get("roles")) {
                roles.add(guild.getRoleById(role.get("id").asText()));
            }
        }
        return roles;
    }

    @Override
    public User getCreator() {
        if (creator == null) {
            if (emoji.has("user") && emoji.hasNonNull("user")) {
                creator = new UserImpl(emoji.get("user"));
            }
        }
        return creator;
    }

    @Override
    public boolean isRequiredColons() {
        if (isRequireColons == null) {
            isRequireColons = emoji.get("require_colons").asBoolean();
        }
        return isRequireColons;
    }

    @Override
    public boolean isManaged() {
        if (isManaged == null) {
            isManaged = emoji.get("managed").asBoolean();
        }
        return isManaged;
    }

    @Override
    public boolean isAnimated() {
        if (isAnimated == null) {
            isAnimated = emoji.get("animated").asBoolean();
        }
        return isAnimated;
    }

    @Override
    public boolean isAvailable() {
        if (isAvailable == null) {
            isAvailable = emoji.get("available").asBoolean();
        }
        return isAvailable;
    }

    @Override
    public ModifyEvent<CustomEmoji> modify(Consumer<EmojiModifyAction> handler) {
        ActionExecutor.modifyEmoji(handler, getGuild().getId(), getId());
        return new ModifyEvent<>(guild.getEmojiById(getId()));
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.Emoji.Get(guild.getId(), getId()));
    }
}
