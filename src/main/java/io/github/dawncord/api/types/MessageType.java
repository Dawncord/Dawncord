package io.github.dawncord.api.types;

/**
 * Represents message types in Discord.
 * Each constant carries the integer value used in the API and a flag indicating
 * whether the message can be deleted by users (subject to channel permissions).
 */
public enum MessageType {
    /**
     * Default message type.
     */
    DEFAULT(0, true),

    /**
     * Recipient was added to a group DM.
     */
    RECIPIENT_ADD(1, false),

    /**
     * Recipient was removed from a group DM.
     */
    RECIPIENT_REMOVE(2, false),

    /**
     * A call was started.
     */
    CALL(3, false),

    /**
     * Channel name was changed.
     */
    CHANNEL_NAME_CHANGE(4, false),

    /**
     * Channel icon was changed.
     */
    CHANNEL_ICON_CHANGE(5, false),

    /**
     * A message was pinned in the channel.
     */
    CHANNEL_PINNED_MESSAGE(6, true),

    /**
     * A user joined the guild.
     */
    USER_JOIN(7, true),

    /**
     * A user boosted the guild.
     */
    GUILD_BOOST(8, true),

    /**
     * The guild reached boost tier 1.
     */
    GUILD_BOOST_TIER_1(9, true),

    /**
     * The guild reached boost tier 2.
     */
    GUILD_BOOST_TIER_2(10, true),

    /**
     * The guild reached boost tier 3.
     */
    GUILD_BOOST_TIER_3(11, true),

    /**
     * A channel was followed.
     */
    CHANNEL_FOLLOW_ADD(12, true),

    /**
     * The guild was disqualified from discovery.
     */
    GUILD_DISCOVERY_DISQUALIFIED(14, true),

    /**
     * The guild was requalified for discovery.
     */
    GUILD_DISCOVERY_REQUALIFIED(15, true),

    /**
     * Initial grace period warning for discovery disqualification.
     */
    GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING(16, true),

    /**
     * Final grace period warning for discovery disqualification.
     */
    GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING(17, true),

    /**
     * A thread was created in the channel.
     */
    THREAD_CREATED(18, true),

    /**
     * A reply to another message.
     */
    REPLY(19, true),

    /**
     * A slash command was invoked.
     */
    CHAT_INPUT_COMMAND(20, true),

    /**
     * The first message in a thread, pinned from the parent channel.
     */
    THREAD_STARTER_MESSAGE(21, false),

    /**
     * A guild invite reminder.
     */
    GUILD_INVITE_REMINDER(22, true),

    /**
     * A context menu command was invoked.
     */
    CONTEXT_MENU_COMMAND(23, true),

    /**
     * An Auto Moderation rule was triggered and an action was executed.
     * Can only be deleted by members with the {@code MANAGE_MESSAGES} permission.
     */
    AUTO_MODERATION_ACTION(24, true),

    /**
     * A role subscription was purchased.
     */
    ROLE_SUBSCRIPTION_PURCHASE(25, true),

    /**
     * An interaction premium upsell prompt.
     */
    INTERACTION_PREMIUM_UPSELL(26, true),

    /**
     * A stage channel became live.
     */
    STAGE_START(27, true),

    /**
     * A stage channel ended.
     */
    STAGE_END(28, true),

    /**
     * A user became a speaker in a stage channel.
     */
    STAGE_SPEAKER(29, true),

    /**
     * The topic of a stage channel was changed.
     */
    STAGE_TOPIC(31, true),

    /**
     * A user subscribed to a premium app.
     */
    GUILD_APPLICATION_PREMIUM_SUBSCRIPTION(32, true),

    /**
     * Guild incident alert mode was enabled.
     */
    GUILD_INCIDENT_ALERT_MODE_ENABLED(36, true),

    /**
     * Guild incident alert mode was disabled.
     */
    GUILD_INCIDENT_ALERT_MODE_DISABLED(37, true),

    /**
     * A raid was reported in the guild.
     */
    GUILD_INCIDENT_REPORT_RAID(38, true),

    /**
     * A false alarm raid report was submitted.
     */
    GUILD_INCIDENT_REPORT_FALSE_ALARM(39, true),

    /**
     * A purchase notification message.
     */
    PURCHASE_NOTIFICATION(44, true),

    /**
     * A poll result message.
     */
    POLL_RESULT(46, true);

    private final int value;
    private final boolean deletable;

    MessageType(int value, boolean deletable) {
        this.value = value;
        this.deletable = deletable;
    }

    /**
     * Gets the integer value of this message type as used in the Discord API.
     *
     * @return The integer value of the message type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns whether messages of this type can be deleted by users.
     * Note: some types (e.g. {@link #AUTO_MODERATION_ACTION}) require special
     * permissions ({@code MANAGE_MESSAGES}) even though this returns {@code true}.
     *
     * @return {@code true} if the message type is deletable, {@code false} otherwise.
     */
    public boolean isDeletable() {
        return deletable;
    }
}
