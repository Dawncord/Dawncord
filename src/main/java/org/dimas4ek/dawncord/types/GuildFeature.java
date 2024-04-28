package org.dimas4ek.dawncord.types;

public enum GuildFeature {
    ANIMATED_BANNER("guild has access to set an animated guild banner image"),
    ANIMATED_ICON("guild has access to set an animated guild icon"),
    APPLICATION_COMMAND_PERMISSIONS_V2("guild is using the old permissions configuration behavior"),
    AUTO_MODERATION("guild has set up auto moderation rules"),
    BANNER("guild has access to set a guild banner image"),
    COMMUNITY("guild can enable welcome screen, Membership Screening, stage channels and discovery, and receives community updates"),
    CREATOR_MONETIZABLE_PROVISIONAL("guild has enabled monetization"),
    CREATOR_STORE_PAGE("guild has enabled the role subscription promo page"),
    DEVELOPER_SUPPORT_SERVER("guild has been set as a support server on the App Directory"),
    DISCOVERABLE("guild is able to be discovered in the directory"),
    FEATURABLE("guild is able to be featured in the directory"),
    INVITES_DISABLED("guild has paused invites, preventing new users from joining"),
    INVITE_SPLASH("guild has access to set an invite splash background"),
    MEMBER_VERIFICATION_GATE_ENABLED("guild has enabled Membership Screening"),
    MORE_STICKERS("guild has increased custom sticker slots"),
    NEWS("guild has access to create announcement channels"),
    PARTNERED("guild is partnered"),
    PREVIEW_ENABLED("guild can be previewed before joining via Membership Screening or the directory"),
    RAID_ALERTS_DISABLED("guild has disabled alerts for join raids in the configured safety alerts channel"),
    ROLE_ICONS("guild is able to set role icons"),
    ROLE_SUBSCRIPTIONS_AVAILABLE_FOR_PURCHASE("guild has role subscriptions that can be purchased"),
    ROLE_SUBSCRIPTIONS_ENABLED("guild has enabled role subscriptions"),
    TICKETED_EVENTS_ENABLED("guild has enabled ticketed events"),
    VANITY_URL("guild has access to set a vanity URL"),
    VERIFIED("guild is verified"),
    VIP_REGIONS("guild has access to set 384kbps bitrate in voice (previously VIP voice servers)"),
    WELCOME_SCREEN_ENABLED("guild has enabled the welcome screen");

    private final String description;

    GuildFeature(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

