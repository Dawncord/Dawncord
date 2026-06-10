package io.github.dawncord.api.entities.guild.integration;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.types.IntegrationExpireBehavior;
import io.github.dawncord.api.types.IntegrationType;
import io.github.dawncord.api.types.Scope;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an implementation of an integration within a guild.
 */
public class Integration implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode integration;
    private final Guild guild;
    private String id;
    private String name;
    private IntegrationType type;
    private Boolean isEnabled;
    private Boolean isSyncing;
    private GuildRole role;
    private Boolean isEmoticonsEnabled;
    private IntegrationExpireBehavior expireBehavior;
    private Integer expireGrace;
    private User user;
    private IntegrationAccount account;
    private ZonedDateTime syncedTimestamp;
    private Integer subscriberCount;
    private Boolean isRevoked;
    private IntegrationApplication application;
    private List<Scope> scopes = new ArrayList<>();

    /**
     * Constructs a new IntegrationImpl with the given JSON integration data and guild.
     *
     * @param integration The JSON data representing the integration.
     * @param guild       The guild to which the integration belongs.
     */
    public Integration(JsonNode integration, Guild guild) {
        this.integration = integration;
        this.guild = guild;
        loader = new LazyLoader(integration);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
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

    public IntegrationType getType() {
        type = loader.loadEnumObject(type, "type", IntegrationType.class);
        return type;
    }

    public IntegrationAccount getAccount() {
        account = loader.loadIfExists(account, "account", () -> new IntegrationAccount(integration.get("account")));
        return account;
    }

    public IntegrationApplication getApplication() {
        application = loader.loadIfExists(application, "application",
                () -> new IntegrationApplication(integration.get("application")));
        return application;
    }

    public List<Scope> getScopes() {
        scopes = loader.loadEnumList(scopes, "scopes", Scope.class, "str");
        return scopes;
    }

    public User getUser() {
        user = loader.loadIfExists(user, "user", () -> new User(integration.get("user")));
        return user;
    }

    public GuildRole getRole() {
        role = loader.loadIfExists(role, "role", () -> guild.getRoleById(integration.get("role_id").asText()));
        return role;
    }

    public IntegrationExpireBehavior getExpireBehavior() {
        expireBehavior = loader.loadEnumObject(expireBehavior, "expire_behavior", IntegrationExpireBehavior.class);
        return expireBehavior;
    }

    public ZonedDateTime getSyncedTimestamp() {
        syncedTimestamp = loader.loadZonedDateTime(syncedTimestamp, "synced_at");
        return syncedTimestamp;
    }

    public boolean isEnabled() {
        isEnabled = loader.loadBoolean(isEnabled, "enabled");
        return isEnabled != null ? isEnabled : false;
    }

    public boolean isSyncing() {
        isSyncing = loader.loadBoolean(isSyncing, "syncing");
        return isSyncing != null ? isSyncing : false;
    }

    public boolean isEmoticonsEnabled() {
        isEmoticonsEnabled = loader.loadBoolean(isEmoticonsEnabled, "enable_emoticons");
        return isEmoticonsEnabled != null ? isEmoticonsEnabled : false;
    }

    public boolean isRevoked() {
        isRevoked = loader.loadBoolean(isRevoked, "revoked");
        return isRevoked;
    }

    public int getExpireGrace() {
        expireGrace = loader.loadInt(expireGrace, "expire_grace_period");
        return expireGrace != null ? expireGrace : 0;
    }

    public int getSubscriberCount() {
        subscriberCount = loader.loadInt(subscriberCount, "subscriber_count");
        return subscriberCount != null ? subscriberCount : 0;
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.Integration.Get(guild.getId(), getId()));
    }
}
