package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.channel.Stage;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event related to a stage channel.
 */
public record StageEvent(Guild guild, Stage stage) implements Event {
    /**
     * Constructs a StageEvent with the specified guild and stage channel.
     *
     * @param guild The guild associated with the stage channel.
     * @param stage The stage channel involved in the event.
     */
    public StageEvent {
    }

    /**
     * Retrieves the stage channel involved in the event.
     *
     * @return The stage channel involved in the event.
     */
    @Override
    public Stage stage() {
        return stage;
    }
}
