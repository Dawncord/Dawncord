package io.github.dawncord.api.types;

/**
 * Represents features that a guild may have.
 * Each constant carries a flag indicating whether the feature can be toggled
 * via the Discord API (mutable guild feature).
 */
public enum GuildFeature {
    /**
     * Guild has access to set an animated guild banner image.
     */
    ANIMATED_BANNER(false),

    /**
     * Guild has access to set an animated guild icon.
     */
    ANIMATED_ICON(false),

    /**
     * Guild is using the old permissions configuration behavior for application commands.
     */
    APPLICATION_COMMAND_PERMISSIONS_V2(false),

    /**
     * Guild has set up auto moderation rules.
     */
    AUTO_MODERATION(false),

    /**
     * Guild has access to set a guild banner image.
     */
    BANNER(false),

    /**
     * Guild can enable various community features and receives community updates.
     */
    COMMUNITY(true),

    /**
     * Guild has enabled monetization.
     */
    CREATOR_MONETIZABLE_PROVISIONAL(false),

    /**
     * Guild has enabled the role subscription promo page.
     */
    CREATOR_STORE_PAGE(false),

    /**
     * Guild has been set as a support server on the App Directory.
     */
    DEVELOPER_SUPPORT_SERVER(false),

    /**
     * Guild is able to be discovered in the directory.
     */
    DISCOVERABLE(true),

    /**
     * Guild is able to be featured in the directory.
     */
    FEATURABLE(false),

    /**
     * Guild has paused invites, preventing new users from joining.
     */
    INVITES_DISABLED(true),

    /**
     * Guild has access to set an invite splash background.
     */
    INVITE_SPLASH(false),

    /**
     * Guild has enabled Membership Screening.
     */
    MEMBER_VERIFICATION_GATE_ENABLED(false),

    /**
     * Guild has increased custom sticker slots.
     */
    MORE_STICKERS(false),

    /**
     * Guild has access to create announcement channels.
     */
    NEWS(false),

    /**
     * Guild is partnered.
     */
    PARTNERED(false),

    /**
     * Guild can be previewed before joining via Membership Screening or the directory.
     */
    PREVIEW_ENABLED(false),

    /**
     * Guild has disabled alerts for join raids in the configured safety alerts channel.
     */
    RAID_ALERTS_DISABLED(true),

    /**
     * Guild is able to set role icons.
     */
    ROLE_ICONS(false),

    /**
     * Guild has role subscriptions that can be purchased.
     */
    ROLE_SUBSCRIPTIONS_AVAILABLE_FOR_PURCHASE(false),

    /**
     * Guild has enabled role subscriptions.
     */
    ROLE_SUBSCRIPTIONS_ENABLED(false),

    /**
     * Guild has enabled ticketed events.
     */
    TICKETED_EVENTS_ENABLED(false),

    /**
     * Guild has access to set a vanity URL.
     */
    VANITY_URL(false),

    /**
     * Guild is verified.
     */
    VERIFIED(false),

    /**
     * Guild has access to set 384kbps bitrate in voice (previously VIP voice servers).
     */
    VIP_REGIONS(false),

    /**
     * Guild has enabled the welcome screen.
     */
    WELCOME_SCREEN_ENABLED(false),

    /**
     * Guild has access to the soundboard feature.
     */
    SOUNDBOARD(false),

    /**
     * Guild has access to additional soundboard sounds.
     */
    MORE_SOUNDBOARD(false),

    /**
     * Guild has enabled guest access via voice channel invitations.
     */
    GUESTS_ENABLED(false),

    /**
     * Guild has access to guild tags.
     */
    GUILD_TAGS(false),

    /**
     * Guild has access to enhanced role colors.
     */
    ENHANCED_ROLE_COLORS(false);

    private final boolean mutable;

    GuildFeature(boolean mutable) {
        this.mutable = mutable;
    }

    /**
     * Returns whether this feature can be toggled via the Discord API.
     * Mutable features may require specific permissions (e.g. Administrator or Manage Guild).
     *
     * @return {@code true} if the feature can be set or unset via the API, {@code false} otherwise.
     */
    public boolean isMutable() {
        return mutable;
    }
}
