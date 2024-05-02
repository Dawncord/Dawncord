package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.WebhookModifyAction;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.WebhookType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.function.Consumer;

/**
 * Implementation class for the Webhook interface.
 */
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

    /**
     * Constructs a new WebhookImpl with the provided JSON node and guild.
     *
     * @param webhook The JSON node representing the webhook.
     * @param guild   The guild associated with the webhook.
     */
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
