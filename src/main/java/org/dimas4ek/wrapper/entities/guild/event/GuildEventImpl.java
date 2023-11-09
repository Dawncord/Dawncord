package org.dimas4ek.wrapper.entities.guild.event;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.GuildEventModifyAction;
import org.dimas4ek.wrapper.entities.GuildMember;
import org.dimas4ek.wrapper.entities.GuildMemberImpl;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.image.GuildEventImage;
import org.dimas4ek.wrapper.types.GuildEventEntityType;
import org.dimas4ek.wrapper.types.GuildEventPrivacyLevel;
import org.dimas4ek.wrapper.types.GuildEventStatus;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuildEventImpl implements GuildEvent {
    private final JSONObject event;

    public GuildEventImpl(JSONObject event) {
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
    public GuildEventPrivacyLevel getPrivacyLevel() {
        return GuildEventPrivacyLevel.GUILD_ONLY;
    }

    @Override
    public GuildEventStatus getStatus() {
        return EnumUtils.getEnumObject(event, "status", GuildEventStatus.class);
        /*for (GuildScheduledEventStatus status : GuildScheduledEventStatus.values()) {
            if (status.getValue() == event.getInt("status")) {
                return status;
            }
        }
        return null;*/
    }

    @Override
    public GuildEventEntityType getEntityType() {
        return EnumUtils.getEnumObject(event, "entity_type", GuildEventEntityType.class);
        /*for (GuildScheduledEventEntityType type : GuildScheduledEventEntityType.values()) {
            if (type.getValue() == event.getInt("entity_type")) {
                return type;
            }
        }
        return null;*/
    }

    @Override
    public boolean inChannel() {
        return getEntityType() != GuildEventEntityType.EXTERNAL;
    }

    @Override
    public int getEntityId() {
        return event.optInt("entity_id", 0);
    }

    @Override
    public String getLocation() {
        JSONObject entityMetadata = event.optJSONObject("entity_metadata");
        return entityMetadata != null
                ? entityMetadata.getString("location")
                : null;
    }

    @Override
    public int getMemberCount() {
        return JsonUtils.fetchEntityParams(
                "/guilds/" + getGuild().getId() + "/scheduled-events/" + getId(),
                Map.of("with_user_count", "true")
        ).getInt("user_count");
    }

    @Override
    public List<GuildMember> getGuildEventMembers() {
        return getGuildEventMembers(100);
    }

    @Override
    public List<GuildMember> getGuildEventMembers(int limit) {
        JSONArray eventMembers = JsonUtils.fetchArrayParams(
                "/guilds/" + getGuild().getId() + "/scheduled-events/" + getId() + "/users",
                Map.of(
                        "with_member", "true",
                        "limit", String.valueOf(limit)
                )
        );
        List<GuildMember> members = new ArrayList<>();
        for (int i = 0; i < eventMembers.length(); i++) {
            members.add(new GuildMemberImpl(
                            JsonUtils.fetchEntity("/guilds/" + getGuild().getId()),
                            eventMembers.getJSONObject(i).getJSONObject("member")
                    )
            );
        }
        return members;
    }

    @Override
    public GuildEventImage getImage() {
        return new GuildEventImage(getId(), event.getString("image"));
    }

    @Override
    public GuildEventModifyAction modify() {
        return new GuildEventModifyAction(getGuild().getId(), getId());
    }

    @Override
    public void delete() {
        ApiClient.delete("/guilds/" + getGuild().getId() + "/scheduled-events/" + getId());
    }
}
