package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.types.Scope;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.List;

/**
 * Represents the installation parameters of an application.
 * This class provides methods to access the installation parameters, including scopes and permissions.
 */
public class InstallParams {
    private final JsonNode params;
    private List<Scope> scopes;
    private List<PermissionType> permissions;

    /**
     * Constructs an InstallParams object with the specified JSON node containing installation parameters.
     *
     * @param params The JSON node containing installation parameters.
     */
    public InstallParams(JsonNode params) {
        this.params = params;
    }

    /**
     * Retrieves the list of scopes associated with the installation parameters.
     *
     * @return The list of scopes.
     */
    public List<Scope> getScopes() {
        if (scopes == null) {
            scopes = EnumUtils.getEnumList(params.get("scopes"), Scope.class);
        }
        return scopes;
    }

    /**
     * Retrieves the list of permission types associated with the installation parameters.
     *
     * @return The list of permission types.
     */
    public List<PermissionType> getPermissions() {
        if (permissions == null) {
            permissions = EnumUtils.getEnumListFromLong(params, "permissions", PermissionType.class);
        }
        return permissions;
    }
}
