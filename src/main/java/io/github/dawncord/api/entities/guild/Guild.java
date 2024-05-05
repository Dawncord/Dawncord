package io.github.dawncord.api.entities.guild;

import io.github.dawncord.api.action.*;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.Webhook;
import io.github.dawncord.api.entities.channel.GuildCategory;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.Invite;
import io.github.dawncord.api.entities.channel.Stage;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.audit.AuditLog;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.guild.role.GuildRoleImpl;
import io.github.dawncord.api.entities.guild.welcomescreen.GuildWelcomeScreen;
import io.github.dawncord.api.entities.guild.widget.GuildWidget;
import io.github.dawncord.api.entities.guild.widget.GuildWidgetSettings;
import io.github.dawncord.api.entities.image.DiscoverySplash;
import io.github.dawncord.api.entities.image.GuildIcon;
import io.github.dawncord.api.entities.image.Splash;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.MfaLevel;
import io.github.dawncord.api.types.VerificationLevel;
import io.github.dawncord.api.types.VoiceRegion;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a guild (server) in Discord.
 */
public interface Guild extends ISnowflake {
    /**
     * Retrieves the name of the guild.
     *
     * @return The name of the guild.
     */
    String getName();

    /**
     * Retrieves the description of the guild.
     *
     * @return The description of the guild.
     */
    String getDescription();

    /**
     * Retrieves the owner of the guild.
     *
     * @return The owner of the guild.
     */
    User getOwner();

    /**
     * Retrieves the icon of the guild.
     *
     * @return The icon of the guild.
     */
    GuildIcon getIcon();

    /**
     * Retrieves the splash image of the guild.
     *
     * @return The splash image of the guild.
     */
    Splash getSplash();

    /**
     * Retrieves the discovery splash image of the guild.
     *
     * @return The discovery splash image of the guild.
     */
    DiscoverySplash getDiscoverySplash();

    //List<GuildFeature> getFeatures();

    /**
     * Retrieves the list of features enabled for the guild.
     *
     * @return A list of features enabled for the guild.
     */
    List<String> getFeatures();

    /**
     * Retrieves the verification level of the guild.
     *
     * @return The verification level of the guild.
     */
    VerificationLevel getVerificationLevel();

    /**
     * Retrieves the list of stickers available in the guild.
     *
     * @return A list of stickers available in the guild.
     */
    List<Sticker> getStickers();

    /**
     * Retrieves a sticker in the guild by its ID.
     *
     * @param stickerId The ID of the sticker.
     * @return The sticker with the specified ID.
     */
    Sticker getStickerById(String stickerId);

    /**
     * Retrieves a sticker in the guild by its ID.
     *
     * @param stickerId The ID of the sticker.
     * @return The sticker with the specified ID.
     */
    Sticker getStickerById(long stickerId);

    /**
     * Retrieves a list of stickers in the guild with the specified name.
     *
     * @param stickerName The name of the sticker.
     * @return A list of stickers with the specified name.
     */
    List<Sticker> getStickersByName(String stickerName);

    /**
     * Retrieves a list of stickers in the guild created by the user with the specified ID.
     *
     * @param userId The ID of the user who created the stickers.
     * @return A list of stickers created by the user with the specified ID.
     */
    List<Sticker> getStickersByAuthorId(String userId);

    /**
     * Retrieves a list of stickers in the guild created by the user with the specified ID.
     *
     * @param userId The ID of the user who created the stickers.
     * @return A list of stickers created by the user with the specified ID.
     */
    List<Sticker> getStickersByAuthorId(long userId);

    /**
     * Creates a new sticker in the guild.
     *
     * @param handler The handler to configure the sticker creation.
     * @return An event representing the creation of the sticker.
     */
    CreateEvent<Sticker> createSticker(Consumer<GuildStickerCreateAction> handler);

