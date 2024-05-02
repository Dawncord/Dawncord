package io.github.dawncord.api.entities.guild.event;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildEventModifyAction;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.GuildMemberImpl;
import io.github.dawncord.api.entities.image.GuildEventImage;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.GuildEventEntityType;
import io.github.dawncord.api.types.GuildEventPrivacyLevel;
import io.github.dawncord.api.types.GuildEventStatus;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.MessageUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementation of the {@link GuildScheduledEvent} interface representing a scheduled event in a guild.
 */
public class GuildScheduledEventImpl implements GuildScheduledEvent {
    private final JsonNode event;
    private final Guild guild;
    private String id;
    private String name;
    private String description;
    private GuildChannel channel;
    private User creator;
    private ZonedDateTime startTimestamp;
    private ZonedDateTime endTimestamp;
    private GuildEventStatus status;
    private GuildEventEntityType entityType;
    private String entityId;
    private String location;
    private Integer memberCount;
    private GuildEventImage image;

    /**
     * Constructs a new instance of GuildScheduledEventImpl with the given JSON node representing the event
     * and the guild to which the event belongs.
     *
     * @param event The JSON node representing the event.
     * @param guild The guild to which the event belongs.
     */
    public GuildScheduledEventImpl(JsonNode event, Guild guild) {
        this.event = event;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = event.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = event.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = event.get("description").asText();
        }
        return description;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(event.get("channel_id").asText());
        }
        return channel;
    }

    @Override
    public User getCreator() {
        if (creator == null) {
            creator = new UserImpl(event.get("creator"));
        }
        return creator;
    }

    @Override
    public ZonedDateTime getStartTimestamp() {
        if (startTimestamp == null) {
            startTimestamp = MessageUtils.getZonedDateTime(event, "scheduled_start_time");
        }
        return startTimestamp;
    }

    @Override
    public ZonedDateTime getEndTimestamp() {
        if (endTimestamp == null) {
            endTimestamp = MessageUtils.getZonedDateTime(event, "scheduled_end_time");
        }
        return endTimestamp;
    }

    @Override
    public GuildEventPrivacyLevel getPrivacyLevel() {
        return GuildEventPrivacyLevel.GUILD_ONLY;
    }

    @Override
    public GuildEventStatus getStatus() {
        if (status == null) {
            status = EnumUtils.getEnumObject(event, "status", GuildEventStatus.class);
        }
        return status;
    }

    @Override
    public GuildEventEntityType getEntityType() {
        if (entityType == null) {
            entityType = EnumUtils.getEnumObject(event, "entity_type", GuildEventEntityType.class);
        }
        return entityType;
    }

    @Override
    public boolean inChannel() {
        return entityType != GuildEventEntityType.EXTERNAL;
    }

    @Override
    public String getEntityId() {
        if (entityId == null) {
            entityId = event.get("entity_id").asText();
        }
        return entityId;
    }

    @Override
    public long getEntityIdLong() {
        return Long.parseLong(getEntityId());
    }

    @Override
    public String getLocation() {
        if (location == null) {
            if (event.has("entity_metadata") && event.hasNonNull("entity_metadata")) {
                JsonNode metadata = event.get("entity_metadata");
                if (metadata.has("location") && metadata.hasNonNull("location")) {
                    location = metadata.get("location").asText();
                }
            }
        }
        return location;
    }

    @Override
    public int getMemberCount() {
        if (memberCount == null) {
            memberCount = event.get("user_count").asInt();
        }
        return memberCount;
    }

    @Override
    public List<GuildMember> getGuildEventMembers() {
        return getGuildEventMembers(100);
    }

    @Override
    public List<GuildMember> getGuildEventMembers(int limit) {
        JsonNode eventMembers = JsonUtils.fetchParams(
                Routes.Guild.ScheduledEvent.Members(guild.getId(), getId()),
                Map.of(
                        "with_member", "true",
                        "limit", String.valueOf(limit)
                )
        );
        List<GuildMember> members = new ArrayList<>();
        for (JsonNode member : eventMembers) {
            members.add(new GuildMemberImpl(member.get("member"), guild));
        }
        return members;
    }

    @Override
    public GuildEventImage getImage() {
        if (image == null) {
            image = new GuildEventImage(getId(), event.get("image").asText());
        }
        return image;
    }

    @Override
    public ModifyEvent<GuildScheduledEvent> modify(Consumer<GuildEventModifyAction> handler) {
        ActionExecutor.modifyGuildEvent(handler, guild.getId(), getId());
        return new ModifyEvent<>(guild.getGuildEventById(getId()));
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.ScheduledEvent.Get(guild.getId(), getId()));
    }
}
