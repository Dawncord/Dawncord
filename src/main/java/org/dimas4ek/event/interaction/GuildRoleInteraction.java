package org.dimas4ek.event.interaction;

import org.dimas4ek.enities.PermissionType;
import org.dimas4ek.enities.guild.GuildRole;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuildRoleInteraction implements GuildRole {
    private final JSONObject role;
    
    public GuildRoleInteraction(JSONObject role) {
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
    public Color getColor() {
        return new Color(role.getInt("color"));
    }
    
    @Override
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        long permissionsFromJson = Long.parseLong(role.getString("permissions"));
        for (PermissionType permission : PermissionType.values()) {
            if ((permissionsFromJson & permission.getValue()) != 0) {
                permissions.add(permission.name());
            }
        }
        return permissions;
    }
}
