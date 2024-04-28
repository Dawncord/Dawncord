package org.dimas4ek.dawncord.entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.WebhookModifyAction;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.image.Avatar;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.WebhookType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.EnumUtils;

import java.util.function.Consumer;

public class WebhookImpl implements Webhook {
    private final JsonNode webhook;
    private final Guild guild;
    private String id;
    private String name;
    private GuildChannel channel;
    private User user;
    private Avatar avatar;
    private String token;
    private String applicationId;
    private WebhookType type;

    public WebhookImpl(JsonNode webhook, Guild guild) {
        this.webhook = webhook;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = webhook.get("id").asText();
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
            name = webhook.get("name").asText();
        }
        return name;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public GuildChannel getChannel() {
        if (channel == null) {
            channel = guild.getChannelById(webhook.get("channel_id").asText());
        }
        return channel;
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = new UserImpl(webhook.get("user"));
        }
        return user;
    }

    @Override
    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = webhook.has("avatar") && webhook.hasNonNull("avatar")
                    ? new Avatar(getId(), webhook.get("avatar").asText())
                    : null;
        }
        return avatar;
    }

    @Override
    public String getToken() {
        if (token == null) {
            token = webhook.has("token") ? webhook.get("token").asText() : null;
        }
        return token;
    }

    @Override
    public String getApplicationId() {
        if (applicationId == null) {
            applicationId = webhook.has("application_id") ? webhook.get("application_id").asText() : null;
        }
        return applicationId;
    }

    @Override
    public WebhookType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(webhook, "type", WebhookType.class);
        }
        return type;
    }

    @Override
    public ModifyEvent<Webhook> modify(Consumer<WebhookModifyAction> handler) {
        ActionExecutor.modifyWebhook(handler, getId());
        return new ModifyEvent<>(guild.getWebhookById(getId()));
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Webhook.ById(getId()));
    }
}
