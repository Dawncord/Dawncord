package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.action.WebhookModifyAction;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.WebhookType;

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

    void modify(Consumer<WebhookModifyAction> handler);

    void delete();
}
