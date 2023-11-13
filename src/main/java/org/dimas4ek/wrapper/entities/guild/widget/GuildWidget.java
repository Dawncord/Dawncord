package org.dimas4ek.wrapper.entities.guild.widget;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuildWidget {
    private final JSONObject widget;

    public GuildWidget(JSONObject widget) {
        this.widget = widget;
    }

    public String getGuildId() {
        return widget.getString("id");
    }

    public long getGuildIdLong() {
        return Long.parseLong(getGuildId());
    }

    public String getGuildName() {
        return widget.getString("name");
    }

    public String getInstantInvite() {
        return widget.optString("instant_invite", null);
    }

    public List<GuildChannel> getChannels() {
        List<GuildChannel> channels = new ArrayList<>();
        JSONArray channelsArray = widget.getJSONArray("channels");
        for (int i = 0; i < channelsArray.length(); i++) {
            String channelId = channelsArray.getJSONObject(i).getString("id");
            channels.add(new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId)));
        }
        return channels;
    }

    public List<GuildWidgetMember> getMembers() {
        return JsonUtils.getEntityList(widget.getJSONArray("members"), GuildWidgetMember::new);
    }

    public int getOnlineMembersCount() {
        return widget.getInt("presence_count");
    }
}
