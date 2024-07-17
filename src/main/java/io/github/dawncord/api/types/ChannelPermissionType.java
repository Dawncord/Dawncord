package io.github.dawncord.api.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents permission types.
 */
public enum ChannelPermissionType {
    /**
     * Allows creation of instant invites.
     */
    CREATE_INSTANT_INVITE(1L << 0, "TVS"),

    /**
     * Allows management and editing of channels.
     */
    MANAGE_CHANNELS(1L << 4, "TVS"),

    /**
     * Allows adding reactions to messages.
     */
    ADD_REACTIONS(1L << 6, "TVS"),

    /**
     * Allows using priority speaker in a voice channel.
     */
    PRIORITY_SPEAKER(1L << 8, "V"),

    /**
     * Allows streaming in a voice channel.
     */
    STREAM(1L << 9, "VS"),

    /**
     * Allows viewing of a channel.
     */
    VIEW_CHANNEL(1L << 10, "TVS"),

    /**
     * Allows sending messages.
     */
    SEND_MESSAGES(1L << 11, "TVS"),

    /**
     * Allows sending text-to-speech messages.
     */
    SEND_TTS_MESSAGES(1L << 12, "TVS"),

    /**
     * Allows management and deletion of messages.
     */
    MANAGE_MESSAGES(1L << 13, "TVS"),

    /**
     * Allows embedding links.
     */
    EMBED_LINKS(1L << 14, "TVS"),

    /**
     * Allows attaching files.
     */
    ATTACH_FILES(1L << 15, "TVS"),

    /**
     * Allows reading of message history.
     */
    READ_MESSAGE_HISTORY(1L << 16, "TVS"),

    /**
     * Allows mentioning @everyone, @here, and all roles.
     */
    MENTION_EVERYONE(1L << 17, "TVS"),

    /**
     * Allows usage of external emojis.
     */
    USE_EXTERNAL_EMOJIS(1L << 18, "TVS"),

    /**
     * Allows connecting to a voice channel.
     */
    CONNECT(1L << 20, "VS"),

    /**
     * Allows speaking in a voice channel.
     */
    SPEAK(1L << 21, "V"),

    /**
     * Allows muting members in a voice channel.
     */
    MUTE_MEMBERS(1L << 22, "VS"),

    /**
     * Allows deafening members in a voice channel.
     */
    DEAFEN_MEMBERS(1L << 23, "V"),

    /**
     * Allows moving members between voice channels.
     */
    MOVE_MEMBERS(1L << 24, "VS"),

    /**
     * Allows using voice activity detection.
     */
    USE_VAD(1L << 25, "V"),

    /**
     * Allows managing roles of other members.
     */
    MANAGE_ROLES(1L << 28, "TVS"),

    /**
     * Allows managing webhooks.
     */
    MANAGE_WEBHOOKS(1L << 29, "TVS"),

    /**
     * Allows usage of application commands.
     */
    USE_APPLICATION_COMMANDS(1L << 31, "TVS"),

    /**
     * Allows requesting to speak in stage channels.
     */
    REQUEST_TO_SPEAK(1L << 32, "S"),

    /**
     * Allows managing events.
     */
    MANAGE_EVENTS(1L << 33, "VS"),

    /**
     * Allows managing threads.
     */
    MANAGE_THREADS(1L << 34, "T"),

    /**
     * Allows creating public threads.
     */
    CREATE_PUBLIC_THREADS(1L << 35, "T"),

    /**
     * Allows creating private threads.
     */
    CREATE_PRIVATE_THREADS(1L << 36, "T"),

    /**
     * Allows using external stickers.
     */
    USE_EXTERNAL_STICKERS(1L << 37, "TVS"),

    /**
     * Allows sending messages in threads.
     */
    SEND_MESSAGES_IN_THREADS(1L << 38, "T"),

    /**
     * Allows using embedded activities.
     */
    USE_EMBEDDED_ACTIVITIES(1L << 39, "V"),

    /**
     * Allows using soundboard functionality.
     */
    USE_SOUNDBOARD(1L << 42, "V"),

    /**
     * Allows creating events.
     */
    CREATE_EVENTS(1L << 44, "VS"),

    /**
     * Allows the user to use external sounds.
     */
    USE_EXTERNAL_SOUNDS(1L << 45, "V"),

    /**
     * Allows sending voice messages.
     */
    SEND_VOICE_MESSAGES(1L << 46, "TVS"),

    /**
     * Allows sending polls.
     */
    SEND_POLLS(1L << 49, "TVS"),

    /**
     * Allows using external apps.
     */
    USE_EXTERNAL_APPS(1L << 50, "TVS"),

    /**
     * No permissions.
     */
    EMPTY(0, null);

    private final long value;
    private final String types;

    ChannelPermissionType(long value, String types) {
        this.value = value;
        this.types = types;
    }

    /**
     * Gets the numeric value of the permission.
     *
     * @return The numeric value of the permission.
     */
    public long getValue() {
        return value;
    }

    public String getTypes() {
        return types;
    }

    /**
     * Retrieves all permission names.
     *
     * @param permissions The list of permissions.
     * @return A list containing the names of all permissions.
     */
    public static List<String> getAll(ChannelPermissionType... permissions) {
        List<String> perms = new ArrayList<>();
        for (ChannelPermissionType perm : ChannelPermissionType.values()) {
            perms.add(perm.name());
        }
        return perms;
    }
}
