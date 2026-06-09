package io.github.dawncord.api.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.OnlineStatus;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents a member in a guild's widget.
 */
public class GuildWidgetMember {
    private final LazyLoader loader;
    private final JsonNode member;
    private String id;
    private String username;
    private GuildWidgetImage avatar;
    private OnlineStatus onlineStatus;

    /**
     * Constructs a new GuildWidgetMember object with the provided JSON node.
     *
     * @param member The JSON node representing the guild widget member.
     */
    public GuildWidgetMember(JsonNode member) {
        this.member = member;
        loader = new LazyLoader(member);
    }

    /**
     * Retrieves the ID of the guild widget member.
     *
     * @return The ID of the member.
     */
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    /**
     * Retrieves the username of the guild widget member.
     *
     * @return The username of the member.
     */
    public String getUsername() {
        username = loader.loadString(username, "username");
        return username;
    }

    /**
     * Retrieves the avatar of the guild widget member.
     *
     * @return The avatar of the member.
     */
    public GuildWidgetImage getAvatarUrl() {
        avatar = loader.loadIfExists(avatar, "avatar_url", () -> new GuildWidgetImage(member.get("avatar_url").asText()));
        return avatar;
    }

    /**
     * Retrieves the online status of the guild widget member.
     *
     * @return The online status of the member.
     */
    public OnlineStatus getOnlineStatus() {
        onlineStatus = loader.loadEnumObject(onlineStatus, "status", OnlineStatus.class);
        return onlineStatus;
    }
}
