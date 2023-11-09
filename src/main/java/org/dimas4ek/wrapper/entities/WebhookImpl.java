package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.GuildImpl;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.WebhookType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONObject;

public class WebhookImpl implements Webhook {
    private final JSONObject webhook;

    public WebhookImpl(JSONObject webhook) {
        this.webhook = webhook;
    }

    @Override
    public String getId() {
        return webhook.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return webhook.getString("name");
    }

    @Override
    public Guild getGuild() {
        return new GuildImpl(JsonUtils.fetchEntity("/guilds/" + webhook.getString("guild_id")));
    }

    @Override
    public GuildChannel getChannel() {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + webhook.getString("channel_id")));
    }

    @Override
    public User getUser() {
        return new UserImpl(webhook.getJSONObject("user"));
    }

    @Override
    public Avatar getAvatar() {
        String avatar = webhook.optString("avatar");
        return avatar != null ? new Avatar(getId(), avatar) : null;
    }

    @Override
    public String getToken() {
        return webhook.optString("token", null);
    }

    @Override
    public String getApplicationId() {
        return webhook.optString("application_id", null);
    }

    @Override
    public WebhookType getType() {
        return EnumUtils.getEnumObject(webhook, "type", WebhookType.class);
    }
}
