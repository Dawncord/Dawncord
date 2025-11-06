package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;

/**
 * Represents the voice state of a member in a guild.
 */
public class VoiceState {
    private final JsonNode state;
    private final Guild guild;
    private GuildChannel channel;
    private GuildMember member;
    private String sessionId;
    private Boolean deaf;
    private Boolean mute;
    private Boolean selfDeaf;
    private Boolean selfMute;
    private Boolean selfStream;
    private Boolean selfVideo;
    private Boolean suppress;
    private ZonedDateTime requestToSpeakTimestamp;

    /**
     * Constructs a new VoiceState instance with the given JSON node and guild.
     *
     * @param state The JSON node representing the voice state.
     * @param guild The guild to which the voice state belongs.
     */
    public VoiceState(JsonNode state, Guild guild) {
        this.state = state;
        this.guild = guild;
    }

    /**
     * Retrieves the guild to which the voice state belongs.
     *
     * @return The guild.
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the voice channel associated with this voice state.
     *
     * @return The voice channel.
     */
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(state.path("channel_id").asText());
        }
        return channel;
    }

    /**
     * Retrieves the member associated with this voice state.
     *
     * @return The guild member.
     */
    public GuildMember getMember() {
        if (member == null) {
            member = guild.getMemberById(state.path("user_id").asText());
        }
        return member;
    }

    /**
     * Retrieves the session ID associated with this voice state.
     *
     * @return The session ID.
     */
    public String getSessionId() {
        if (sessionId == null) {
            sessionId = state.path("session_id").asText();
        }
        return sessionId;
    }

    /**
     * Checks if the user is deafened in the voice channel.
     *
     * @return True if the user is deafened, false otherwise.
     */
    public Boolean isDeaf() {
        if (deaf == null) {
            deaf = state.path("deaf").asBoolean();
        }
        return deaf;
    }

    /**
     * Checks if the user is muted in the voice channel.
     *
     * @return True if the user is muted, false otherwise.
     */
    public Boolean isMute() {
        if (mute == null) {
            mute = state.path("mute").asBoolean();
        }
        return mute;
    }

    /**
     * Checks if the user is self-deafened in the voice channel.
     *
     * @return True if the user is self-deafened, false otherwise.
     */
    public Boolean isSelfDeaf() {
        if (selfDeaf == null) {
            selfDeaf = state.path("self_deaf").asBoolean();
        }
        return selfDeaf;
    }

    /**
     * Checks if the user is self-muted in the voice channel.
     *
     * @return True if the user is self-muted, false otherwise.
     */
    public Boolean isSelfMute() {
        if (selfMute == null) {
            selfMute = state.path("self_mute").asBoolean();
        }
        return selfMute;
    }

    /**
     * Checks if the user is streaming in the voice channel.
     *
     * @return True if the user is streaming, false otherwise.
     */
    public Boolean isSelfStream() {
        if (selfStream == null) {
            selfStream = state.path("self_stream").asBoolean();
        }
        return selfStream;
    }

    /**
     * Checks if the user's video is enabled in the voice channel.
     *
     * @return True if the user's video is enabled, false otherwise.
     */
    public Boolean isSelfVideo() {
        if (selfVideo == null) {
            selfVideo = state.path("self_video").asBoolean();
        }
        return selfVideo;
    }

    /**
     * Checks if the user's voice is being suppressed in the voice channel.
     *
     * @return True if the user's voice is being suppressed, false otherwise.
     */
    public Boolean isSuppress() {
        if (suppress == null) {
            suppress = state.path("suppress").asBoolean();
        }
        return suppress;
    }

    /**
     * Retrieves the timestamp when the user requested to speak.
     *
     * @return The timestamp of the request to speak.
     */
    public ZonedDateTime getRequestToSpeakTimestamp() {
        if (requestToSpeakTimestamp == null) {
            requestToSpeakTimestamp = TimeUtils.getZonedDateTime(state, "request_to_speak_timestamp");
        }
        return requestToSpeakTimestamp;
    }

}
