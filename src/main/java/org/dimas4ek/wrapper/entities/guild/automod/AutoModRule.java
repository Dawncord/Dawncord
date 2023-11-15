package org.dimas4ek.wrapper.entities.guild.automod;

import org.dimas4ek.wrapper.action.AutoModRuleModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.AutoModEventType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;

import java.util.List;
import java.util.function.Consumer;

public interface AutoModRule {
    String getId();

    long getIdLong();

    String getName();

    Guild getGuild();

    User getCreator();

    AutoModEventType getEventType();

    AutoModTriggerType getTriggerType();

    AutoModTriggerMetadata getTriggerMetadata();

    List<AutoModAction> getActions();

    boolean isEnabled();

    List<String> getExemptRoles();

    List<String> getExemptChannels();

    void modify(Consumer<AutoModRuleModifyAction> handler);

    void delete();
}
