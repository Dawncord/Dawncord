package io.github.dawncord.api.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.guildrole.GuildRoleModifyAction;
import io.github.dawncord.api.entities.IMentionable;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.RoleIcon;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.types.RoleFlags;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.LazyLoader;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a role in a guild.
 */
public class GuildRole implements ISnowflake, IMentionable {
    private final LazyLoader loader;
    private final JsonNode role;
    private final Guild guild;
    private String id;
    private String name;
    private Color color;
    private RoleColors colors;
    private Boolean isPinned;
    private RoleIcon icon;
    private String unicodeEmoji;
    private Integer position;
    private List<PermissionType> permissions;
    private Boolean isManaged;
    private Boolean isMentionable;
    private Tags tags;

    /**
     * Constructs a new GuildRole object with the provided JSON node and guild.
     *
     * @param role  The JSON node representing the role.
     * @param guild The guild to which the role belongs.
     */
    public GuildRole(JsonNode role, Guild guild) {
        this.role = role;
        this.guild = guild;
        loader = new LazyLoader(role);
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

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public RoleIcon getIcon() {
        icon = loader.loadIfExists(icon, "icon", () -> new RoleIcon(getId(), role.get("icon").asText()));
        return icon;
    }

    public String getUnicodeEmoji() {
        unicodeEmoji = loader.loadString(unicodeEmoji, "unicode_emoji");
        return unicodeEmoji;
    }

    public List<PermissionType> getPermissions() {
        permissions = loader.loadEnumListFromLong(permissions, "permissions", PermissionType.class);
        return permissions;
    }

    public int getPosition() {
        position = loader.loadInt(position, "position");
        return position;
    }

    public void setPosition(int position) {
        ApiClient.patch(
                JsonNodeFactory.instance.objectNode()
                        .put("id", getId())
                        .put("position", position),
                Routes.Guild.Role.All(guild.getId())
        );
    }

    public Color getColor() {
        color = loader.loadIfExists(color, "color", () -> new Color(role.get("color").asInt()));
        return color;
    }

    public RoleColors getColors() {
        colors = loader.loadIfExists(colors, "colors", () -> new RoleColors(role.get("colors")));
        return colors;
    }

    public boolean isPinned() {
        isPinned = loader.loadBoolean(isPinned, "hoist");
        return isPinned;
    }

    public boolean isManaged() {
        isManaged = loader.loadBoolean(isManaged, "managed");
        return isManaged;
    }

    public boolean isMentionable() {
        isMentionable = loader.loadBoolean(isMentionable, "mentionable");
        return isMentionable;
    }

    public Tags getTags() {
        tags = loader.loadIfExists(tags, "tags", () -> new Tags(role.get("tags"), guild));
        return tags;
    }

    public RoleFlags getFlags() {
        return RoleFlags.IN_PROMPT;
    }

    public ModifyEvent<GuildRole> modify(Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, guild.getId(), getId(), guild.getFeatures().contains("ROLE_ICONS"));
        return new ModifyEvent<>(guild.getRoleById(getId()));
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.Role.Get(guild.getId(), getId()));
    }

    @Override
    public String getAsMention() {
        return "<@&" + getId() + ">";
    }
}
