package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.application.team.Team;
import io.github.dawncord.api.entities.application.team.TeamImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.types.ActivityType;
import io.github.dawncord.api.types.ApplicationFlag;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;

/**
 * Represents an implementation of an application.
 * ApplicationImpl is a class providing methods to access properties of an application.
 */
public class ApplicationImpl implements Application {
    private final JsonNode application;
    private Guild guild;
    private String id;
    private String name;
    private String description;
    private Integer guildCount;
    private List<String> redirectURIs;
    private String interactionEndpointUrl;
    private String verificationUrl;
    private String customInstallUrl;
    private ApplicationIcon icon;
    private ApplicationIcon coverImage;
    private ActivityType type;
    private User bot;
    private User owner;
    private Team team;
    private Boolean publicBot;
    private Boolean requiresOAuth;
    private String tosUrl;
    private String ppUrl;
    private String verifyKey;
    private List<ApplicationFlag> flags;
    private List<String> tags;
    private Boolean monetized;
    private InstallParams installParams;

    /**
     * Constructs an ApplicationImpl object with the provided JSON node and guild.
     *
     * @param application The JSON node representing the application.
     * @param guild       The guild associated with the application.
     */
    public ApplicationImpl(JsonNode application, Guild guild) {
        this.application = application;
        this.guild = guild;
    }

    /**
     * Constructs an ApplicationImpl object with the provided JSON node and guild.
     *
     * @param application The JSON node representing the application.
     */
    public ApplicationImpl(JsonNode application) {
        this.application = application;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = application.get("id").asText();
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
            name = application.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = application.get("description").asText();
        }
        return description;
    }

    @Override
    public Guild getGuild() {
        if (guild == null) {
            guild = new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(application.get("guild_id").asText())));
        }
        return guild;
    }

    @Override
    public int getGuildCount() {
        if (guildCount == null && application.has("guild_count")) {
            guildCount = application.get("guild_count").asInt();
        }
        return guildCount != null ? guildCount : 0;
    }

    @Override
    public List<String> getRedirectURIs() {
        if (redirectURIs == null) {
            redirectURIs = JsonUtils.getStringList(application, "redirect_uris");
        }
        return redirectURIs;
    }

    @Override
    public String getInteractionEndpointUrl() {
        if (interactionEndpointUrl == null) {
            interactionEndpointUrl = application.get("interaction_endpoint_url").asText();
        }
        return interactionEndpointUrl;
    }

    @Override
    public String getVerificationUrl() {
        if (verificationUrl == null) {
            verificationUrl = application.get("verification_url").asText();
        }
        return verificationUrl;
    }

    @Override
    public String getCustomInstallUrl() {
        if (customInstallUrl == null) {
            customInstallUrl = application.get("custom_install_url").asText();
        }
        return customInstallUrl;
    }

    @Override
    public ApplicationIcon getIcon() {
        if (icon == null) {
            icon = application.has("icon") ? new ApplicationIcon(getId(), application.get("icon").asText()) : null;
        }
        return icon;
    }

    @Override
    public ApplicationIcon getCoverImage() {
        if (coverImage == null) {
            coverImage = application.has("cover_image") ? new ApplicationIcon(getId(), application.get("cover_image").asText()) : null;
        }
        return coverImage;
    }

    @Override
    public ActivityType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(application, "type", ActivityType.class);
        }
        return type;
    }

    @Override
    public User getBot() {
        if (bot == null) {
            bot = application.has("bot") ? new UserImpl(application.get("bot")) : null;
        }
        return bot;
    }

    @Override
    public User getOwner() {
        if (owner == null) {
            owner = application.has("owner") ? new UserImpl(application.get("owner")) : null;
        }
        return owner;
    }

    @Override
    public Team getTeam() {
        if (team == null) {
            team = application.has("team") ? new TeamImpl(application.get("team")) : null;
        }
        return team;
    }

    @Override
    public boolean isPublicBot() {
        if (publicBot == null && application.has("public_bot")) {
            publicBot = application.get("public_bot").asBoolean(false);
        }
        return publicBot != null && publicBot;
    }

    @Override
    public boolean isBotRequiresOAuth() {
        if (requiresOAuth == null && application.has("require_oauth")) {
            requiresOAuth = application.get("require_oauth").asBoolean();
        }
        return requiresOAuth != null && requiresOAuth;
    }

    @Override
    public String getTOSUrl() {
        if (tosUrl == null) {
            tosUrl = application.get("tos_url").asText(null);
        }
        return tosUrl;
    }

    @Override
    public String getPPUrl() {
        if (ppUrl == null) {
            ppUrl = application.get("privacy_policy_url").asText(null);
        }
        return ppUrl;
    }

    @Override
    public String getVerifyKey() {
        if (verifyKey == null) {
            verifyKey = application.get("verify_key").asText(null);
        }
        return verifyKey;
    }

    @Override
    public List<ApplicationFlag> getFlags() {
        if (flags == null) {
            flags = EnumUtils.getEnumListFromLong(application, "flags", ApplicationFlag.class);
        }
        return flags;
    }

    @Override
    public List<String> getTags() {
        if (tags == null) {
            tags = JsonUtils.getStringList(application, "tags");
        }
        return tags;
    }

    @Override
    public boolean isMonetized() {
        if (monetized == null && application.has("monetized")) {
            monetized = application.get("monetized").asBoolean(false);
        }
        return monetized != null && monetized;
    }

    @Override
    public InstallParams getInstallParams() {
        if (installParams == null) {
            installParams = application.has("install_params") ? new InstallParams(application.get("install_params")) : null;
        }
        return installParams;
    }
}