    /**
     * Modifies an existing sticker in the guild.
     *
     * @param stickerId The ID of the sticker to modify.
     * @param handler   The handler to configure the sticker modification.
     * @return An event representing the modification of the sticker.
     */
    ModifyEvent<Sticker> modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler);

    /**
     * Retrieves a list of channels in the guild.
     *
     * @return A list of channels in the guild.
     */
    List<GuildChannel> getChannels();

    /**
     * Retrieves a channel in the guild by its ID.
     *
     * @param channelId The ID of the channel.
     * @return The channel with the specified ID.
     */
    GuildChannel getChannelById(String channelId);

    /**
     * Retrieves a channel in the guild by its ID.
     *
     * @param channelId The ID of the channel.
     * @return The channel with the specified ID.
     */
    GuildChannel getChannelById(long channelId);

    /**
     * Retrieves a list of channels in the guild with the specified name.
     *
     * @param channelName The name of the channel.
     * @return A list of channels with the specified name.
     */
    List<GuildChannel> getChannelsByName(String channelName);

    /**
     * Creates a new channel in the guild.
     *
     * @param type    The type of the channel to create.
     * @param handler The handler to configure the channel creation.
     * @return An event representing the creation of the channel.
     */
    CreateEvent<GuildChannel> createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler);

    /**
     * Modifies an existing channel in the guild.
     *
     * @param channelId The ID of the channel to modify.
     * @param handler   The handler to configure the channel modification.
     * @return An event representing the modification of the channel.
     */
    ModifyEvent<GuildChannel> modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler);

    /**
     * Modifies an existing channel in the guild.
     *
     * @param channelId The ID of the channel to modify.
     * @param handler   The handler to configure the channel modification.
     * @return An event representing the modification of the channel.
     */
    ModifyEvent<GuildChannel> modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler);

    /**
     * Modifies the position of a channel in the guild.
     *
     * @param channelId The ID of the channel to modify the position of.
     * @param handler   The handler to configure the modification of the channel position.
     * @return An event representing the modification of the channel position.
     */
    ModifyEvent<GuildChannel> modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler);

    /**
     * Modifies the position of a channel in the guild.
     *
     * @param channelId The ID of the channel to modify the position of.
     * @param handler   The handler to configure the modification of the channel position.
     * @return An event representing the modification of the channel position.
     */
    ModifyEvent<GuildChannel> modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler);

    /**
     * Deletes a channel in the guild by its ID.
     *
     * @param channelId The ID of the channel to delete.
     */
    void deleteChannelById(String channelId);

    /**
     * Deletes a channel in the guild by its ID.
     *
     * @param channelId The ID of the channel to delete.
     */
    void deleteChannelById(long channelId);

    /**
     * Retrieves the stage associated with the specified channel ID.
     *
     * @param channelId The ID of the channel.
     * @return The stage associated with the specified channel ID.
     */
    Stage getStageByChannelId(String channelId);

    /**
     * Retrieves the stage associated with the specified channel ID.
     *
     * @param channelId The ID of the channel.
     * @return The stage associated with the specified channel ID.
     */
    Stage getStageByChannelId(long channelId);

    /**
     * Creates a new stage channel in the guild.
     *
     * @param handler The handler to configure the stage channel creation.
     * @return An event representing the creation of the stage channel.
     */
    CreateEvent<Stage> createStage(Consumer<StageCreateAction> handler);

    /**
     * Modifies the topic of the stage associated with the specified channel ID.
     *
     * @param channelId The ID of the channel.
     * @param topic     The new topic for the stage.
     * @return An event representing the modification of the stage.
     */
    ModifyEvent<Stage> modifyStageByChannelId(String channelId, String topic);

    /**
     * Deletes the stage associated with the specified channel ID.
     *
     * @param channelId The ID of the channel.
     */
    void deleteStage(String channelId);

    /**
     * Retrieves a list of members in the guild.
     *
     * @return A list of members in the guild.
     */
    List<GuildMember> getMembers();

    /**
     * Retrieves a member in the guild by their ID.
     *
     * @param memberId The ID of the member.
     * @return The member with the specified ID.
     */
    GuildMember getMemberById(String memberId);

    /**
     * Retrieves a member in the guild by their ID.
     *
     * @param memberId The ID of the member.
     * @return The member with the specified ID.
     */
    GuildMember getMemberById(long memberId);

    /**
     * Searches for members in the guild by their username, limiting the result count.
     *
     * @param username The username to search for.
     * @param limit    The maximum number of members to return.
     * @return A list of members matching the search criteria.
     */
    List<GuildMember> searchMembers(String username, int limit);

    /**
     * Searches for members in the guild by their username.
     *
     * @param username The username to search for.
     * @return A list of members matching the search criteria.
     */
    List<GuildMember> searchMembers(String username);

    /**
     * Modifies a member in the guild by their ID.
     *
     * @param memberId The ID of the member to modify.
     * @param handler  The handler to configure the member modification.
     * @return An event representing the modification of the member.
     */
    ModifyEvent<GuildMember> modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler);

    /**
     * Modifies a member in the guild by their ID.
     *
     * @param memberId The ID of the member to modify.
     * @param handler  The handler to configure the member modification.
     * @return An event representing the modification of the member.
     */
    ModifyEvent<GuildMember> modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler);

    /**
     * Modifies the current member's settings in the guild.
     *
     * @param handler The handler to configure the modification of the current member.
     * @return An event representing the modification of the current member.
     */
    ModifyEvent<GuildMember> modifyMe(Consumer<CurrentMemberModifyAction> handler);

    /**
     * Kicks a member from the guild by their ID.
     *
     * @param memberId The ID of the member to kick.
     */
    void kickMember(String memberId);

    /**
     * Kicks a member from the guild by their ID.
     *
     * @param memberId The ID of the member to kick.
     */
    void kickMember(long memberId);

    /**
     * Bans a member from the guild by their ID.
     *
     * @param memberId The ID of the member to ban.
     */
    void banMember(String memberId);

    /**
     * Bans a member from the guild by their ID.
     *
     * @param memberId The ID of the member to ban.
     */
    void banMember(long memberId);

    /**
     * Unbans a member from the guild by their ID.
     *
     * @param memberId The ID of the member to unban.
     */
    void unbanMember(String memberId);

    /**
     * Unbans a member from the guild by their ID.
     *
     * @param memberId The ID of the member to unban.
     */
    void unbanMember(long memberId);

    /**
     * Adds a member to the guild by their ID.
     *
     * @param accessToken The access token.
     * @param userId      The ID of the user to add as a member.
     * @see <a href="https://discord.com/developers/docs/topics/oauth2" target="_blank">Discord OAuth2 Documentation</a>
     */
    void addMember(String accessToken, String userId);

    /**
     * Adds a member to the guild by their ID.
     *
     * @param accessToken The access token.
     * @param userId      The ID of the user to add as a member.
     * @see <a href="https://discord.com/developers/docs/topics/oauth2" target="_blank">Discord OAuth2 Documentation</a>
     */
    void addMember(String accessToken, long userId);

    /**
     * Retrieves a list of banned members in the guild.
     *
     * @return A list of banned members in the guild.
     */
    List<GuildBan> getBans();

    /**
     * Retrieves information about the ban for the member with the specified user ID.
     *
     * @param userId The ID of the banned member.
     * @return Information about the ban for the member with the specified user ID.
     */
    GuildBan getBanByUserId(String userId);

    /**
     * Retrieves information about the ban for the member with the specified user ID.
     *
     * @param userId The ID of the banned member.
     * @return Information about the ban for the member with the specified user ID.
     */
    GuildBan getBanByUserId(long userId);

    /**
     * Retrieves a list of roles in the guild.
     *
     * @return A list of roles in the guild.
     */
    List<GuildRoleImpl> getRoles();

    /**
     * Retrieves information about the role with the specified ID.
     *
     * @param roleId The ID of the role.
     * @return Information about the role with the specified ID.
     */
    GuildRoleImpl getRoleById(String roleId);

    /**
     * Retrieves information about the role with the specified ID.
     *
     * @param roleId The ID of the role.
     * @return Information about the role with the specified ID.
     */
    GuildRoleImpl getRoleById(long roleId);

    /**
     * Retrieves a list of roles in the guild with the specified name.
     *
     * @param roleName The name of the role.
     * @return A list of roles in the guild with the specified name.
     */
    List<GuildRoleImpl> getRolesByName(String roleName);

    /**
     * Retrieves the public role in the guild.
     *
     * @return The public role in the guild.
     */
    GuildRoleImpl getPublicRole();

    /**
     * Creates a new role in the guild.
     *
     * @param handler The handler to configure the role creation.
     * @return An event representing the creation of the role.
     */
    CreateEvent<GuildRole> createRole(Consumer<GuildRoleCreateAction> handler);

    /**
     * Modifies the role with the specified ID.
     *
     * @param roleId  The ID of the role to modify.
     * @param handler The handler to configure the role modification.
     * @return An event representing the modification of the role.
     */
    ModifyEvent<GuildRole> modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler);

    /**
     * Modifies the role with the specified ID.
     *
     * @param roleId  The ID of the role to modify.
     * @param handler The handler to configure the role modification.
     * @return An event representing the modification of the role.
     */
    ModifyEvent<GuildRole> modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler);

    /**
     * Deletes the role with the specified ID.
     *
     * @param roleId The ID of the role to delete.
     */
    void deleteRole(String roleId);

    /**
     * Deletes the role with the specified ID.
     *
     * @param roleId The ID of the role to delete.
     */
    void deleteRole(long roleId);

    /**
     * Retrieves a list of categories in the guild.
     *
     * @return A list of categories in the guild.
     */
    List<GuildCategory> getCategories();

    /**
     * Retrieves information about the category with the specified ID.
     *
     * @param categoryId The ID of the category.
     * @return Information about the category with the specified ID.
     */
    GuildCategory getCategoryById(String categoryId);

    /**
     * Retrieves information about the category with the specified ID.
     *
     * @param categoryId The ID of the category.
     * @return Information about the category with the specified ID.
     */
    GuildCategory getCategoryById(long categoryId);

    /**
     * Retrieves a list of categories in the guild with the specified name.
     *
     * @param categoryName The name of the category.
     * @return A list of categories in the guild with the specified name.
     */
    List<GuildCategory> getCategoriesByName(String categoryName);

    /**
     * Modifies the guild.
     *
     * @param handler The handler to configure the guild modification.
     * @return An event representing the modification of the guild.
     */
    ModifyEvent<Guild> modify(Consumer<GuildModifyAction> handler);

    /**
     * Deletes the guild.
     */
    void delete();

    /**
     * Checks if the guild has active threads.
     *
     * @return True if the guild has active threads, otherwise false.
     */
    boolean hasActiveThreads();

    /**
     * Retrieves the count of active threads in the guild.
     *
     * @return The count of active threads in the guild.
     */
    int getActiveThreadsCount();

    /**
     * Retrieves a list of active threads in the guild.
     *
     * @return A list of active threads in the guild.
     */
    List<Thread> getActiveThreads();

    /**
     * Retrieves a list of public archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of public archive threads in the specified channel.
     */
    List<Thread> getPublicArchiveThreads(String channelId);

    /**
     * Retrieves a list of public archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of public archive threads in the specified channel.
     */
    List<Thread> getPublicArchiveThreads(long channelId);

    /**
     * Retrieves a list of private archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of private archive threads in the specified channel.
     */
    List<Thread> getPrivateArchiveThreads(String channelId);

    /**
     * Retrieves a list of private archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of private archive threads in the specified channel.
     */
    List<Thread> getPrivateArchiveThreads(long channelId);

    /**
     * Retrieves a list of joined private archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of joined private archive threads in the specified channel.
     */
    List<Thread> getJoinedPrivateArchiveThreads(String channelId);

    /**
     * Retrieves a list of joined private archive threads in the specified channel.
     *
     * @param channelId The ID of the channel.
     * @return A list of joined private archive threads in the specified channel.
     */
    List<Thread> getJoinedPrivateArchiveThreads(long channelId);

    /**
     * Retrieves a thread by its ID.
     *
     * @param threadId The ID of the thread.
     * @return The thread with the specified ID.
     */
    Thread getThreadById(String threadId);

    /**
     * Retrieves a thread by its ID.
     *
     * @param threadId The ID of the thread.
     * @return The thread with the specified ID.
     */
    Thread getThreadById(long threadId);

    /**
     * Retrieves a list of active threads in the guild with the specified name.
     *
     * @param threadName The name of the thread.
     * @return A list of active threads in the guild with the specified name.
     */
    List<Thread> getActiveThreadsByName(String threadName);

    /**
     * Retrieves a list of guild events in the guild.
     *
     * @return A list of guild events in the guild.
     */
    List<GuildScheduledEvent> getGuildEvents();

    /**
     * Retrieves information about the guild event with the specified ID.
     *
     * @param eventId The ID of the guild event.
     * @return Information about the guild event with the specified ID.
     */
    GuildScheduledEvent getGuildEventById(String eventId);

    /**
     * Retrieves information about the guild event with the specified ID.
     *
     * @param eventId The ID of the guild event.
     * @return Information about the guild event with the specified ID.
     */
    GuildScheduledEvent getGuildEventById(long eventId);

    /**
     * Retrieves a list of guild events in the guild with the specified name.
     *
     * @param eventName The name of the guild event.
     * @return A list of guild events in the guild with the specified name.
     */
    List<GuildScheduledEvent> getGuildEventsByName(String eventName);

    /**
     * Modifies the guild event with the specified ID.
     *
     * @param eventId The ID of the guild event to modify.
     * @param handler The handler to configure the guild event modification.
     * @return An event representing the modification of the guild event.
     */
    ModifyEvent<GuildScheduledEvent> modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler);

    /**
     * Modifies the guild event with the specified ID.
     *
     * @param eventId The ID of the guild event to modify.
     * @param handler The handler to configure the guild event modification.
     * @return An event representing the modification of the guild event.
     */
    ModifyEvent<GuildScheduledEvent> modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler);

    /**
     * Retrieves the audit log of the guild.
     *
     * @return The audit log of the guild.
     */
    AuditLog getAuditLog();

    /**
     * Retrieves the list of custom emojis in the guild.
     *
     * @return The list of custom emojis in the guild.
     */
    List<CustomEmoji> getEmojis();

    /**
     * Retrieves information about the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji.
     * @return Information about the custom emoji with the specified ID.
     */
    CustomEmoji getEmojiById(String emojiId);

    /**
     * Retrieves information about the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji.
     * @return Information about the custom emoji with the specified ID.
     */
    CustomEmoji getEmojiById(long emojiId);

    /**
     * Retrieves a list of custom emojis in the guild with the specified name.
     *
     * @param emojiName The name of the custom emoji.
     * @return A list of custom emojis in the guild with the specified name.
     */
    List<CustomEmoji> getEmojisByName(String emojiName);

    /**
     * Retrieves a list of custom emojis created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return A list of custom emojis created by the user with the specified ID.
     */
    List<CustomEmoji> getEmojisByCreatorId(String userId);

    /**
     * Retrieves a list of custom emojis created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return A list of custom emojis created by the user with the specified ID.
     */
    List<CustomEmoji> getEmojisByCreatorId(long userId);

    /**
     * Creates a new custom emoji in the guild.
     *
     * @param handler The handler to configure the creation of the custom emoji.
     * @return An event representing the creation of the custom emoji.
     */
    CreateEvent<CustomEmoji> createEmoji(Consumer<EmojiCreateAction> handler);

    /**
     * Modifies the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji to modify.
     * @param handler The handler to configure the modification of the custom emoji.
     * @return An event representing the modification of the custom emoji.
     */
    ModifyEvent<CustomEmoji> modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler);

    /**
     * Modifies the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji to modify.
     * @param handler The handler to configure the modification of the custom emoji.
     * @return An event representing the modification of the custom emoji.
     */
    ModifyEvent<CustomEmoji> modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler);

    /**
     * Deletes the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji to delete.
     */
    void deleteEmoji(String emojiId);

    /**
     * Deletes the custom emoji with the specified ID.
     *
     * @param emojiId The ID of the custom emoji to delete.
     */
    void deleteEmoji(long emojiId);

    /**
     * Retrieves the list of auto moderation rules in the guild.
     *
     * @return The list of auto moderation rules in the guild.
     */
    List<AutoModRule> getAutoModRules();

    /**
     * Retrieves information about the auto moderation rule with the specified ID.
     *
     * @param ruleId The ID of the auto moderation rule.
     * @return Information about the auto moderation rule with the specified ID.
     */
    AutoModRule getAutoModRuleById(String ruleId);

    /**
     * Retrieves information about the auto moderation rule with the specified ID.
     *
     * @param ruleId The ID of the auto moderation rule.
     * @return Information about the auto moderation rule with the specified ID.
     */
    AutoModRule getAutoModRuleById(long ruleId);

    /**
     * Retrieves a list of auto moderation rules in the guild with the specified name.
     *
     * @param ruleName The name of the auto moderation rule.
     * @return A list of auto moderation rules in the guild with the specified name.
     */
    List<AutoModRule> getAutoModRuleByName(String ruleName);

    /**
     * Retrieves a list of auto moderation rules created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return A list of auto moderation rules created by the user with the specified ID.
     */
    List<AutoModRule> getAutoModRuleByCreatorId(String userId);

    /**
     * Retrieves a list of auto moderation rules created by the user with the specified ID.
     *
     * @param userId The ID of the user.
     * @return A list of auto moderation rules created by the user with the specified ID.
     */
    List<AutoModRule> getAutoModRuleByCreatorId(long userId);

    /**
     * Creates a new auto moderation rule in the guild.
     *
     * @param handler The handler to configure the creation of the auto moderation rule.
     * @return An event representing the creation of the auto moderation rule.
     */
    CreateEvent<AutoModRule> createAutoModRule(Consumer<AutoModRuleCreateAction> handler);

    /**
     * Modifies the auto moderation rule with the specified ID.
     *
     * @param ruleId  The ID of the auto moderation rule to modify.
     * @param handler The handler to configure the modification of the auto moderation rule.
     * @return An event representing the modification of the auto moderation rule.
     */
    ModifyEvent<AutoModRule> modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler);

    /**
     * Modifies the auto moderation rule with the specified ID.
     *
     * @param ruleId  The ID of the auto moderation rule to modify.
     * @param handler The handler to configure the modification of the auto moderation rule.
     * @return An event representing the modification of the auto moderation rule.
     */
    ModifyEvent<AutoModRule> modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler);

    /**
     * Deletes the auto moderation rule with the specified ID.
     *
     * @param ruleId The ID of the auto moderation rule to delete.
     */
    void deleteAutoModRule(String ruleId);

    /**
     * Deletes the auto moderation rule with the specified ID.
     *
     * @param ruleId The ID of the auto moderation rule to delete.
     */
    void deleteAutoModRule(long ruleId);

    /**
     * Retrieves the preview information of the guild.
     *
     * @return The preview information of the guild.
     */
    GuildPreview getPreview();

    /**
     * Sets the MFA (Multi-Factor Authentication) level for the guild.
     *
     * @param mfaLevel The MFA level to set for the guild.
     */
    void setMfaLevel(MfaLevel mfaLevel);

    /**
     * Retrieves a list of voice regions available for the guild.
     *
     * @return A list of voice regions available for the guild.
     */
    List<VoiceRegion> getVoiceRegions();

    /**
     * Retrieves the optimal voice region for the guild.
     *
     * @return The optimal voice region for the guild.
     */
    VoiceRegion getOptimalVoiceRegion();

    /**
     * Retrieves a list of invites to the guild.
     *
     * @return A list of invites to the guild.
     */
    List<Invite> getInvites();

    /**
     * Retrieves the invite with the specified code.
     *
     * @param code The code of the invite.
     * @return The invite with the specified code.
     */
    Invite getInvite(String code);

    /**
     * Retrieves a list of integrations in the guild.
     *
     * @return A list of integrations in the guild.
     */
    List<Integration> getIntegrations();

    /**
     * Retrieves information about the integration with the specified ID.
     *
     * @param integrationId The ID of the integration.
     * @return Information about the integration with the specified ID.
     */
    Integration getIntegrationById(String integrationId);

    /**
     * Retrieves information about the integration with the specified ID.
     *
     * @param integrationId The ID of the integration.
     * @return Information about the integration with the specified ID.
     */
    Integration getIntegrationById(long integrationId);

    /**
     * Deletes the integration with the specified ID.
     *
     * @param integrationId The ID of the integration to delete.
     */
    void deleteIntegration(String integrationId);

    /**
     * Deletes the integration with the specified ID.
     *
     * @param integrationId The ID of the integration to delete.
     */
    void deleteIntegration(long integrationId);

    /**
     * Retrieves the widget settings for the guild.
     *
     * @return The widget settings for the guild.
     */
    GuildWidget getWidget();

    /**
     * Retrieves the settings for the guild's widget.
     *
     * @return The settings for the guild's widget.
     */
    GuildWidgetSettings getWidgetSettings();

    /**
     * Modifies the settings for the guild's widget.
     *
     * @param handler The handler to configure the modification of the widget settings.
     * @return An event representing the modification of the widget settings.
     */
    ModifyEvent<GuildWidgetSettings> modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler);

    /**
     * Retrieves the welcome screen for the guild.
     *
     * @return The welcome screen for the guild.
     */
    GuildWelcomeScreen getWelcomeScreen();

    /**
     * Modifies the welcome screen for the guild.
     *
     * @param handler The handler to configure the modification of the welcome screen.
     * @return An event representing the modification of the welcome screen.
     */
    ModifyEvent<GuildWelcomeScreen> modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler);

    /**
     * Retrieves the onboarding configuration for the guild.
     *
     * @return The onboarding configuration for the guild.
     */
    GuildOnboarding getOnboarding();

    /**
     * Modifies the onboarding configuration for the guild.
     *
     * @param handler The handler to configure the modification of the onboarding settings.
     * @return An event representing the modification of the onboarding settings.
     */
    ModifyEvent<GuildOnboarding> modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler);

    /**
     * Retrieves a list of webhooks associated with the guild.
     *
     * @return A list of webhooks associated with the guild.
     */
    List<Webhook> getWebhooks();

    /**
     * Retrieves the webhook with the specified ID.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook with the specified ID.
     */
    Webhook getWebhookById(String webhookId);

    /**
     * Retrieves the webhook with the specified ID.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook with the specified ID.
     */
    Webhook getWebhookById(long webhookId);

    /**
     * Retrieves the webhook with the specified name.
     *
     * @param webhookName The name of the webhook.
     * @return The webhook with the specified name.
     */
    Webhook getWebhookByName(String webhookName);
}
