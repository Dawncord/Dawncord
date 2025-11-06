package io.github.dawncord.api.entities.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.types.StagePrivacyLevel;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.Map;

/**
 * Implementation of a stage in a guild.
 */
public class Stage implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode stage;
    private final Guild guild;
    private String id;
    private GuildChannel channel;
    private String topic;
    private GuildScheduledEvent guildEvent;

    /**
     * Constructs a new StageImpl object.
     *
     * @param stage The JSON representation of the stage.
     * @param guild The guild to which this stage belongs.
     */
    public Stage(JsonNode stage, Guild guild) {
        this.stage = stage;
        this.guild = guild;
        loader = new LazyLoader(stage);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildChannel getChannel() {
        channel = loader.load(channel,
                () -> new GuildChannel(JsonUtils.fetch(Routes.Channel.Get(stage.get("channel_id").asText())), guild));
        return channel;
    }

    public String getTopic() {
        topic = loader.loadString(topic, "topic");
        return topic;
    }

    public StagePrivacyLevel getPrivacyLevel() {
        return StagePrivacyLevel.GUILD_ONLY;
    }

    public GuildScheduledEvent getGuildEvent() {
        guildEvent = loader.load(guildEvent, () -> new GuildScheduledEvent(
                JsonUtils.fetchParams(
                        Routes.Guild.ScheduledEvent.Get(guild.getId(), stage.get("guild_scheduled_event_id").asText()),
                        Map.of("with_user_count", "true")
                ))
        );
        return guildEvent;
    }

    public void delete() {
        ApiClient.delete(Routes.StageInstance(getId()));
    }

    public void modify(String topic) {
        ApiClient.patch(JsonNodeFactory.instance.objectNode().put("topic", topic), Routes.StageInstance(getId()));
    }
}
