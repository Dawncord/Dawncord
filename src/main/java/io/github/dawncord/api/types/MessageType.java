package io.github.dawncord.api.types;

/**
 * Represents message types.
 */
public enum MessageType {
    /**
     * Default message type.
     */
    DEFAULT(0),

    /**
     * Recipient add.
     */
    RECIPIENT_ADD(1),

    /**
     * Recipient remove.
     */
    RECIPIENT_REMOVE(2),

    /**
     * Call.
     */
    CALL(3),

    /**
     * Channel name change.
     */
    CHANNEL_NAME_CHANGE(4),

    /**
     * Channel icon change.
     */
    CHANNEL_ICON_CHANGE(5),

    /**
     * Channel pinned.
     */
    CHANNEL_PINNED_MESSAGE(6),

    /**
     * User join.
     */
    USER_JOIN(7),

    /**
     * Guild boost.
     */
    GUILD_BOOST(8),

    /**
     * Guild boost tier 1.
     */
    GUILD_BOOST_TIER_1(9),

    /**
     * Guild boost tier 2.
     */
    GUILD_BOOST_TIER_2(10),

    /**
     * Guild boost tier 3.
     */
    GUILD_BOOST_TIER_3(11),

    /**
     * Channel follow add.
     */
    CHANNEL_FOLLOW_ADD(12),

    /**
     * Guild discovery disqualified.
     */
    GUILD_DISCOVERY_DISQUALIFIED(14),

    /**
     * Guild discovery requalified.
     */
    GUILD_DISCOVERY_REQUALIFIED(15),

    /**
     * Guild discovery grace period initial warning.
     */
    GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING(16),

    /**
     * Guild discovery grace period final warning.
     */
    GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING(17),

    /**
     * Thread created.
     */
    THREAD_CREATED(18),

    /**
     * Reply.
     */
    REPLY(19),

    /**
     * Chat input command.
     */
    CHAT_INPUT_COMMAND(20),

    /**
     * Thread starter message.
     */
    THREAD_STARTER_MESSAGE(21),

    /**
     * Guild invite reminder.
     */
    GUILD_INVITE_REMINDER(22),

    /**
     * Context menu command.
     */
    CONTEXT_MENU_COMMAND(23),

    /**
     * Auto moderation action.
     */
    AUTO_MODERATION_ACTION(24),

    /**
     * Role subscription purchase.
     */
    ROLE_SUBSCRIPTION_PURCHASE(25),

    /**
     * Interaction premium upsell.
     */
    INTERACTION_PREMIUM_UPSELL(26),

    /**
     * Stage start.
     */
    STAGE_START(27),

    /**
     * Stage end.
     */
    STAGE_END(28),

    /**
     * Stage speaker.
     */
    STAGE_SPEAKER(29),

    /**
     * Stage topic.
     */
    STAGE_TOPIC(31),

    /**
     * Guild application premium subscription.
     */
    GUILD_APPLICATION_PREMIUM_SUBSCRIPTION(32);

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the message type.
     *
     * @return The value of the message type.
     */
    public int getValue() {
        return value;
    }
}
