package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.types.ApplicationFlag;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.types.Scope;
import io.github.dawncord.api.utils.IOUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an action for updating an application.
 *
 * @see Application
 */
public class ApplicationModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    /**
     * Create a new {@link ApplicationModifyAction}
     */
    public ApplicationModifyAction() {
        this.jsonObject = mapper.createObjectNode();
    }

    private ApplicationModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the description property in the Application.
     *
     * @param description the description to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Sets the custom install URL property in the Application.
     *
     * @param url the URL to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setCustomInstallUrl(String url) {
        return setProperty("custom_install_url", url);
    }

    /**
     * Sets the verification URL property in the Application.
     *
     * @param url the URL to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setVerificationUrl(String url) {
        return setProperty("role_connections_verification_url", url);
    }

    /**
     * Sets the interaction endpoint URL property in the Application.
     *
     * @param url the URL to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setInteractionEndpointUrl(String url) {
        return setProperty("interactions_endpoint_url", url);
    }

    /**
     * Sets the install parameters for the application action.
     *
     * @param scopes      list of scopes to set
     * @param permissions list of permissions to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setInstallParams(List<Scope> scopes, List<PermissionType> permissions) {
        long value = permissions.stream()
                .mapToLong(PermissionType::getValue)
                .reduce(0L, (a, b) -> a | b);

        ArrayNode scopesNode = mapper.createArrayNode();
        scopes.stream().map(Scope::getValue).forEach(scopesNode::add);

        ObjectNode installParamsNode = mapper.createObjectNode();
        installParamsNode.set("scopes", scopesNode);
        installParamsNode.put("permissions", String.valueOf(value));

        return setProperty("install_params", installParamsNode);
    }

    /**
     * Sets the flags for the Application.
     *
     * @param flags the flags to set
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setFlags(ApplicationFlag... flags) {
        long value = Arrays.stream(flags)
                .mapToLong(ApplicationFlag::getValue)
                .reduce(0L, (a, b) -> a | b);

        return setProperty("flags", value);
    }

    /**
     * Sets the icon for the application.
     *
     * @param path the path of the icon image file
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setIcon(String path) {
        return setProperty("icon", IOUtils.setImageData(path));
    }

    /**
     * Sets the cover image for the application.
     *
     * @param path the path of the cover image file
     * @return the modified ApplicationModifyAction object
     */
    public ApplicationModifyAction setCoverImage(String path) {
        return setProperty("cover_image", IOUtils.setImageData(path));
    }

    /**
     * Set tags for the application.
     *
     * @param tags the tags to be set
     * @return the resulting ApplicationModifyAction
     */
    public ApplicationModifyAction setTags(String... tags) {
        return setProperty("tags", tags);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Application());
            hasChanges = false;
        }
    }
}
