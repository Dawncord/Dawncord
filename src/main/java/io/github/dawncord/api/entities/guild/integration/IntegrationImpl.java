package io.github.dawncord.api.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.IntegrationExpireBehavior;
import io.github.dawncord.api.types.IntegrationType;
import io.github.dawncord.api.types.Scope;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.MessageUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an implementation of an integration within a guild.
 */
public class IntegrationImpl implements Integration {
    private final JsonNode integration;
    private final Guild guild;
    private String id;
    private String name;
    private IntegrationType type;
    private IntegrationAccount account;
    private IntegrationApplication application;
    private List<Scope> scopes = new ArrayList<>();
    private User user;
    private GuildRole role;
    private IntegrationExpireBehavior expireBehavior;
    private ZonedDateTime syncedTimestamp;
    private Boolean isEnabled;
    private Boolean isSyncing;
    private Boolean isEmoticonsEnabled;
    private Boolean isRevoked;
    private Integer expireGrace;
    private Integer subscriberCount;

    /**
     * Constructs a new IntegrationImpl with the given JSON integration data and guild.
     *
     * @param integration The JSON data representing the integration.
     * @param guild       The guild to which the integration belongs.
     */
    public IntegrationImpl(JsonNode integration, Guild guild) {
        this.integration = integration;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = integration.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = integration.get("name").asText();
        }
        return name;
    }

    /**
     * Retrieves the guild to which this integration belongs.
     *
     * @return The guild associated with this integration.
     */
    public Guild getGuild() {
        return guild;
    }

    @Override
    public IntegrationType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(integration, "type", IntegrationType.class);
        }
        return type;
    }

    @Override
    public IntegrationAccount getAccount() {
        if (account == null) {
            JsonNode accountNode = integration.get("account");
            account = new IntegrationAccount(accountNode.get("id").asText(), accountNode.get("name").asText());
        }
        return account;
    }

    @Override
    public IntegrationApplication getApplication() {
        if (application == null) {
            application = integration.has("application") && integration.hasNonNull("application")
                    ? new IntegrationApplication(integration.get("application"))
                    : null;
        }
        return application;
    }

    @Override
    public List<Scope> getScopes() {
        if (scopes == null) {
            scopes = new ArrayList<>();
            for (JsonNode scopeNode : integration.get("scopes")) {
                String stringValue = scopeNode.asText(null);
                if (stringValue != null) {
                    for (Scope scope : Scope.values()) {
                        if (scope.getValue().equals(stringValue)) {
                            scopes.add(scope);
                            break;
                        }
                    }
                }
            }
        }
        return scopes;
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = integration.has("user") ? new UserImpl(integration.get("user")) : null;
        }
        return user;
    }

    @Override
    public GuildRole getRole() {
        if (role == null) {
            role = integration.has("role_id") ? guild.getRoleById(integration.get("role_id").asText()) : null;
        }
        return role;
    }

    @Override
    public IntegrationExpireBehavior getExpireBehavior() {
        if (expireBehavior == null) {
            expireBehavior = EnumUtils.getEnumObject(integration, "expire_behavior", IntegrationExpireBehavior.class);
        }
        return expireBehavior;
    }

    @Override
    public ZonedDateTime getSyncedTimestamp() {
        if (syncedTimestamp == null) {
            syncedTimestamp = MessageUtils.getZonedDateTime(integration, "synced_at");
        }
        return syncedTimestamp;
    }

    @Override
    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = integration.has("enabled") ? integration.get("enabled").asBoolean() : null;
        }
        return isEnabled != null ? isEnabled : false;
    }

    @Override
    public boolean isSyncing() {
        if (isSyncing == null) {
            isSyncing = integration.has("syncing") ? integration.get("syncing").asBoolean() : null;
        }
        return isSyncing != null ? isSyncing : false;
    }

    @Override
    public boolean isEmoticonsEnabled() {
        if (isEmoticonsEnabled == null) {
            isEmoticonsEnabled = integration.has("enable_emoticons") ? integration.get("enable_emoticons").asBoolean() : null;
        }
        return isEmoticonsEnabled != null ? isEmoticonsEnabled : false;
    }

    @Override
    public boolean isRevoked() {
        if (isRevoked == null) {
            isRevoked = integration.has("revoked") && integration.get("revoked").asBoolean();
        }
        return isRevoked;
    }

    @Override
    public int getExpireGrace() {
        if (expireGrace == null) {
            expireGrace = integration.has("expire_grace_period") ? integration.get("expire_grace_period").asInt() : null;
        }
        return expireGrace != null ? expireGrace : 0;
    }

    @Override
    public int getSubscriberCount() {
        if (subscriberCount == null) {
            subscriberCount = integration.has("subscriber_count") ? integration.get("subscriber_count").asInt() : null;
        }
        return subscriberCount != null ? subscriberCount : 0;
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.Integration.Get(guild.getId(), getId()));
    }
}
