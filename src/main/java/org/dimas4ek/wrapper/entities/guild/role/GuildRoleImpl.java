package org.dimas4ek.wrapper.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildRoleModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.image.RoleIcon;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

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
                "/guilds/" + getId() + "/roles"
        );
    }

    @Override
    public void modify(Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, guild.getId(), getId(), guild.getFeatures().contains("ROLE_ICONS"));
    }

    @Override
    public void delete() {
        ApiClient.delete("/guilds/" + guild.getId() + "/roles/" + getId());
    }

    @Override
    public String getAsMention() {
        return "<@&" + getId() + ">";
    }
}
