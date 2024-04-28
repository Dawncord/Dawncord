package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.types.PermissionType;
import org.dimas4ek.dawncord.utils.IOUtils;

import java.awt.*;
import java.util.List;

public class GuildRoleCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final boolean roleIcons;
    private boolean hasChanges = false;
    private String createdId;

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

    public GuildRoleCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildRoleCreateAction setIcon(String path) {
        if (roleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildRoleCreateAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        return setProperty("permissions", value);
    }

    public GuildRoleCreateAction setColor(Color color) {
        return setProperty("color", color.getRGB());
    }

    public GuildRoleCreateAction setHoist(boolean hoist) {
        return setProperty("hoist", hoist);
    }

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
