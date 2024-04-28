package org.dimas4ek.dawncord.entities.guild.automod;

import org.dimas4ek.dawncord.action.AutoModRuleModifyAction;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.AutoModEventType;
import org.dimas4ek.dawncord.types.AutoModTriggerType;

import java.util.List;
import java.util.function.Consumer;

public interface AutoModRule extends ISnowflake {
    String getName();

    User getCreator();

    AutoModEventType getEventType();

    AutoModTriggerType getTriggerType();

    AutoModTriggerMetadata getTriggerMetadata();

    List<AutoModAction> getActions();

    boolean isEnabled();

    List<String> getExemptRoles();

    List<String> getExemptChannels();

    ModifyEvent<AutoModRule> modify(Consumer<AutoModRuleModifyAction> handler);

    void delete();
}
