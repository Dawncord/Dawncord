package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.channel.Stage;
import org.dimas4ek.wrapper.entities.guild.Guild;

public class StageEvent implements Event {
    private final Guild guild;
    private final Stage stage;

    public StageEvent(Guild guild, Stage stage) {
        this.guild = guild;
        this.stage = stage;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public Stage getStage() {
        return stage;
    }
}
