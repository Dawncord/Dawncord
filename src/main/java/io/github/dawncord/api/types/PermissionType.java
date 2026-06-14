package io.github.dawncord.api.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Represents Discord permission types.
 * <p>Each constant carries a bitwise flag value and optional channel-type applicability
 * ({@link Scope}). Permissions with no scopes apply at the guild level only.</p>
 * <p>Use {@link #getScopes()}, {@link #appliesTo(ChannelType)}, or the convenience methods
 * {@link #isText()}, {@link #isVoice()}, and {@link #isStage()} to query applicability.</p>
 */
public enum PermissionType {
    /**
     * Allows creation of instant invites.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    CREATE_INSTANT_INVITE(1L << 0, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows kicking members.
     * <p>Applies at the guild level.</p>
     */
    KICK_MEMBERS(1L << 1),

    /**
     * Allows banning members.
     * <p>Applies at the guild level.</p>
     */
    BAN_MEMBERS(1L << 2),

    /**
     * Grants all permissions and bypasses channel permission overwrites.
     * <p>Applies at the guild level.</p>
     */
    ADMINISTRATOR(1L << 3),

    /**
     * Allows management and editing of channels.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    MANAGE_CHANNELS(1L << 4, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows management of the guild.
     * <p>Applies at the guild level.</p>
     */
    MANAGE_GUILD(1L << 5),

    /**
     * Allows adding reactions to messages.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    ADD_REACTIONS(1L << 6, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows viewing of audit logs.
     * <p>Applies at the guild level.</p>
     */
    VIEW_AUDIT_LOG(1L << 7),

    /**
     * Allows using priority speaker in a voice channel.
     * <p>Applies to: Voice channels.</p>
     */
    PRIORITY_SPEAKER(1L << 8, Scope.VOICE),

    /**
     * Allows streaming in a voice channel.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    STREAM(1L << 9, Scope.VOICE, Scope.STAGE),

    /**
     * Allows viewing of a channel.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    VIEW_CHANNEL(1L << 10, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows sending messages.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    SEND_MESSAGES(1L << 11, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows sending text-to-speech messages.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    SEND_TTS_MESSAGES(1L << 12, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows management and deletion of messages.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    MANAGE_MESSAGES(1L << 13, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows embedding links.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    EMBED_LINKS(1L << 14, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows attaching files.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    ATTACH_FILES(1L << 15, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows reading of message history.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    READ_MESSAGE_HISTORY(1L << 16, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows mentioning @everyone, @here, and all roles.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    MENTION_EVERYONE(1L << 17, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows usage of external emojis.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    USE_EXTERNAL_EMOJIS(1L << 18, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows viewing of guild insights.
     * <p>Applies at the guild level.</p>
     */
    VIEW_GUILD_INSIGHTS(1L << 19),

    /**
     * Allows connecting to a voice channel.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    CONNECT(1L << 20, Scope.VOICE, Scope.STAGE),

    /**
     * Allows speaking in a voice channel.
     * <p>Applies to: Voice channels.</p>
     */
    SPEAK(1L << 21, Scope.VOICE),

    /**
     * Allows muting members in a voice channel.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    MUTE_MEMBERS(1L << 22, Scope.VOICE, Scope.STAGE),

    /**
     * Allows deafening members in a voice channel.
     * <p>Applies to: Voice channels.</p>
     */
    DEAFEN_MEMBERS(1L << 23, Scope.VOICE),

    /**
     * Allows moving members between voice channels.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    MOVE_MEMBERS(1L << 24, Scope.VOICE, Scope.STAGE),

    /**
     * Allows using voice activity detection.
     * <p>Applies to: Voice channels.</p>
     */
    USE_VAD(1L << 25, Scope.VOICE),

    /**
     * Allows changing one's own nickname.
     * <p>Applies at the guild level.</p>
     */
    CHANGE_NICKNAME(1L << 26),

    /**
     * Allows managing nicknames of other members.
     * <p>Applies at the guild level.</p>
     */
    MANAGE_NICKNAMES(1L << 27),

    /**
     * Allows managing roles of other members.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    MANAGE_ROLES(1L << 28, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows managing webhooks.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    MANAGE_WEBHOOKS(1L << 29, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows managing guild expressions (emojis, stickers, and soundboard sounds).
     * <p>Applies at the guild level.</p>
     */
    MANAGE_GUILD_EXPRESSIONS(1L << 30),

    /**
     * Allows usage of application commands.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    USE_APPLICATION_COMMANDS(1L << 31, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows requesting to speak in stage channels.
     * <p>Applies to: Stage channels.</p>
     */
    REQUEST_TO_SPEAK(1L << 32, Scope.STAGE),

    /**
     * Allows managing events.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    MANAGE_EVENTS(1L << 33, Scope.VOICE, Scope.STAGE),

    /**
     * Allows managing threads.
     * <p>Applies to: Text channels.</p>
     */
    MANAGE_THREADS(1L << 34, Scope.TEXT),

    /**
     * Allows creating public threads.
     * <p>Applies to: Text channels.</p>
     */
    CREATE_PUBLIC_THREADS(1L << 35, Scope.TEXT),

    /**
     * Allows creating private threads.
     * <p>Applies to: Text channels.</p>
     */
    CREATE_PRIVATE_THREADS(1L << 36, Scope.TEXT),

    /**
     * Allows using external stickers.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    USE_EXTERNAL_STICKERS(1L << 37, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows sending messages in threads.
     * <p>Applies to: Text channels.</p>
     */
    SEND_MESSAGES_IN_THREADS(1L << 38, Scope.TEXT),

    /**
     * Allows using embedded activities.
     * <p>Applies to: Text, Voice channels.</p>
     */
    USE_EMBEDDED_ACTIVITIES(1L << 39, Scope.TEXT, Scope.VOICE),

    /**
     * Allows moderating members (timeouts).
     * <p>Applies at the guild level.</p>
     */
    MODERATE_MEMBERS(1L << 40),

    /**
     * Allows viewing creator monetization analytics.
     * <p>Applies at the guild level.</p>
     */
    VIEW_CREATOR_MONETIZATION_ANALYTICS(1L << 41),

    /**
     * Allows using soundboard functionality.
     * <p>Applies to: Voice channels.</p>
     */
    USE_SOUNDBOARD(1L << 42, Scope.VOICE),

    /**
     * Allows creating emojis, stickers, and soundboard sounds, and editing and deleting
     * those created by the current user.
     * <p>Applies at the guild level.</p>
     */
    CREATE_GUILD_EXPRESSIONS(1L << 43),

    /**
     * Allows creating guild events and editing and deleting those created by the current user.
     * <p>Applies to: Voice, Stage channels.</p>
     */
    CREATE_EVENTS(1L << 44, Scope.VOICE, Scope.STAGE),

    /**
     * Allows the user to use external sounds from other servers.
     * <p>Applies to: Voice channels.</p>
     */
    USE_EXTERNAL_SOUNDS(1L << 45, Scope.VOICE),

    /**
     * Allows sending voice messages.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    SEND_VOICE_MESSAGES(1L << 46, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows setting voice channel status.
     * <p>Applies to: Voice channels.</p>
     */
    SET_VOICE_CHANNEL_STATUS(1L << 48, Scope.VOICE),

    /**
     * Allows sending polls.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    SEND_POLLS(1L << 49, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows user-installed apps to send public responses.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    USE_EXTERNAL_APPS(1L << 50, Scope.TEXT, Scope.VOICE, Scope.STAGE),

    /**
     * Allows pinning and unpinning messages.
     * <p>Applies to: Text channels.</p>
     */
    PIN_MESSAGES(1L << 51, Scope.TEXT),

    /**
     * Allows bypassing slowmode restrictions.
     * <p>Applies to: Text, Voice, Stage channels.</p>
     */
    BYPASS_SLOWMODE(1L << 52, Scope.TEXT, Scope.VOICE, Scope.STAGE);

    private final long value;
    private final Set<Scope> scopes;

    PermissionType(long value, Scope... scopes) {
        this.value = value;
        this.scopes = scopes.length == 0
                ? EnumSet.noneOf(Scope.class)
                : EnumSet.copyOf(List.of(scopes));
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

    /**
     * Gets the numeric value of the permission.
     *
     * @return The numeric value of the permission.
     */
    public long getValue() {
        return value;
    }

    /**
     * Returns an unmodifiable set of channel-type scopes this permission applies to.
     * An empty set indicates a guild-level permission with no specific channel-type restriction.
     *
     * @return An unmodifiable {@link Set} of {@link Scope} values.
     */
    public Set<Scope> getScopes() {
        return Collections.unmodifiableSet(scopes);
    }

    /**
     * Returns {@code true} if this permission is applicable in the given channel type.
     * <p>The mapping from {@link ChannelType} to {@link Scope} is:</p>
     * <ul>
     *   <li>{@link Scope#TEXT} — {@code GUILD_TEXT}, {@code GUILD_ANNOUNCEMENT},
     *       {@code GUILD_FORUM}, {@code GUILD_MEDIA}, {@code ANNOUNCEMENT_THREAD},
     *       {@code PUBLIC_THREAD}, {@code PRIVATE_THREAD}</li>
     *   <li>{@link Scope#VOICE} — {@code GUILD_VOICE}</li>
     *   <li>{@link Scope#STAGE} — {@code GUILD_STAGE_VOICE}</li>
     * </ul>
     * <p>All other channel types (e.g. {@code DM}, {@code GUILD_CATEGORY}) return {@code false}.</p>
     *
     * @param channelType The channel type to check.
     * @return {@code true} if this permission's scopes include the scope for the given channel type.
     */
    public boolean appliesTo(ChannelType channelType) {
        return switch (channelType) {
            case GUILD_TEXT, GUILD_ANNOUNCEMENT, GUILD_FORUM, GUILD_MEDIA,
                 ANNOUNCEMENT_THREAD, PUBLIC_THREAD, PRIVATE_THREAD -> scopes.contains(Scope.TEXT);
            case GUILD_VOICE -> scopes.contains(Scope.VOICE);
            case GUILD_STAGE_VOICE -> scopes.contains(Scope.STAGE);
            default -> false;
        };
    }

    /**
     * Returns {@code true} if this permission applies to text-based channels
     * (guild text, announcement, forum, media, and threads).
     *
     * @return {@code true} if the permission has {@link Scope#TEXT} applicability.
     */
    public boolean isText() {
        return scopes.contains(Scope.TEXT);
    }

    /**
     * Returns {@code true} if this permission applies to voice channels.
     *
     * @return {@code true} if the permission has {@link Scope#VOICE} applicability.
     */
    public boolean isVoice() {
        return scopes.contains(Scope.VOICE);
    }

    /**
     * Returns {@code true} if this permission applies to stage voice channels.
     *
     * @return {@code true} if the permission has {@link Scope#STAGE} applicability.
     */
    public boolean isStage() {
        return scopes.contains(Scope.STAGE);
    }

    /**
     * Represents the channel-type context in which a permission applies,
     * corresponding to the "Channel Type" column in the Discord permissions table.
     */
    public enum Scope {
        /**
         * Text-based channels: guild text, announcement, forum, media, and all thread types.
         */
        TEXT,

        /**
         * Voice channels ({@code GUILD_VOICE}).
         */
        VOICE,

        /**
         * Stage voice channels ({@code GUILD_STAGE_VOICE}).
         */
        STAGE
    }
}
