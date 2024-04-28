package org.dimas4ek.dawncord.entities;

import org.dimas4ek.dawncord.action.WebhookModifyAction;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.image.Avatar;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.WebhookType;

import java.util.function.Consumer;

public interface Webhook extends ISnowflake {
    String getName();

    Guild getGuild();

    GuildChannel getChannel();

    User getUser();

    Avatar getAvatar();

    String getToken();

    String getApplicationId();

    WebhookType getType();

    ModifyEvent<Webhook> modify(Consumer<WebhookModifyAction> handler);

    void delete();
}
