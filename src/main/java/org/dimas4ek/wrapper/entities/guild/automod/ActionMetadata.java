package org.dimas4ek.wrapper.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;

public class ActionMetadata {
    private final JsonNode metadata;
    private final Guild guild;
    private GuildChannel channel;
    private Integer duration;
    private String customMessage;

    public ActionMetadata(JsonNode metadata, Guild guild) {
        this.metadata = metadata;
        this.guild = guild;
    }

    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(metadata.get("channel_id").asText());
        }
        return channel;
    }

    public int getDuration() {
        if (duration == null) {
            duration = metadata.get("duration_seconds").asInt();
        }
        return duration;
    }

    public String getCustomMessage() {
        if (customMessage == null) {
            customMessage = metadata.has("custom_message") && metadata.hasNonNull("custom_message")
                    ? metadata.get("custom_message").asText()
                    : null;
        }
        return customMessage;
    }
}
