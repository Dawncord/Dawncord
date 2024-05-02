package io.github.dawncord.api.types;

/**
 * Represents features that a guild may have.
 */
public enum GuildFeature {
    /**
     * Guild has access to set an animated guild banner image.
     */
    ANIMATED_BANNER(),

    /**
     * Guild has access to set an animated guild icon.
     */
    ANIMATED_ICON(),

    /**
     * Guild is using the old permissions configuration behavior for application commands.
     */
    APPLICATION_COMMAND_PERMISSIONS_V2(),

    /**
     * Guild has set up auto moderation rules.
     */
    AUTO_MODERATION(),

    /**
     * Guild has access to set a guild banner image.
     */
    BANNER(),

    /**
     * Guild can enable various community features and receives community updates.
     */
    COMMUNITY(),

    /**
     * Guild has enabled monetization.
     */
    CREATOR_MONETIZABLE_PROVISIONAL(),

    /**
     * Guild has enabled the role subscription promo page.
     */
    CREATOR_STORE_PAGE(),

    /**
     * Guild has been set as a support server on the App Directory.
     */
    DEVELOPER_SUPPORT_SERVER(),

    /**
     * Guild is able to be discovered in the directory.
     */
    DISCOVERABLE(),

    /**
     * Guild is able to be featured in the directory.
     */
    FEATURABLE(),

    /**
     * Guild has paused invites, preventing new users from joining.
     */
    INVITES_DISABLED(),

    /**
     * Guild has access to set an invite splash background.
     */
    INVITE_SPLASH(),

    /**
     * Guild has enabled Membership Screening.
     */
    MEMBER_VERIFICATION_GATE_ENABLED(),

    /**
     * Guild has increased custom sticker slots.
     */
    MORE_STICKERS(),

    /**
     * Guild has access to create announcement channels.
     */
    NEWS(),

    /**
     * Guild is partnered.
     */
    PARTNERED(),

    /**
     * Guild can be previewed before joining via Membership Screening or the directory.
     */
    PREVIEW_ENABLED(),

    /**
     * Guild has disabled alerts for join raids in the configured safety alerts channel.
     */
    RAID_ALERTS_DISABLED(),

    /**
     * Guild is able to set role icons.
     */
    ROLE_ICONS(),

    /**
     * Guild has role subscriptions that can be purchased.
     */
    ROLE_SUBSCRIPTIONS_AVAILABLE_FOR_PURCHASE(),

    /**
     * Guild has enabled role subscriptions.
     */
    ROLE_SUBSCRIPTIONS_ENABLED(),

    /**
     * Guild has enabled ticketed events.
     */
    TICKETED_EVENTS_ENABLED(),

    /**
     * Guild has access to set a vanity URL.
     */
    VANITY_URL(),

    /**
     * Guild is verified.
     */
    VERIFIED(),

    /**
     * Guild has access to set 384kbps bitrate in voice (previously VIP voice servers).
     */
    VIP_REGIONS(),

    /**
     * Guild has enabled the welcome screen.
     */
    WELCOME_SCREEN_ENABLED()
}

