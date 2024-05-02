package io.github.dawncord.api.entities.guild.welcomescreen;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.action.GuildWelcomeScreenModifyAction;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the welcome screen configuration for a guild.
 */
public class GuildWelcomeScreen {

    private final JsonNode welcomeScreen;
    private final Guild guild;
    private String description;
    private List<GuildWelcomeChannel> welcomeChannels;

    /**
     * Constructs a new GuildWelcomeScreen object with the provided JSON node and guild.
     *
     * @param welcomeScreen The JSON node representing the welcome screen configuration.
     * @param guild         The guild to which the welcome screen belongs.
     */
    public GuildWelcomeScreen(JsonNode welcomeScreen, Guild guild) {
        this.welcomeScreen = welcomeScreen;
        this.guild = guild;
    }

    /**
     * Retrieves the description of the welcome screen.
     *
     * @return The description of the welcome screen, or null if not available.
     */
    public String getDescription() {
        if (description == null && welcomeScreen.has("description")) {
            description = welcomeScreen.get("description").asText();
        }
        return description;
    }

    /**
     * Retrieves the list of welcome channels configured on the welcome screen.
     *
     * @return The list of welcome channels.
     */
    public List<GuildWelcomeChannel> getChannels() {
        if (welcomeChannels == null && welcomeScreen.has("welcome_channels")) {
            welcomeChannels = JsonUtils.getEntityList(welcomeScreen.get("welcome_channels"), channel -> new GuildWelcomeChannel(channel, guild));
        }
        return welcomeChannels;
    }

    /**
     * Modifies the welcome screen configuration using the provided handler.
     *
     * @param handler The consumer handler for modifying the welcome screen.
     * @return A ModifyEvent representing the modification action.
     */
    public ModifyEvent<GuildWelcomeScreen> modify(Consumer<GuildWelcomeScreenModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getWelcomeScreen());
    }
}
