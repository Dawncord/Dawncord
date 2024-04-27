package org.dimas4ek.dawncord.entities;

import lombok.Getter;
import org.dimas4ek.dawncord.types.PermissionOverrideType;
import org.dimas4ek.dawncord.types.PermissionType;

import java.util.List;

@Getter
public class PermissionOverride {
    private final String id;
    private final PermissionOverrideType type;
    private final List<PermissionType> denied;
    private final List<PermissionType> allowed;

    public PermissionOverride(String id, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed) {
        this.id = id;
        this.type = type;
        this.denied = denied;
        this.allowed = allowed;
    }

    public PermissionOverride(long id, PermissionOverrideType type, List<PermissionType> denied, List<PermissionType> allowed) {
        this.id = String.valueOf(id);
        this.type = type;
        this.denied = denied;
        this.allowed = allowed;
    }
}
