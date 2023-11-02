package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildScheduledEvent;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.types.GuildScheduledEventEntityType;
import org.dimas4ek.wrapper.types.GuildScheduledEventPrivacyLevel;
import org.dimas4ek.wrapper.types.GuildScheduledEventStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONObject;

import java.time.ZonedDateTime;

public class GuildScheduledEventImpl implements GuildScheduledEvent {
    private final JSONObject event;

    public GuildScheduledEventImpl(JSONObject event) {
        this.event = event;
    }

    @Override
    public String getId() {
        return event.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return event.getString("name");
    }

    @Override
    public String getDescription() {
        return event.getString("description");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + event.getString("guild_id")));
    }

    @Override
    public GuildChannel getChannel() {
        return getGuild().getChannelById(event.getString("channel_id"));
    }

    @Override
    public User getCreator() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + event.getString("creator_id")));
    }

    @Override
    public ZonedDateTime getStartTimestamp() {
        return MessageUtils.getZonedDateTime(event, "scheduled_start_time");
    }

    @Override
    public ZonedDateTime getEndTimestamp() {
        return MessageUtils.getZonedDateTime(event, "scheduled_end_time");
    }

    @Override
    public GuildScheduledEventPrivacyLevel getPrivacyLevel() {
        return GuildScheduledEventPrivacyLevel.GUILD_ONLY;
    }

    @Override
    public GuildScheduledEventStatus getStatus() {
        return EnumUtils.getEnumObject(event, "status", GuildScheduledEventStatus.class);
        /*for (GuildScheduledEventStatus status : GuildScheduledEventStatus.values()) {
            if (status.getValue() == event.getInt("status")) {
                return status;
            }
        }
        return null;*/
    }

    @Override
    public GuildScheduledEventEntityType getEntityType() {
        return EnumUtils.getEnumObject(event, "entity_type", GuildScheduledEventEntityType.class);
        /*for (GuildScheduledEventEntityType type : GuildScheduledEventEntityType.values()) {
            if (type.getValue() == event.getInt("entity_type")) {
                return type;
            }
        }
        return null;*/
    }

    @Override
    public int getMemberCount() {
        return event.getInt("user_count");
    }
}
