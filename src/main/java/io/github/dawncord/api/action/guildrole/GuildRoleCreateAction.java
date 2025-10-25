package io.github.dawncord.api.action.guildrole;

import com.fasterxml.jackson.databind.JsonNode;
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
public class GuildRoleCreateAction extends GuildRoleAction {
    private String createdId;

    /**
     * Create a new {@link GuildRoleCreateAction}
     *
     * @param guildId   The ID of the guild in which the role is being created.
     * @param hasRoleIcons A boolean flag indicating whether role icons are enabled.
     */
    public GuildRoleCreateAction(String guildId, boolean hasRoleIcons) {
        super(guildId, hasRoleIcons);
    }

    private String getCreatedId() {
        return createdId;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }
    
    @Override
    protected void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Role.All(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
