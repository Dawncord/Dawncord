package io.github.dawncord.api.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildRoleModifyAction;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.RoleIcon;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the implementation of a role within a guild.
 */
public class GuildRoleImpl implements GuildRole {

    private final JsonNode role;
    private final Guild guild;
    private String id;
    private String name;
    private RoleIcon icon;
    private List<PermissionType> permissions;
    private Integer position;
    private Color color;
    private Boolean isPinned;
    private Boolean isManaged;
    private Boolean isMentionable;
    private Tags tags;

    /**
     * Constructs a new GuildRoleImpl object with the provided JSON node and guild.
     *
     * @param role  The JSON node representing the role.
     * @param guild The guild to which the role belongs.
     */
    public GuildRoleImpl(JsonNode role, Guild guild) {
        this.role = role;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = role.get("id").asText();
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
            name = role.get("name").asText();
        }
        return name;
    }

    @Override
    public RoleIcon getIcon() {
        if (icon == null) {
            icon = role.has("icon") && role.hasNonNull("icon")
                    ? new RoleIcon(role.get("id").asText(), role.get("icon").asText())
                    : null;
        }
        return icon;
    }

    @Override
    public List<PermissionType> getPermissions() {
        if (permissions == null) {
            permissions = EnumUtils.getEnumListFromLong(role, "permissions", PermissionType.class);
        }
        return permissions;
    }

    @Override
    public int getPosition() {
        if (position == null) {
            position = role.get("position").asInt();
        }
        return position;
    }

    @Override
    public Color getColor() {
        if (color == null) {
            color = new Color(role.get("color").asInt());
        }
        return color;
    }

    @Override
    public boolean isPinned() {
        if (isPinned == null) {
            isPinned = role.get("hoist").asBoolean();
        }
        return isPinned;
    }

    @Override
    public boolean isManaged() {
        if (isManaged == null) {
            isManaged = role.get("managed").asBoolean();
        }
        return isManaged;
    }

    @Override
    public boolean isMentionable() {
        if (isMentionable == null) {
            isMentionable = role.get("mentionable").asBoolean();
        }
        return isMentionable;
    }

    @Override
    public Tags getTags() {
        if (tags == null) {
            tags = role.has("tags") ? new Tags(role.get("tags")) : null;
        }
        return tags;
    }

    @Override
    public void setPosition(int position) {
        ApiClient.patch(
                JsonNodeFactory.instance.objectNode()
                        .put("id", getId())
                        .put("position", position),
                Routes.Guild.Role.All(guild.getId())
        );
    }

    @Override
    public ModifyEvent<GuildRole> modify(Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, guild.getId(), getId(), guild.getFeatures().contains("ROLE_ICONS"));
        return new ModifyEvent<>(guild.getRoleById(getId()));
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.Role.Get(guild.getId(), getId()));
    }

    @Override
    public String getAsMention() {
        return "<@&" + getId() + ">";
    }
}
