package io.github.dawncord.api.event;

import io.github.dawncord.api.entities.VoiceState;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an event triggered when the voice state of a member in a guild is updated.
 */
public class VoiceStateUpdateEvent implements Event {
    private final Guild guild;
    private final VoiceState voiceState;

    /**
     * Constructs a VoiceStateUpdateEvent with the specified guild and voice state.
     *
     * @param guild      The guild associated with the voice state update event.
     * @param voiceState The updated voice state of a member.
     */
    public VoiceStateUpdateEvent(Guild guild, VoiceState voiceState) {
        this.guild = guild;
        this.voiceState = voiceState;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the updated voice state of a member.
     *
     * @return The updated voice state of a member.
     */
    public VoiceState getVoiceState() {
        return voiceState;
    }
}
