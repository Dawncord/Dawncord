package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.Stage;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to a stage channel.
 */
public class StageEvent implements Event {
    private final Guild guild;
    private final Stage stage;

    /**
     * Constructs a StageEvent with the specified guild and stage channel.
     *
     * @param guild The guild associated with the stage channel.
     * @param stage The stage channel involved in the event.
     */
    public StageEvent(Guild guild, Stage stage) {
        this.guild = guild;
        this.stage = stage;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the stage channel involved in the event.
     *
     * @return The stage channel involved in the event.
     */
    public Stage getStage() {
        return stage;
    }
}
