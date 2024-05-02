package io.github.dawncord.api.entities.guild.automod;

import io.github.dawncord.api.action.AutoModRuleModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.AutoModEventType;
import io.github.dawncord.api.types.AutoModTriggerType;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an auto-moderation rule.
 */
public interface AutoModRule extends ISnowflake {
    /**
     * Retrieves the name of the auto-moderation rule.
     *
     * @return The name of the auto-moderation rule.
     */
    String getName();

    /**
     * Retrieves the creator of the auto-moderation rule.
     *
     * @return The creator of the auto-moderation rule.
     */
    User getCreator();

    /**
     * Retrieves the event type associated with the auto-moderation rule.
     *
     * @return The event type associated with the auto-moderation rule.
     */
    AutoModEventType getEventType();

    /**
     * Retrieves the trigger type of the auto-moderation rule.
     *
     * @return The trigger type of the auto-moderation rule.
     */
    AutoModTriggerType getTriggerType();

    /**
     * Retrieves the trigger metadata of the auto-moderation rule.
     *
     * @return The trigger metadata of the auto-moderation rule.
     */
    AutoModTriggerMetadata getTriggerMetadata();

    /**
     * Retrieves the list of actions associated with the auto-moderation rule.
     *
     * @return The list of actions associated with the auto-moderation rule.
     */
    List<AutoModAction> getActions();

    /**
     * Checks if the auto-moderation rule is enabled.
     *
     * @return True if the auto-moderation rule is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Retrieves the list of exempt roles from the auto-moderation rule.
     *
     * @return The list of exempt roles from the auto-moderation rule.
     */
    List<String> getExemptRoles();

    /**
     * Retrieves the list of exempt channels from the auto-moderation rule.
     *
     * @return The list of exempt channels from the auto-moderation rule.
     */
    List<String> getExemptChannels();

    /**
     * Modifies the auto-moderation rule using the provided handler.
     *
     * @param handler The handler for modifying the auto-moderation rule.
     * @return A ModifyEvent representing the modification event.
     */
    ModifyEvent<AutoModRule> modify(Consumer<AutoModRuleModifyAction> handler);

    /**
     * Deletes the auto-moderation rule.
     */
    void delete();
}
