package org.dimas4ek.wrapper.entities.role;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildRoleModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class GuildRoleImpl implements GuildRole {
    private final JSONObject role;
    private final Guild guild;

    public GuildRoleImpl(Guild guild, JSONObject role) {
        this.guild = guild;
        this.role = role;
    }

    @Override
    public String getId() {
        return role.getString("id");
    }

    @Override
    public String getName() {
        return role.getString("name");
    }

    @Override
    public String getDescription() {
        return role.getString("description");
    }

    @Override
    public List<PermissionType> getPermissions() {
        return EnumUtils.getEnumListFromLong(role, "permissions", PermissionType.class);
        /*List<PermissionType> permissions = new ArrayList<>();
        long permissionsFromJson = Long.parseLong(role.getString("permissions"));
        for (PermissionType permission : PermissionType.values()) {
            if ((permissionsFromJson & permission.getValue()) != 0) {
                permissions.add(permission);
            }
        }
        return permissions;*/
    }

    @Override
    public int getPosition() {
        return role.getInt("position");
    }

    @Override
    public Color getColor() {
        return new Color(role.getInt("color"));
    }

    @Override
    public boolean isPinned() {
        return role.getBoolean("hoist");
    }

    @Override
    public boolean isManaged() {
        return role.getBoolean("managed");
    }

    @Override
    public boolean isMentionable() {
        return role.getBoolean("mentionable");
    }

    @Override
    public Tags getTags() {
        return role.has("tags")
                ? new Tags(role.getJSONObject("tags"))
                : null;
    }

    @Override
    public void setPosition(int position) {
        ApiClient.patch(
                new JSONObject()
                        .put("id", getId())
                        .put("position", position),
                "/guilds/" + getId() + "/roles"
        );
    }

    @Override
    public GuildRoleModifyAction modify() {
        return new GuildRoleModifyAction(guild.getId(), getId());
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
