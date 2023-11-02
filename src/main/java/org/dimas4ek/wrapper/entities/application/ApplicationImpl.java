package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.application.team.Team;
import org.dimas4ek.wrapper.entities.application.team.TeamImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.image.ApplicationIcon;
import org.dimas4ek.wrapper.types.ActivityType;
import org.dimas4ek.wrapper.types.ApplicationFlag;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApplicationImpl implements Application {
    private final JSONObject application;

    public ApplicationImpl(JSONObject application) {
        this.application = application;
    }

    @Override
    public String getId() {
        return application.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return application.getString("name");
    }

    @Override
    public String getDescription() {
        return application.getString("description");
    }

    @Override
    public Guild getGuild() {
        JSONObject guildJson = application.optJSONObject("guild");
        return guildJson != null ? new GuildImpl(JsonUtils.fetchEntity("/guilds/" + guildJson.getString("id"))) : null;
    }

    @Override
    public int getGuildCount() {
        return application.optInt("approximate_guild_count", 0);
    }

    @Override
    public List<String> getRedirectURIs() {
        List<String> uris = new ArrayList<>();
        if (application.has("redirect_uris")) {
            for (int i = 0; i < application.getJSONArray("redirect_uris").length(); i++) {
                uris.add(application.getJSONArray("redirect_uris").getString(i));
            }
        }
        return uris;
    }

    @Override
    public String getInteractionEndpointUrl() {
        return application.optString("interactions_endpoint_url", null);
    }

    @Override
    public String getVerificationUrl() {
        return application.optString("role_connections_verification_url", null);
    }

    @Override
    public String getInstallUrl() {
        return application.optString("custom_install_url", null);
    }

    @Override
    public ApplicationIcon getIcon() {
        return new ApplicationIcon(getId(), application.getString("icon"));
    }

    @Override
    public ApplicationIcon getCoverImage() {
        String coverImage = application.optString("cover_image", null);
        return coverImage != null ? new ApplicationIcon(getId(), coverImage) : null;
    }

    @Override
    public ActivityType getType() {
        return EnumUtils.getEnumObject(application, "type", ActivityType.class);
    }

    @Override
    public User getBot() {
        JSONObject botJson = application.optJSONObject("bot");
        return botJson != null ? new UserImpl(botJson) : null;
    }

    @Override
    public User getOwner() {
        JSONObject ownerJson = application.optJSONObject("owner");
        return ownerJson != null ? new UserImpl(JsonUtils.fetchEntity("/users/" + ownerJson.getString("id"))) : null;
    }

    @Override
    public Team getTeam() {
        JSONObject teamJson = application.optJSONObject("team");
        return teamJson != null ? new TeamImpl(teamJson) : null;
    }

    @Override
    public boolean isPublicBot() {
        return application.optBoolean("bot_public", false);
    }

    @Override
    public boolean isBotRequiresOAuth() {
        return application.optBoolean("bot_require_code_grant", false);
    }

    @Override
    public String getTOSUrl() {
        return application.optString("terms_of_service_url", null);
    }

    @Override
    public String getPPUrl() {
        return application.optString("privacy_policy_url", null);
    }

    @Override
    public String getVerifyKey() {
        return application.getString("verify_key");
    }

    @Override
    public List<ApplicationFlag> getFlags() {
        return EnumUtils.getEnumListFromLong(application, "flags", ApplicationFlag.class);
    }

    @Override
    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        if (application.has("tags")) {
            for (int i = 0; i < application.getJSONArray("tags").length(); i++) {
                tags.add(application.getJSONArray("tags").getString(i));
            }
        }
        return tags;
    }

    @Override
    public boolean isMonetized() {
        return application.optBoolean("is_monetized", false);
    }
}
