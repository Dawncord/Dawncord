package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.AbstractApplication;
import io.github.dawncord.api.entities.IApplication;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.application.team.Team;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.types.ApplicationFlag;
import io.github.dawncord.api.types.EventWebhookStatus;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

/**
 * Represents an implementation of an application.
 * ApplicationImpl is a class providing methods to access properties of an application.
 */
public class Application extends AbstractApplication implements IApplication {
    private final LazyLoader loader;
    private final JsonNode application;
    private List<String> rpcOrigins;
    private Boolean publicBot;
    private Boolean requiresCodeGrant;
    private String tosUrl;
    private String ppUrl;
    private User owner;
    private String verifyKey;
    private Team team;
    private Guild guild;
    private String primarySkuId;
    private String slug;
    private ApplicationIcon coverImage;
    private List<ApplicationFlag> flags;
    private Integer guildCount;
    private Integer userCount;
    private Integer authCount;
    private List<String> redirectURIs;
    private String interactionEndpointUrl;
    private String roleConnectionUrl;
    private String eventWebhooksUrl;
    private EventWebhookStatus eventWebhooksStatus;
    //private List<?> eventWebhooksTypes; todo check how it look in json
    private List<String> tags;
    private InstallParams installParams;
    private IntegrationTypesConfig integrationTypesConfig;
    private String customInstallUrl;

    /**
     * Constructs an ApplicationImpl object with the provided JSON node and guild.
     *
     * @param application The JSON node representing the application.
     */
    public Application(JsonNode application) {
        super(application);
        this.application = application;
        loader = new LazyLoader(application);
    }

    public Guild getGuild() {
        guild = loader.loadIfExists(guild, "guild_id",
                () -> new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(getGuildId()))));
        return guild;
    }

    public List<String> getRpcOrigins() {
        rpcOrigins = loader.loadIfExists(rpcOrigins, "rpc_origins",
                () -> JsonUtils.getStringList(application, "rpc_origins"));
        return rpcOrigins;
    }

    public int getGuildCount() {
        guildCount = loader.loadInt(guildCount, "approximate_guild_count");
        return guildCount != null ? guildCount : 0;
    }

    public int getUserCount() {
        userCount = loader.loadInt(userCount, "approximate_user_install_count");
        return userCount != null ? userCount : 0;
    }

    public int getAuthorizationCount() {
        authCount = loader.loadInt(authCount, "approximate_user_authorization_count");
        return authCount != null ? authCount : 0;
    }

    public List<String> getRedirectURIs() {
        redirectURIs = loader.loadIfExists(redirectURIs, "redirect_uris",
                () -> JsonUtils.getStringList(application, "redirect_uris"));
        return redirectURIs;
    }

    public String getInteractionEndpointUrl() {
        interactionEndpointUrl = loader.loadString(interactionEndpointUrl, "interaction_endpoint_url");
        return interactionEndpointUrl;
    }

    public String getRoleConnectionVerificationUrl() {
        roleConnectionUrl = loader.loadString(roleConnectionUrl, "role_connections_verification_url");
        return roleConnectionUrl;
    }

    public String getEventWebhooksUrl() {
        eventWebhooksUrl = loader.loadString(eventWebhooksUrl, "event_webhooks_url");
        return eventWebhooksUrl;
    }

    public EventWebhookStatus getEventWebhooksStatus() {
        eventWebhooksStatus = loader.loadEnumObject(eventWebhooksStatus, "event_webhooks_status", EventWebhookStatus.class);
        return eventWebhooksStatus;
    }

    public String getCustomInstallUrl() {
        customInstallUrl = loader.loadString(customInstallUrl, "custom_install_url");
        return customInstallUrl;
    }

    public ApplicationIcon getCoverImage() {
        coverImage = loader.loadIfExists(coverImage, "cover_image",
                () -> new ApplicationIcon(getId(), getCoverImageId()));
        return coverImage;
    }

    public User getOwner() {
        owner = loader.loadIfExists(owner, "owner",
                () -> new UserImpl(JsonUtils.fetch(Routes.User(getOwnerId()))));
        return owner;
    }

    public Team getTeam() {
        team = loader.loadIfExists(team, "team", Team::new);
        return team;
    }

    public boolean getPublicBot() {
        publicBot = loader.loadBoolean(publicBot, "bot_public");
        return publicBot != null && publicBot;
    }

    public boolean isBotRequiresCodeGrant() {
        requiresCodeGrant = loader.loadBoolean(requiresCodeGrant, "bot_require_code_grant");
        return requiresCodeGrant != null && requiresCodeGrant;
    }

    public String getTOSUrl() {
        tosUrl = loader.loadString(tosUrl, "terms_of_service_url");
        return tosUrl;
    }

    public String getPPUrl() {
        ppUrl = loader.loadString(ppUrl, "privacy_policy_url");
        return ppUrl;
    }

    public String getVerifyKey() {
        verifyKey = loader.loadString(verifyKey, "verify_key");
        return verifyKey;
    }

    public String getPrimarySkuId() {
        primarySkuId = loader.loadString(primarySkuId, "primary_sku_id");
        return primarySkuId;
    }

    public String getSlug() {
        slug = loader.loadString(slug, "slug");
        return slug;
    }

    public List<ApplicationFlag> getFlags() {
        flags = loader.loadEnumListFromLong(flags, "flags", ApplicationFlag.class);
        return flags;
    }

    public List<String> getTags() {
        tags = loader.loadIfExists(tags, "tags",
                () -> JsonUtils.getStringList(application, "tags"));
        return tags;
    }

    public InstallParams getInstallParams() {
        installParams = loader.loadIfExists(installParams, "install_params",
                () -> new InstallParams(application.get("install_params")));
        return installParams;
    }

    public IntegrationTypesConfig getIntegrationTypesConfig() {
        integrationTypesConfig = loader.loadIfExists(integrationTypesConfig, "integration_types_config", IntegrationTypesConfig::new);
        return integrationTypesConfig;
    }

    private String getBotId() {
        return application.get("bot").get("id").asText();
    }

    private String getGuildId() {
        return application.get("guild_id").asText();
    }

    private String getCoverImageId() {
        return application.get("cover_image").asText();
    }

    private String getOwnerId() {
        return application.get("owner").get("id").asText();
    }
}
