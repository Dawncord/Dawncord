package io.github.dawncord.api.entities.guild.widget;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.GuildWidgetImage;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

/**
 * Represents a guild widget, providing access to its properties and members.
 */
public class GuildWidget {
    private final LazyLoader loader;
    private final JsonNode widget;
    private final Guild guild;
    private String instantInvite;
    private List<GuildChannel> channels;
    private List<GuildWidgetMember> members;
    private Integer onlineMembersCount;
    private GuildWidgetImage image;

    /**
     * Constructs a new GuildWidget object with the provided JSON node and guild.
     *
     * @param widget The JSON node representing the guild widget.
     * @param guild  The guild to which the widget belongs.
     */
    public GuildWidget(JsonNode widget, Guild guild) {
        this.widget = widget;
        this.guild = guild;
        loader = new LazyLoader(widget);
    }

    public Guild getGuild() {
        return guild;
    }

    /**
     * Retrieves the instant invite link to the guild.
     *
     * @return The instant invite link.
     */
    public String getInstantInvite() {
        instantInvite = loader.loadString(instantInvite, "instant_invite");
        return instantInvite;
    }

    /**
     * Retrieves the list of channels in the guild's widget.
     *
     * @return The list of guild channels.
     */
    public List<GuildChannel> getChannels() {
        channels = loader.loadIfExists(channels, "channels", () -> {
            for (JsonNode channelNode : widget.get("channels")) {
                channels.add(guild.getChannelById(channelNode.get("id").asText()));
            }
            return channels;
        });
        return channels;
    }

    /**
     * Retrieves the list of members in the guild's widget.
     *
     * @return The list of guild widget members.
     */
    public List<GuildWidgetMember> getMembers() {
        members = loader.loadEntityList(members, "members", GuildWidgetMember::new);
        return members;
    }

    /**
     * Retrieves the count of online members in the guild's widget.
     *
     * @return The count of online members.
     */
    public int getOnlineMembersCount() {
        onlineMembersCount = loader.loadInt(onlineMembersCount, "presence_count");
        return onlineMembersCount;
    }

    public GuildWidgetImage getImage() {
        image = loader.load(image, () -> new GuildWidgetImage(Routes.Guild.Widget.Image(guild.getId())));
        return image;
    }
}
