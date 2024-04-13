package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.ApplicationFlag;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.types.Scope;
import org.dimas4ek.wrapper.utils.IOUtils;

import java.util.Arrays;
import java.util.List;

public class ApplicationModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private boolean hasChanges = false;

    public ApplicationModifyAction() {
        this.jsonObject = mapper.createObjectNode();
    }

    private ApplicationModifyAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public ApplicationModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    public ApplicationModifyAction setCustomInstallUrl(String url) {
        return setProperty("custom_install_url", url);
    }

    public ApplicationModifyAction setVerificationUrl(String url) {
        return setProperty("role_connections_verification_url", url);
    }

    public ApplicationModifyAction setInteractionEndpointUrl(String url) {
        return setProperty("interactions_endpoint_url", url);
    }

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

    public ApplicationModifyAction setFlags(ApplicationFlag... flags) {
        long value = Arrays.stream(flags)
                .mapToLong(ApplicationFlag::getValue)
                .reduce(0L, (a, b) -> a | b);

        return setProperty("flags", value);
    }

    public ApplicationModifyAction setIcon(String path) {
        return setProperty("icon", IOUtils.setImageData(path));
    }

    public ApplicationModifyAction setCoverImage(String path) {
        return setProperty("cover_image", IOUtils.setImageData(path));
    }

    public ApplicationModifyAction setTags(String... tags) {
        return setProperty("tags", tags);
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/applications/@me");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
