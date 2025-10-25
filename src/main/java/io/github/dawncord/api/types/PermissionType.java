package io.github.dawncord.api.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents permission types.
 */
public enum PermissionType {
    /**
     * Allows creation of instant invites.
     */
    CREATE_INSTANT_INVITE(1L << 0),

    /**
     * Allows kicking members.
     */
    KICK_MEMBERS(1L << 1),

    /**
     * Allows banning members.
     */
    BAN_MEMBERS(1L << 2),

    /**
     * Grants all permissions and bypasses channel permission overwrites.
     */
    ADMINISTRATOR(1L << 3),

    /**
     * Allows management and editing of channels.
     */
    MANAGE_CHANNELS(1L << 4),

    /**
     * Allows management of the guild.
     */
    MANAGE_GUILD(1L << 5),

    /**
     * Allows adding reactions to messages.
     */
    ADD_REACTIONS(1L << 6),

    /**
     * Allows viewing of audit logs.
     */
    VIEW_AUDIT_LOG(1L << 7),

    /**
     * Allows using priority speaker in a voice channel.
     */
    PRIORITY_SPEAKER(1L << 8),

    /**
     * Allows streaming in a voice channel.
     */
    STREAM(1L << 9),

    /**
     * Allows viewing of a channel.
     */
    VIEW_CHANNEL(1L << 10),

    /**
     * Allows sending messages.
     */
    SEND_MESSAGES(1L << 11),

    /**
     * Allows sending text-to-speech messages.
     */
    SEND_TTS_MESSAGES(1L << 12),

    /**
     * Allows management and deletion of messages.
     */
    MANAGE_MESSAGES(1L << 13),

    /**
     * Allows embedding links.
     */
    EMBED_LINKS(1L << 14),

    /**
     * Allows attaching files.
     */
    ATTACH_FILES(1L << 15),

    /**
     * Allows reading of message history.
     */
    READ_MESSAGE_HISTORY(1L << 16),

    /**
     * Allows mentioning @everyone, @here, and all roles.
     */
    MENTION_EVERYONE(1L << 17),

    /**
     * Allows usage of external emojis.
     */
    USE_EXTERNAL_EMOJIS(1L << 18),

    /**
     * Allows viewing of guild insights.
     */
    VIEW_GUILD_INSIGHTS(1L << 19),

    /**
     * Allows connecting to a voice channel.
     */
    CONNECT(1L << 20),

    /**
     * Allows speaking in a voice channel.
     */
    SPEAK(1L << 21),

    /**
     * Allows muting members in a voice channel.
     */
    MUTE_MEMBERS(1L << 22),

    /**
     * Allows deafening members in a voice channel.
     */
    DEAFEN_MEMBERS(1L << 23),

    /**
     * Allows moving members between voice channels.
     */
    MOVE_MEMBERS(1L << 24),

    /**
     * Allows using voice activity detection.
     */
    USE_VAD(1L << 25),

    /**
     * Allows changing one's own nickname.
     */
    CHANGE_NICKNAME(1L << 26),

    /**
     * Allows managing nicknames of other members.
     */
    MANAGE_NICKNAMES(1L << 27),

    /**
     * Allows managing roles of other members.
     */
    MANAGE_ROLES(1L << 28),

    /**
     * Allows managing webhooks.
     */
    MANAGE_WEBHOOKS(1L << 29),

    /**
     * Allows managing guild expressions.
     */
    MANAGE_GUILD_EXPRESSIONS(1L << 30),

    /**
     * Allows usage of application commands.
     */
    USE_APPLICATION_COMMANDS(1L << 31),

    /**
     * Allows requesting to speak in stage channels.
     */
    REQUEST_TO_SPEAK(1L << 32),

    /**
     * Allows managing events.
     */
    MANAGE_EVENTS(1L << 33),

    /**
     * Allows managing threads.
     */
    MANAGE_THREADS(1L << 34),

    /**
     * Allows creating public threads.
     */
    CREATE_PUBLIC_THREADS(1L << 35),

    /**
     * Allows creating private threads.
     */
    CREATE_PRIVATE_THREADS(1L << 36),

    /**
     * Allows using external stickers.
     */
    USE_EXTERNAL_STICKERS(1L << 37),

    /**
     * Allows sending messages in threads.
     */
    SEND_MESSAGES_IN_THREADS(1L << 38),

    /**
     * Allows using embedded activities.
     */
    USE_EMBEDDED_ACTIVITIES(1L << 39),

    /**
     * Allows moderating members.
     */
    MODERATE_MEMBERS(1L << 40),

    /**
     * Allows viewing creator monetization analytics.
     */
    VIEW_CREATOR_MONETIZATION_ANALYTICS(1L << 41),

    /**
     * Allows using soundboard functionality.
     */
    USE_SOUNDBOARD(1L << 42),
    /**
     * Allows the user to use external sounds.
     */
    USE_EXTERNAL_SOUNDS(1L << 45),

    /**
     * Allows sending voice messages.
     */
    SEND_VOICE_MESSAGES(1L << 46);

    private final long value;

    PermissionType(long value) {
        this.value = value;
    }

    /**
     * Gets the numeric value of the permission.
     *
     * @return The numeric value of the permission.
     */
    public long getValue() {
        return value;
    }

    /**
     * Retrieves all permission names.
     *
     * @param permissions The list of permissions.
     * @return A list containing the names of all permissions.
     */
    public static List<String> getAll(PermissionType... permissions) {
        List<String> perms = new ArrayList<>();
        for (PermissionType perm : PermissionType.values()) {
            perms.add(perm.name());
        }
        return perms;
    }
}
