package org.dimas4ek.dawncord.entities.guild.welcomescreen;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.action.GuildWelcomeScreenModifyAction;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.List;
import java.util.function.Consumer;

public class GuildWelcomeScreen {
    private final JsonNode welcomeScreen;
    private final Guild guild;
    private String description;
    private List<GuildWelcomeChannel> welcomeChannels;

    public GuildWelcomeScreen(JsonNode welcomeScreen, Guild guild) {
        this.welcomeScreen = welcomeScreen;
        this.guild = guild;
    }

    public String getDescription() {
        if (description == null && welcomeScreen.has("description")) {
            description = welcomeScreen.get("description").asText();
        }
        return description;
    }

    public List<GuildWelcomeChannel> getChannels() {
        if (welcomeChannels == null && welcomeScreen.has("welcome_channels")) {
            welcomeChannels = JsonUtils.getEntityList(welcomeScreen.get("welcome_channels"), channel -> new GuildWelcomeChannel(channel, guild));
        }
        return welcomeChannels;
    }

    public ModifyEvent<GuildWelcomeScreen> modify(Consumer<GuildWelcomeScreenModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getWelcomeScreen());
    }
}
