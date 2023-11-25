package org.dimas4ek.wrapper.entities.channel;

import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.types.StagePrivacyLevel;

public interface Stage extends ISnowflake {
    Guild getGuild();

    GuildChannel getChannel();

    String getTopic();

    StagePrivacyLevel getPrivacyLevel();

    boolean isDiscoverable();

    GuildEvent getEvent();

    void delete();

    void modify(String topic);
}
