package org.dimas4ek.wrapper.entities.guild.welcomescreen;

import org.dimas4ek.wrapper.action.GuildWelcomeScreenModifyAction;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GuildWelcomeScreen {
    public final Guild guild;
    private final JSONObject welcomeScreen;

    public GuildWelcomeScreen(Guild guild, JSONObject welcomeScreen) {
        this.welcomeScreen = welcomeScreen;
        this.guild = guild;
    }

    public String getDescription() {
        return welcomeScreen.optString("description", null);
    }

    public List<GuildWelcomeChannel> getChannels() {
        JSONArray welcomeChannels = welcomeScreen.optJSONArray("welcome_channels");
        return welcomeChannels != null
                ? JsonUtils.getEntityList(welcomeChannels, GuildWelcomeChannel::new)
                : null;
    }

    public GuildWelcomeScreenModifyAction modify() {
        return new GuildWelcomeScreenModifyAction(guild.getId());
    }
}
