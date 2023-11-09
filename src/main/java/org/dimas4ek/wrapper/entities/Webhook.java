package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.WebhookType;

public interface Webhook {
    String getId();

    long getIdLong();

    String getName();

    Guild getGuild();

    GuildChannel getChannel();

    User getUser();

    Avatar getAvatar();

    String getToken();

    String getApplicationId();

    WebhookType getType();
}
