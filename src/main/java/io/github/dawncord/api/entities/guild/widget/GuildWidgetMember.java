package io.github.dawncord.api.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.types.OnlineStatus;
import io.github.dawncord.api.utils.EnumUtils;

/**
 * Represents a member in a guild's widget.
 */
public class GuildWidgetMember implements ISnowflake {
    private final JsonNode member;
    private String id;
    private String username;
    private Avatar avatar;
    private OnlineStatus onlineStatus;

    /**
     * Constructs a new GuildWidgetMember object with the provided JSON node.
     *
     * @param member The JSON node representing the guild widget member.
     */
    public GuildWidgetMember(JsonNode member) {
        this.member = member;
    }

    /**
     * Retrieves the ID of the guild widget member.
     *
     * @return The ID of the member.
     */
    @Override
    public String getId() {
        if (id == null) {
            id = member.get("id").asText();
        }
        return id;
    }

    /**
     * Retrieves the ID of the guild widget member as a long value.
     *
     * @return The ID of the member as a long.
     */
    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    /**
     * Retrieves the username of the guild widget member.
     *
     * @return The username of the member.
     */
    public String getUsername() {
        if (username == null) {
            username = member.get("username").asText();
        }
        return username;
    }

    /**
     * Retrieves the avatar of the guild widget member.
     *
     * @return The avatar of the member.
     */
    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = member.has("avatar") && member.hasNonNull("avatar")
                    ? new Avatar(getId(), member.get("avatar").asText())
                    : null;
        }
        return avatar;
    }

    /**
     * Retrieves the online status of the guild widget member.
     *
     * @return The online status of the member.
     */
    public OnlineStatus getOnlineStatus() {
        if (onlineStatus == null) {
            onlineStatus = EnumUtils.getEnumObject(member, "status", OnlineStatus.class);
        }
        return onlineStatus;
    }
}
