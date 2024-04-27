package org.dimas4ek.dawncord.entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.GuildMember;
import org.dimas4ek.dawncord.utils.MessageUtils;

import java.time.ZonedDateTime;

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

    public VoiceState(JsonNode state, Guild guild) {
        this.state = state;
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(state.path("channel_id").asText());
        }
        return channel;
    }

    public GuildMember getMember() {
        if (member == null) {
            member = guild.getMemberById(state.path("user_id").asText());
        }
        return member;
    }

    public String getSessionId() {
        if (sessionId == null) {
            sessionId = state.path("session_id").asText();
        }
        return sessionId;
    }

    public Boolean isDeaf() {
        if (deaf == null) {
            deaf = state.path("deaf").asBoolean();
        }
        return deaf;
    }

    public Boolean isMute() {
        if (mute == null) {
            mute = state.path("mute").asBoolean();
        }
        return mute;
    }

    public Boolean isSelfDeaf() {
        if (selfDeaf == null) {
            selfDeaf = state.path("self_deaf").asBoolean();
        }
        return selfDeaf;
    }

    public Boolean isSelfMute() {
        if (selfMute == null) {
            selfMute = state.path("self_mute").asBoolean();
        }
        return selfMute;
    }

    public Boolean isSelfStream() {
        if (selfStream == null) {
            selfStream = state.path("self_stream").asBoolean();
        }
        return selfStream;
    }

    public Boolean isSelfVideo() {
        if (selfVideo == null) {
            selfVideo = state.path("self_video").asBoolean();
        }
        return selfVideo;
    }

    public Boolean isSuppress() {
        if (suppress == null) {
            suppress = state.path("suppress").asBoolean();
        }
        return suppress;
    }

    public ZonedDateTime getRequestToSpeakTimestamp() {
        if (requestToSpeakTimestamp == null) {
            requestToSpeakTimestamp = MessageUtils.getZonedDateTime(state, "request_to_speak_timestamp");
        }
        return requestToSpeakTimestamp;
    }
}
