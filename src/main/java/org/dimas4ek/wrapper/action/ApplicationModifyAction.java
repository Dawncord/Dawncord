package org.dimas4ek.wrapper.action;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.ApplicationFlag;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.types.Scope;
import org.dimas4ek.wrapper.utils.IOUtils;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class ApplicationModifyAction {
    private final JSONObject jsonObject;

    public ApplicationModifyAction() {
        this.jsonObject = new JSONObject();
    }

    private void setProperty(String name, Object value) {
        jsonObject.put(name, value);
    }

    public ApplicationModifyAction setDescription(String description) {
        setProperty("description", description);
        return this;
    }

    public ApplicationModifyAction setCustomInstallUrl(String url) {
        setProperty("custom_install_url", url);
        return this;
    }

    public ApplicationModifyAction setVerificationUrl(String url) {
        setProperty("role_connections_verification_url", url);
        return this;
    }

    public ApplicationModifyAction setInteractionEndpointUrl(String url) {
        setProperty("interactions_endpoint_url ", url);
        return this;
    }

    public ApplicationModifyAction setInstallParams(List<Scope> scopes, List<PermissionType> permissions) {
        long value = permissions.stream()
                .mapToLong(PermissionType::getValue)
                .reduce(0L, (a, b) -> a | b);

        setProperty("install_params", new JSONObject()
                .put("scopes", scopes.stream().map(Scope::getValue).toList())
                .put("permissions", String.valueOf(value)));

        return this;
    }

    public ApplicationModifyAction setFlags(ApplicationFlag... flags) {
        long value = Arrays.stream(flags)
                .mapToLong(ApplicationFlag::getValue)
                .reduce(0L, (a, b) -> a | b);

        setProperty("flags", value);
        return this;
    }

    public ApplicationModifyAction setIcon(String path) {
        setProperty("icon", IOUtils.setImageData(path));
        return this;
    }

    public ApplicationModifyAction setCoverImage(String path) {
        setProperty("cover_image", IOUtils.setImageData(path));
        return this;
    }

    public ApplicationModifyAction setTags(String... tags) {
        setProperty("tags", tags);
        return this;
    }

    private void submit() {
        ApiClient.post(jsonObject, "/applications/@me");
    }
}
