package org.dimas4ek.wrapper.event;

import org.dimas4ek.wrapper.entities.VoiceState;
import org.dimas4ek.wrapper.entities.guild.Guild;

public class VoiceStateUpdateEvent implements Event {
    private final Guild guild;
    private final VoiceState voiceState;

    public VoiceStateUpdateEvent(Guild guild, VoiceState voiceState) {
        this.guild = guild;
        this.voiceState = voiceState;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    public VoiceState getVoiceState() {
        return voiceState;
    }
}
