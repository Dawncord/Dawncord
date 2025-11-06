package io.github.dawncord.api.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a guild widget, providing access to its properties and members.
 */
public class GuildWidget {

    private final JsonNode widget;
    private final Guild guild;
    private String guildId;
    private String guildName;
    private String instantInvite;
    private final List<GuildChannel> channels = new ArrayList<>();
    private List<GuildWidgetMember> members;
    private Integer onlineMembersCount;

    /**
     * Constructs a new GuildWidget object with the provided JSON node and guild.
     *
     * @param widget The JSON node representing the guild widget.
     * @param guild  The guild to which the widget belongs.
     */
    public GuildWidget(JsonNode widget, Guild guild) {
        this.widget = widget;
        this.guild = guild;
    }

    /**
     * Retrieves the ID of the guild associated with the widget.
     *
     * @return The ID of the guild.
     */
    public String getGuildId() {
        if (guildId == null) {
            guildId = widget.get("id").asText();
        }
        return guildId;
    }

    /**
     * Retrieves the ID of the guild as a long value.
     *
     * @return The ID of the guild as a long.
     */
    public long getGuildIdLong() {
        return Long.parseLong(getGuildId());
    }

    /**
     * Retrieves the name of the guild associated with the widget.
     *
     * @return The name of the guild.
     */
    public String getGuildName() {
        if (guildName == null) {
            guildName = widget.get("name").asText();
        }
        return guildName;
    }

    /**
     * Retrieves the instant invite link to the guild.
     *
     * @return The instant invite link.
     */
    public String getInstantInvite() {
        if (instantInvite == null) {
            instantInvite = widget.get("instant_invite").asText();
        }
        return instantInvite;
    }

    /**
     * Retrieves the list of channels in the guild's widget.
     *
     * @return The list of guild channels.
     */
    public List<GuildChannel> getChannels() {
        if (channels.isEmpty()) {
            for (JsonNode channelNode : widget.get("channels")) {
                channels.add(guild.getChannelById(channelNode.get("id").asText()));
            }
        }
        return channels;
    }

    /**
     * Retrieves the list of members in the guild's widget.
     *
     * @return The list of guild widget members.
     */
    public List<GuildWidgetMember> getMembers() {
        if (members == null) {
            members = JsonUtils.getEntityList(widget.get("members"), GuildWidgetMember::new);
        }
        return members;
    }

    /**
     * Retrieves the count of online members in the guild's widget.
     *
     * @return The count of online members.
     */
    public int getOnlineMembersCount() {
        if (onlineMembersCount == null) {
            onlineMembersCount = widget.get("presence_count").asInt();
        }
        return onlineMembersCount;
    }
}
