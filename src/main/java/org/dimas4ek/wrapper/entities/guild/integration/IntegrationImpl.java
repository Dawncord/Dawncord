package org.dimas4ek.wrapper.entities.guild.integration;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.types.IntegrationExpireBehavior;
import org.dimas4ek.wrapper.types.IntegrationType;
import org.dimas4ek.wrapper.types.Scope;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class IntegrationImpl implements Integration {
    private final Guild guild;
    private final JSONObject integration;

    public IntegrationImpl(Guild guild, JSONObject integration) {
        this.guild = guild;
        this.integration = integration;
    }

    @Override
    public String getId() {
        return integration.getString("id");
    }

    @Override
    public String getName() {
        return integration.getString("name");
    }

    public Guild getGuild() {
        return guild;
    }

    @Override
    public IntegrationType getType() {
        return EnumUtils.getEnumObject(integration, "type", IntegrationType.class);
    }

    @Override
    public IntegrationAccount getAccount() {
        JSONObject account = integration.getJSONObject("account");
        return new IntegrationAccount(
                account.getString("id"),
                account.getString("name")
        );
    }

    @Override
    public IntegrationApplication getApplication() {
        return integration.optJSONObject("application") != null
                ? new IntegrationApplication(integration.getJSONObject("application"))
                : null;
    }

    @Override
    public List<Scope> getScopes() {
        List<Scope> scopes = new ArrayList<>();
        for (int i = 0; i < integration.getJSONArray("scopes").length(); i++) {
            String stringValue = integration.getJSONArray("scopes").optString(i, null);
            if (stringValue != null) {
                for (Scope scope : Scope.values()) {
                    if (scope.getValue().equals(stringValue)) {
                        scopes.add(scope);
                        break;
                    }
                }
            }
        }
        return scopes;
    }

    @Override
    public User getUser() {
        return integration.optJSONObject("user") == null ? null : new UserImpl(integration.getJSONObject("user"));
    }

    @Override
    public GuildRole getRole() {
        return integration.optString("role_id") == null ? null : getGuild().getRoleById(integration.getString("role_id"));
    }

    @Override
    public IntegrationExpireBehavior getExpireBehavior() {
        return integration.has("expire_behavior") ? EnumUtils.getEnumObject(integration, "expire_behavior", IntegrationExpireBehavior.class) : null;
    }

    @Override
    public ZonedDateTime getSyncedTimestamp() {
        return integration.has("synced_at") ? MessageUtils.getZonedDateTime(integration, "synced_at") : null;
    }

    @Override
    public boolean isEnabled() {
        return integration.getBoolean("enabled");
    }

    @Override
    public boolean isSyncing() {
        return integration.optBoolean("syncing", false);
    }

    @Override
    public boolean isEmoticonsEnabled() {
        return integration.optBoolean("enable_emoticons", false);
    }

    @Override
    public boolean isRevoked() {
        return integration.optBoolean("revoked", false);
    }

    @Override
    public int getExpireGrace() {
        return integration.optInt("expire_grace_period", 0);
    }

    @Override
    public int getSubscriberCount() {
        return integration.optInt("subscriber_count", 0);
    }
}
