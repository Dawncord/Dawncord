package org.dimas4ek.dawncord.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class GuildWidget {
    private final JsonNode widget;
    private final Guild guild;
    private String guildId;
    private String guildName;
    private String instantInvite;
    private List<GuildChannel> channels = new ArrayList<>();
    private List<GuildWidgetMember> members;
    private Integer onlineMembersCount;

    public GuildWidget(JsonNode widget, Guild guild) {
        this.widget = widget;
        this.guild = guild;
    }

    public String getGuildId() {
        if (guildId == null) {
            guildId = widget.get("id").asText();
        }
        return guildId;
    }

    public long getGuildIdLong() {
        return Long.parseLong(getGuildId());
    }

    public String getGuildName() {
        if (guildName == null) {
            guildName = widget.get("name").asText();
        }
        return guildName;
    }

    public String getInstantInvite() {
        if (instantInvite == null) {
            instantInvite = widget.get("instant_invite").asText();
        }
        return instantInvite;
    }

    public List<GuildChannel> getChannels() {
        if (channels == null) {
            channels = new ArrayList<>();
            for (JsonNode channelNode : widget.get("channels")) {
                channels.add(guild.getChannelById(channelNode.get("id").asText()));
            }
        }
        return channels;
    }

    public List<GuildWidgetMember> getMembers() {
        if (members == null) {
            members = JsonUtils.getEntityList(widget.get("members"), GuildWidgetMember::new);
        }
        return members;
    }

    public int getOnlineMembersCount() {
        if (onlineMembersCount == null) {
            onlineMembersCount = widget.get("presence_count").asInt();
        }
        return onlineMembersCount;
    }
}
