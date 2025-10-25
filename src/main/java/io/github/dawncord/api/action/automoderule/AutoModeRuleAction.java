package io.github.dawncord.api.action.automoderule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.types.AutoModActionType;

/**
 * Abstract base class for AutoMod rule actions.
 */
public abstract class AutoModeRuleAction extends Action<AutoModeRuleAction> implements IAutoModeRuleAction {
    protected final String guildId;

    protected AutoModeRuleAction(String guildId) {
        super();
        this.guildId = guildId;
        this.jsonObject.set("actions", mapper.createArrayNode());
    }

    /**
     * Sets the name for the rule.
     *
     * @param name the name to set
     * @return the action object
     */
    public AutoModeRuleAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the timeout action for the rule.
     *
     * @param duration the duration in seconds
     * @return the action object
     */
    public AutoModeRuleAction setTimeoutAction(int duration) {
        return addAction(AutoModActionType.TIMEOUT,
                mapper.createObjectNode().put("duration_seconds", duration));
    }

    /**
     * Sets the alert message action for the rule.
     *
     * @param channelId the channel ID for alerts
     * @return the action object
     */
    public AutoModeRuleAction setAlertMessageAction(String channelId) {
        return addAction(AutoModActionType.SEND_ALERT_MESSAGE,
                mapper.createObjectNode().put("channel_id", channelId));
    }

    /**
     * Sets the block message action for the rule.
     *
     * @param message the custom message
     * @return the action object
     */
    public AutoModeRuleAction setBlockMessageAction(String message) {
        return addAction(AutoModActionType.BLOCK_MESSAGE,
                mapper.createObjectNode().put("custom_message", message));
    }

    /**
     * Sets the block message action for the rule.
     *
     * @return the action object
     */
    public AutoModeRuleAction setBlockMessageAction() {
        return addAction(AutoModActionType.BLOCK_MESSAGE, mapper.createObjectNode());
    }

    /**
     * Sets the enabled state for the rule.
     *
     * @param enabled the enabled state
     * @return the action object
     */
    public AutoModeRuleAction setEnabled(boolean enabled) {
        return setProperty("enabled", enabled);
    }

    /**
     * Sets the exempt roles for the rule.
     *
     * @param exemptRoles the exempt role IDs
     * @return the action object
     */
    public AutoModeRuleAction setExemptRoles(String... exemptRoles) {
        return setProperty("exempt_roles", exemptRoles);
    }

    /**
     * Sets the exempt channels for the rule.
     *
     * @param exemptChannels the exempt channel IDs
     * @return the action object
     */
    public AutoModeRuleAction setExemptChannels(String... exemptChannels) {
        return setProperty("exempt_channels", exemptChannels);
    }

    private AutoModeRuleAction addAction(AutoModActionType actionType, ObjectNode metadata) {
        ArrayNode actionsArray = getOrCreateActionsArray();
        actionsArray.add(
                mapper.createObjectNode()
                        .put("type", actionType.getValue())
                        .set("metadata", metadata)
        );
        return setProperty("actions", actionsArray);
    }

    private ArrayNode getOrCreateActionsArray() {
        if (!jsonObject.has("actions")) {
            jsonObject.set("actions", mapper.createArrayNode());
        }
        return (ArrayNode) jsonObject.get("actions");
    }

    /**
     * Submits the action to the API.
     */
    protected abstract void submit();
}