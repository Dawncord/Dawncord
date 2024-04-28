package org.dimas4ek.dawncord.entities.channel;

import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.dawncord.types.StagePrivacyLevel;

public interface Stage extends ISnowflake {
    Guild getGuild();

    GuildChannel getChannel();

    String getTopic();

    StagePrivacyLevel getPrivacyLevel();

    boolean isDiscoverable();

    GuildScheduledEvent getGuildEvent();

    void delete();

    void modify(String topic);
}
