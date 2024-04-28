package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.types.PermissionType;
import org.dimas4ek.dawncord.utils.IOUtils;

import java.awt.*;
import java.util.List;

public class GuildRoleModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String guildId;
    private final String roleId;
    private final boolean roleIcons;
    private boolean hasChanges = false;

    public GuildRoleModifyAction(String guildId, String roleId, boolean hasRoleIcons) {
        this.guildId = guildId;
        this.roleId = roleId;
        this.roleIcons = hasRoleIcons;
        this.jsonObject = mapper.createObjectNode();
    }

    private GuildRoleModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildRoleModifyAction setName(String name) {
        return setProperty("name", name);

    }

    public GuildRoleModifyAction setIcon(String path) {
        if (roleIcons) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildRoleModifyAction setPermissions(List<PermissionType> permissions) {
        long value = 0;
        for (PermissionType permission : permissions) {
            value |= permission.getValue();
        }
        return setProperty("permissions", value);
    }

    public GuildRoleModifyAction setColor(Color color) {
        return setProperty("color", color.getRGB());
    }

    public GuildRoleModifyAction setHoist(boolean hoist) {
        return setProperty("hoist", hoist);
    }

    public GuildRoleModifyAction setMentionable(boolean mentionable) {
        return setProperty("mentionable", mentionable);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.patch(jsonObject, Routes.Guild.Role.Get(guildId, roleId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
