package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEventImpl;
import io.github.dawncord.api.types.StagePrivacyLevel;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.Map;

/**
 * Implementation of a stage in a guild.
 */
public class StageImpl implements Stage {
    private final JsonNode stage;
    private final Guild guild;
    private String id;
    private GuildChannel channel;
    private String topic;
    private Boolean isDiscoverable;
    private GuildScheduledEvent guildEvent;

    /**
     * Constructs a new StageImpl object.
     *
     * @param stage The JSON representation of the stage.
     * @param guild The guild to which this stage belongs.
     */
    public StageImpl(JsonNode stage, Guild guild) {
        this.stage = stage;
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = stage.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = new GuildChannelImpl(JsonUtils.fetch(Routes.Channel.Get(stage.get("channel_id").asText())), guild);
        }
        return channel;
    }

    @Override
    public String getTopic() {
        if (topic == null) {
            topic = stage.get("topic").asText();
        }
        return topic;
    }

    @Override
    public StagePrivacyLevel getPrivacyLevel() {
        return StagePrivacyLevel.GUILD_ONLY;
    }

    @Override
    public boolean isDiscoverable() {
        if (isDiscoverable == null) {
            isDiscoverable = !stage.get("discoverable_disabled").asBoolean();
        }
        return isDiscoverable;
    }

    @Override
    public GuildScheduledEvent getGuildEvent() {
        if (guildEvent == null) {
            guildEvent = new GuildScheduledEventImpl(
                    JsonUtils.fetchParams(
                            Routes.Guild.ScheduledEvent.Get(guild.getId(), stage.get("guild_scheduled_event_id").asText()),
                            Map.of("with_user_count", "true")
                    ), guild);
        }
        return guildEvent;
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.StageInstance(getId()));
    }

    @Override
    public void modify(String topic) {
        ApiClient.patch(JsonNodeFactory.instance.objectNode().put("topic", topic), Routes.StageInstance(getId()));
    }
}
