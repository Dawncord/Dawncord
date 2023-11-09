package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditLogEvent {
    GUILD_UPDATE(1, "Server settings were updated", "Guild"),
    CHANNEL_CREATE(10, "Channel was created", "Channel"),
    CHANNEL_UPDATE(11, "Channel settings were updated", "Channel"),
    CHANNEL_DELETE(12, "Channel was deleted", "Channel"),
    CHANNEL_OVERWRITE_CREATE(13, "Permission overwrite was added to a channel", "Channel Overwrite"),
    CHANNEL_OVERWRITE_UPDATE(14, "Permission overwrite was updated for a channel", "Channel Overwrite"),
    CHANNEL_OVERWRITE_DELETE(15, "Permission overwrite was deleted from a channel", "Channel Overwrite"),
    MEMBER_KICK(20, "Member was removed from server", ""),
    MEMBER_PRUNE(21, "Members were pruned from server", ""),
    MEMBER_BAN_ADD(22, "Member was banned from server", ""),
    MEMBER_BAN_REMOVE(23, "Server ban was lifted for a member", ""),
    MEMBER_UPDATE(24, "Member was updated in server", "Member"),
    MEMBER_ROLE_UPDATE(25, "Member was added or removed from a role", "Partial Role"),
    MEMBER_MOVE(26, "Member was moved to a different voice channel", ""),
    MEMBER_DISCONNECT(27, "Member was disconnected from a voice channel", ""),
    BOT_ADD(28, "Bot user was added to server", ""),
    ROLE_CREATE(30, "Role was created", "Role"),
    ROLE_UPDATE(31, "Role was edited", "Role"),
    ROLE_DELETE(32, "Role was deleted", "Role"),
    INVITE_CREATE(40, "Server invite was created", "Invite and Invite Metadata"),
    INVITE_UPDATE(41, "Server invite was updated", "Invite and Invite Metadata"),
    INVITE_DELETE(42, "Server invite was deleted", "Invite and Invite Metadata"),
    WEBHOOK_CREATE(50, "Webhook was created", "Webhook"),
    WEBHOOK_UPDATE(51, "Webhook properties or channel were updated", "Webhook"),
    WEBHOOK_DELETE(52, "Webhook was deleted", "Webhook"),
    EMOJI_CREATE(60, "Emoji was created", "Emoji"),
    EMOJI_UPDATE(61, "Emoji name was updated", "Emoji"),
    EMOJI_DELETE(62, "Emoji was deleted", "Emoji"),
    MESSAGE_DELETE(72, "Single message was deleted", ""),
    MESSAGE_BULK_DELETE(73, "Multiple messages were deleted", ""),
    MESSAGE_PIN(74, "Message was pinned to a channel", ""),
    MESSAGE_UNPIN(75, "Message was unpinned from a channel", ""),
    INTEGRATION_CREATE(80, "App was added to server", "Integration"),
    INTEGRATION_UPDATE(81, "App was updated (as an example, its scopes were updated)", "Integration"),
    INTEGRATION_DELETE(82, "App was removed from server", "Integration"),
    STAGE_INSTANCE_CREATE(83, "Stage instance was created (stage channel becomes live)", "Stage Instance"),
    STAGE_INSTANCE_UPDATE(84, "Stage instance details were updated", "Stage Instance"),
    STAGE_INSTANCE_DELETE(85, "Stage instance was deleted (stage channel no longer live)", "Stage Instance"),
    STICKER_CREATE(90, "Sticker was created", "Sticker"),
    STICKER_UPDATE(91, "Sticker details were updated", "Sticker"),
    STICKER_DELETE(92, "Sticker was deleted", "Sticker"),
    GUILD_SCHEDULED_EVENT_CREATE(100, "Event was created", "Guild Scheduled Event"),
    GUILD_SCHEDULED_EVENT_UPDATE(101, "Event was updated", "Guild Scheduled Event"),
    GUILD_SCHEDULED_EVENT_DELETE(102, "Event was canceled", "Guild Scheduled Event"),
    THREAD_CREATE(110, "Thread was created in a channel", "Thread"),
    THREAD_UPDATE(111, "Thread was updated", "Thread"),
    THREAD_DELETE(112, "Thread was deleted", "Thread"),
    APPLICATION_COMMAND_PERMISSION_UPDATE(121, "Permissions were updated for a command", "Command Permission"),
    AUTO_MODERATION_RULE_CREATE(140, "Auto Moderation rule was created", "Auto Moderation Rule"),
    AUTO_MODERATION_RULE_UPDATE(141, "Auto Moderation rule was updated", "Auto Moderation Rule"),
    AUTO_MODERATION_RULE_DELETE(142, "Auto Moderation rule was deleted", "Auto Moderation Rule"),
    AUTO_MODERATION_BLOCK_MESSAGE(143, "Message was blocked by Auto Moderation", ""),
    AUTO_MODERATION_FLAG_TO_CHANNEL(144, "Message was flagged by Auto Moderation", ""),
    AUTO_MODERATION_USER_COMMUNICATION_DISABLED(145, "Member was timed out by Auto Moderation", ""),
    CREATOR_MONETIZATION_REQUEST_CREATED(150, "Creator monetization request was created", ""),
    CREATOR_MONETIZATION_TERMS_ACCEPTED(151, "Creator monetization terms were accepted", "");

    private final int value;
    private String description;
    private String objectChanged;

    AuditLogEvent(int value) {
        this.value = value;
    }
}
