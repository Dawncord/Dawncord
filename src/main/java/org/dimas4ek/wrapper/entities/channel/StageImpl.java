package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildEventImpl;
import org.dimas4ek.wrapper.types.StagePrivacyLevel;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

import java.util.Map;

public class StageImpl implements Stage {
    private final JSONObject stage;

    public StageImpl(JSONObject stage) {
        this.stage = stage;
    }

    @Override
    public String getId() {
        return stage.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + stage.getString("guild_id")));
    }

    @Override
    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + stage.getString("guild_id")));
    }

    @Override
    public String getTopic() {
        return stage.getString("topic");
    }

    @Override
    public StagePrivacyLevel getPrivacyLevel() {
        return StagePrivacyLevel.GUILD_ONLY;
    }

    @Override
    public boolean isDiscoverable() {
        return !stage.getBoolean("discoverable_disabled");
    }

    @Override
    public GuildEvent getEvent() {
        return new GuildEventImpl(JsonUtils.fetchEntityParams(
                "/guilds/" + getGuild().getId() + "/scheduled-events/" + stage.getString("guild_scheduled_event_id"),
                Map.of("with_user_count", "true"))
        );
    }

    @Override
    public void delete() {
        ApiClient.delete("/stage-instances/" + getId());
    }

    @Override
    public void modify(String topic) {
        ApiClient.patch(new JSONObject().put("topic", topic), "/stage-instances/" + getId());
    }
}
