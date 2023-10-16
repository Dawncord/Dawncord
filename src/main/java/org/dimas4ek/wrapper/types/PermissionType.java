package org.dimas4ek.wrapper.types;

public enum PermissionType {
    CREATE_INSTANT_INVITE(0x1L),
    KICK_MEMBERS(0x2L),
    BAN_MEMBERS(0x4L),
    ADMINISTRATOR(0x8L),
    MANAGE_CHANNELS(0x10L),
    MANAGE_GUILD(0x20L),
    ADD_REACTIONS(0x40L),
    VIEW_AUDIT_LOG(0x80L),
    PRIORITY_SPEAKER(0x100L),
    STREAM(0x200L),
    VIEW_CHANNEL(0x400L),
    SEND_MESSAGES(0x800L),
    SEND_TTS_MESSAGES(0x1000L),
    MANAGE_MESSAGES(0x2000L),
    EMBED_LINKS(0x4000L),
    ATTACH_FILES(0x8000L),
    READ_MESSAGE_HISTORY(0x10000L),
    MENTION_EVERYONE(0x20000L),
    USE_EXTERNAL_EMOJIS(0x40000L),
    VIEW_GUILD_INSIGHTS(0x80000L),
    CONNECT(0x100000L),
    SPEAK(0x200000L),
    MUTE_MEMBERS(0x400000L),
    DEAFEN_MEMBERS(0x800000L),
    MOVE_MEMBERS(0x1000000L),
    USE_VAD(0x2000000L),
    CHANGE_NICKNAME(0x4000000L),
    MANAGE_NICKNAMES(0x8000000L),
    MANAGE_ROLES(0x10000000L),
    MANAGE_WEBHOOKS(0x20000000L),
    MANAGE_GUILD_EXPRESSIONS(0x40000000L),
    USE_APPLICATION_COMMANDS(0x80000000L),
    REQUEST_TO_SPEAK(0x100000000L),
    MANAGE_EVENTS(0x200000000L),
    MANAGE_THREADS(0x400000000L),
    CREATE_PUBLIC_THREADS(0x800000000L),
    CREATE_PRIVATE_THREADS(0x1000000000L),
    USE_EXTERNAL_STICKERS(0x2000000000L),
    SEND_MESSAGES_IN_THREADS(0x4000000000L),
    USE_EMBEDDED_ACTIVITIES(0x8000000000L),
    MODERATE_MEMBERS(0x10000000000L),
    VIEW_CREATOR_MONETIZATION_ANALYTICS(0x20000000000L),
    USE_SOUNDBOARD(0x40000000000L),
    USE_EXTERNAL_SOUNDS(0x200000000000L),
    SEND_VOICE_MESSAGES(0x400000000000L);

    private final long value;

    PermissionType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}