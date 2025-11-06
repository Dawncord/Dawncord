package io.github.dawncord.api.entities.guild.event;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.GuildEventModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.UserImpl;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildImpl;
import io.github.dawncord.api.entities.guild.GuildMember;
import io.github.dawncord.api.entities.guild.GuildMemberImpl;
import io.github.dawncord.api.entities.image.GuildEventImage;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.GuildEventEntityType;
import io.github.dawncord.api.types.GuildEventPrivacyLevel;
import io.github.dawncord.api.types.GuildEventStatus;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class GuildScheduledEvent implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode event;
    private String id;
    private String name;
    private String description;
    private GuildChannel channel;
    private Guild guild;
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
     */
    public GuildScheduledEvent(JsonNode event) {
        this.event = event;
        loader = new LazyLoader(event);
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

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public String getDescription() {
        description = loader.loadString(description, "description");
        return description;
    }

    public Guild getGuild() {
        guild = loader.load(guild, () -> new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(event.get("guild_id").asText()))));
        return guild;
    }

    public GuildChannel getChannel() {
        channel = loader.load(channel, () -> guild.getChannelById(event.get("channel_id").asText()));
        return channel;
    }

    public User getCreator() {
        creator = loader.load(creator, () -> new UserImpl(event.get("creator")));
        return creator;
    }

    public ZonedDateTime getStartTimestamp() {
        startTimestamp = loader.loadZonedDateTime(startTimestamp, "scheduled_start_time");
        return startTimestamp;
    }

    public ZonedDateTime getEndTimestamp() {
        endTimestamp = loader.loadZonedDateTime(endTimestamp, "scheduled_end_time");
        return endTimestamp;
    }

    public GuildEventPrivacyLevel getPrivacyLevel() {
        return GuildEventPrivacyLevel.GUILD_ONLY;
    }

    public GuildEventStatus getStatus() {
        status = loader.loadEnumObject(status, "status", GuildEventStatus.class);
        return status;
    }

    public GuildEventEntityType getEntityType() {
        entityType = loader.loadEnumObject(entityType, "entity_type", GuildEventEntityType.class);
        return entityType;
    }

    public boolean inChannel() {
        return getEntityType() != GuildEventEntityType.EXTERNAL;
    }

    public String getEntityId() {
        entityId = loader.loadString(entityId, "entity_id");
        return entityId;
    }

    public long getEntityIdLong() {
        return Long.parseLong(getEntityId());
    }

    public String getLocation() {
        location = loader.load(location, () -> {
            if (event.has("entity_metadata") && event.hasNonNull("entity_metadata")) {
                JsonNode metadata = event.get("entity_metadata");
                if (metadata.has("location") && metadata.hasNonNull("location")) {
                    location = metadata.get("location").asText();
                }
            }
            return location;
        });
        return location;
    }

    public int getMemberCount() {
        memberCount = loader.loadInt(memberCount, "user_count");
        return memberCount;
    }

    public List<GuildMember> getGuildEventMembers() {
        return getGuildEventMembers(100);
    }

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

    public GuildEventImage getImage() {
        image = loader.loadIfExists(image, "image", () -> new GuildEventImage(getId(), event.get("image").asText()));
        return image;
    }

    public ModifyEvent<GuildScheduledEvent> modify(Consumer<GuildEventModifyAction> handler) {
        ActionExecutor.modifyGuildEvent(handler, guild.getId(), getId());
        return new ModifyEvent<>(guild.getGuildEventById(getId()));
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.ScheduledEvent.Get(guild.getId(), getId()));
    }
}
