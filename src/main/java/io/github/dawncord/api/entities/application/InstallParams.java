package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.types.Scope;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

/**
 * Represents the installation parameters of an application.
 * This class provides methods to access the installation parameters, including scopes and permissions.
 */
public class InstallParams {
    private final LazyLoader loader;
    private List<Scope> scopes;
    private List<PermissionType> permissions;

    /**
     * Constructs an InstallParams object with the specified JSON node containing installation parameters.
     *
     * @param params The JSON node containing installation parameters.
     */
    public InstallParams(JsonNode params) {
        loader = new LazyLoader(params);
    }

    /**
     * Retrieves the list of scopes associated with the installation parameters.
     *
     * @return The list of scopes.
     */
    public List<Scope> getScopes() {
        scopes = loader.loadEnumList(scopes, "scopes", Scope.class, "str");
        return scopes;
    }

    /**
     * Retrieves the list of permission types associated with the installation parameters.
     *
     * @return The list of permission types.
     */
    public List<PermissionType> getPermissions() {
        permissions = loader.loadEnumListFromLong(permissions, "permissions", PermissionType.class);
        return permissions;
    }
}
