package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.IOUtils;

import java.awt.*;
import java.util.List;

/**
 * Represents an action for creating a guild role.
 *
 * @see GuildRole
 */
public class GuildRoleCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final boolean roleIcons;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link GuildRoleCreateAction}
     *
     * @param guildId   The ID of the guild in which the role is being created.
     * @param roleIcons A boolean flag indicating whether role icons are enabled.
     */
    public GuildRoleCreateAction(String guildId, boolean roleIcons) {
        this.guildId = guildId;
        this.roleIcons = roleIcons;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildRoleCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the guild role.
     *
     * @param name the name to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the icon for the guild role.
     *
     * @param path the path of the icon image file
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setIcon(String path) {
        if (roleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the permissions for the guild role.
     *
     * @param permissions list of permissions to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        return setProperty("permissions", value);
    }

    /**
     * Sets the color for the guild role.
     *
     * @param color the color to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setColor(Color color) {
        return setProperty("color", color.getRGB());
    }

    /**
     * Sets the hoist for the guild role.
     *
     * @param hoist the hoist value to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setHoist(boolean hoist) {
        return setProperty("hoist", hoist);
    }

    /**
     * Sets the mentionable for the guild role.
     *
     * @param mentionable the mentionable value to set
     * @return the modified GuildRoleCreateAction object
     */
    public GuildRoleCreateAction setMentionable(boolean mentionable) {
        return setProperty("mentionable", mentionable);
    }

    private String getCreatedId() {
        return createdId;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Role.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
