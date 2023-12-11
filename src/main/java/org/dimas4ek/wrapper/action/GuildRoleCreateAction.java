package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.IOUtils;

import java.awt.*;
import java.util.List;

public class GuildRoleCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final boolean roleIcons;
    private boolean hasChanges = false;

    public GuildRoleCreateAction(String guildId, boolean roleIcons) {
        this.guildId = guildId;
        this.roleIcons = roleIcons;
        this.jsonObject = mapper.createObjectNode();
    }

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public GuildRoleCreateAction setName(String name) {
        setProperty("name", name);
        return this;
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
        setProperty("permissions", value);
        return this;
    }

    public GuildRoleCreateAction setColor(Color color) {
        setProperty("color", color.getRGB());
        return this;
    }

    public GuildRoleCreateAction setHoist(boolean hoist) {
        setProperty("hoist", hoist);
        return this;
    }

    public GuildRoleCreateAction setMentionable(boolean mentionable) {
        setProperty("mentionable", mentionable);
        return this;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds/" + guildId + "/roles");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
