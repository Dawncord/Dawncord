package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.types.Scope;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.json.JSONObject;

import java.util.List;

public class InstallParams {
    private final JSONObject params;

    public InstallParams(JSONObject params) {
        this.params = params;
    }

    public List<Scope> getScopes() {
        return EnumUtils.getEnumList(params.getJSONArray("scopes"), Scope.class);
    }

    public List<PermissionType> getPermissions() {
        return EnumUtils.getEnumListFromLong(params, "permissions", PermissionType.class);
    }
}
