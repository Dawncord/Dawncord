package io.github.dawncord.api.types;

/**
 * Represents system channel flags.
 */
public enum SystemChannelFlag {
    /**
     * Suppress member join notifications.
     */
    SUPPRESS_JOIN_NOTIFICATIONS(1 << 0),
    /**
     * Suppress server boost notifications.
     */
    SUPPRESS_PREMIUM_SUBSCRIPTIONS(1 << 1),
    /**
     * Suppress server setup tips.
     */
    SUPPRESS_GUILD_REMINDER_NOTIFICATIONS(1 << 2),
    /**
     * Hide member join sticker reply buttons.
     */
    SUPPRESS_JOIN_NOTIFICATION_REPLIES(1 << 3),
    /**
     * Suppress role subscription purchase and renewal notifications.
     */
    SUPPRESS_ROLE_SUBSCRIPTION_PURCHASE_NOTIFICATIONS(1 << 4),
    /**
     * Hide role subscription sticker reply buttons.
     */
    SUPPRESS_ROLE_SUBSCRIPTION_PURCHASE_NOTIFICATION_REPLIES(1 << 5);

    private final int value;

    SystemChannelFlag(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the system channel flag.
     *
     * @return The value of the system channel flag.
     */
    public int getValue() {
        return value;
    }
}

