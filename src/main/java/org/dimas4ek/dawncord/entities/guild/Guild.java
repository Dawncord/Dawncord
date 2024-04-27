package org.dimas4ek.dawncord.entities.guild;

import org.dimas4ek.dawncord.action.*;
import org.dimas4ek.dawncord.entities.CustomEmoji;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.Webhook;
import org.dimas4ek.dawncord.entities.channel.GuildCategory;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.channel.Invite;
import org.dimas4ek.dawncord.entities.channel.Stage;
import org.dimas4ek.dawncord.entities.channel.thread.Thread;
import org.dimas4ek.dawncord.entities.guild.audit.AuditLog;
import org.dimas4ek.dawncord.entities.guild.automod.AutoModRule;
import org.dimas4ek.dawncord.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.dawncord.entities.guild.integration.Integration;
import org.dimas4ek.dawncord.entities.guild.role.GuildRole;
import org.dimas4ek.dawncord.entities.guild.role.GuildRoleImpl;
import org.dimas4ek.dawncord.entities.guild.welcomescreen.GuildWelcomeScreen;
import org.dimas4ek.dawncord.entities.guild.widget.GuildWidget;
import org.dimas4ek.dawncord.entities.guild.widget.GuildWidgetSettings;
import org.dimas4ek.dawncord.entities.image.DiscoverySplash;
import org.dimas4ek.dawncord.entities.image.GuildIcon;
import org.dimas4ek.dawncord.entities.image.Splash;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;
import org.dimas4ek.dawncord.event.CreateEvent;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.MfaLevel;
import org.dimas4ek.dawncord.types.VoiceRegion;

import java.util.List;
import java.util.function.Consumer;

public interface Guild extends ISnowflake {
    String getName();

    String getDescription();

    User getOwner();

    GuildIcon getIcon();

    Splash getSplash();

    DiscoverySplash getDiscoverySplash();

    //List<GuildFeature> getFeatures(); todo fix missing features

    List<String> getFeatures();

    List<Sticker> getStickers();

    Sticker getStickerById(String stickerId);

    Sticker getStickerById(long stickerId);

    List<Sticker> getStickersByName(String stickerName);

    List<Sticker> getStickersByAuthorId(String userId);

    List<Sticker> getStickersByAuthorId(long userId);

    CreateEvent<Sticker> createSticker(Consumer<GuildStickerCreateAction> handler);

    ModifyEvent<Sticker> modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler);

    List<GuildChannel> getChannels();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);

    List<GuildChannel> getChannelsByName(String channelName);

    CreateEvent<GuildChannel> createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler);

    ModifyEvent<GuildChannel> modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler);

    ModifyEvent<GuildChannel> modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler);

    ModifyEvent<GuildChannel> modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler);

    ModifyEvent<GuildChannel> modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler);

    void deleteChannelById(String channelId);

    void deleteChannelById(long channelId);

    Stage getStageByChannelId(String channelId);

    Stage getStageByChannelId(long channelId);

    CreateEvent<Stage> createStage(Consumer<StageCreateAction> handler);

    ModifyEvent<Stage> modifyStageByChannelId(String channelId, String topic);

    void deleteStage(String channelId);

    List<GuildMember> getMembers();

    GuildMember getMemberById(String memberId);

    GuildMember getMemberById(long memberId);

    List<GuildMember> searchMembers(String username, int limit);

    List<GuildMember> searchMembers(String username);

    ModifyEvent<GuildMember> modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler);

    ModifyEvent<GuildMember> modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler);

    ModifyEvent<GuildMember> modifyMe(Consumer<CurrentMemberModifyAction> handler);

    void kickMember(String memberId);

    void kickMember(long memberId);

    void banMember(String memberId);

    void banMember(long memberId);

    void unbanMember(String memberId);

    void unbanMember(long memberId);

    void addMember(String userId);

    void addMember(long userId);

    List<GuildBan> getBans();

    GuildBan getBanByUserId(String userId);

    GuildBan getBanByUserId(long userId);

    List<GuildRoleImpl> getRoles();

    GuildRoleImpl getRoleById(String roleId);

    GuildRoleImpl getRoleById(long roleId);

    List<GuildRoleImpl> getRolesByName(String roleName);

    GuildRoleImpl getPublicRole();

    CreateEvent<GuildRole> createRole(Consumer<GuildRoleCreateAction> handler);

    ModifyEvent<GuildRole> modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler);

    ModifyEvent<GuildRole> modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler);

    void deleteRole(String roleId);

    void deleteRole(long roleId);

    List<GuildCategory> getCategories();

    GuildCategory getCategoryById(String categoryId);

    GuildCategory getCategoryById(long categoryId);

    List<GuildCategory> getCategoriesByName(String categoryName);

    ModifyEvent<Guild> modify(Consumer<GuildModifyAction> handler);

    void delete();

    boolean hasActiveThreads();

    int getActiveThreadsCount();

    List<Thread> getActiveThreads();

    List<Thread> getPublicArchiveThreads(String channelId);

    List<Thread> getPublicArchiveThreads(long channelId);

    List<Thread> getPrivateArchiveThreads(String channelId);

    List<Thread> getPrivateArchiveThreads(long channelId);

    List<Thread> getJoinedPrivateArchiveThreads(String channelId);

    List<Thread> getJoinedPrivateArchiveThreads(long channelId);

    Thread getThreadById(String threadId);

    Thread getThreadById(long threadId);

    List<Thread> getActiveThreadsByName(String threadName);

    List<GuildScheduledEvent> getGuildEvents();

    GuildScheduledEvent getGuildEventById(String eventId);

    GuildScheduledEvent getGuildEventById(long eventId);

    List<GuildScheduledEvent> getGuildEventsByName(String eventName);

    ModifyEvent<GuildScheduledEvent> modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler);

    ModifyEvent<GuildScheduledEvent> modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler);

    AuditLog getAuditLog();

    List<CustomEmoji> getEmojis();

    CustomEmoji getEmojiById(String emojiId);

    CustomEmoji getEmojiById(long emojiId);

    List<CustomEmoji> getEmojisByName(String emojiName);

    List<CustomEmoji> getEmojisByCreatorId(String userId);

    List<CustomEmoji> getEmojisByCreatorId(long userId);

    CreateEvent<CustomEmoji> createEmoji(Consumer<EmojiCreateAction> handler);

    ModifyEvent<CustomEmoji> modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler);

    ModifyEvent<CustomEmoji> modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler);

    void deleteEmoji(String emojiId);

    void deleteEmoji(long emojiId);

    List<AutoModRule> getAutoModRules();

    AutoModRule getAutoModRuleById(String ruleId);

    AutoModRule getAutoModRuleById(long ruleId);

    List<AutoModRule> getAutoModRuleByName(String ruleName);

    List<AutoModRule> getAutoModRuleByCreatorId(String userId);

    List<AutoModRule> getAutoModRuleByCreatorId(long userId);

    CreateEvent<AutoModRule> createAutoModRule(Consumer<AutoModRuleCreateAction> handler);

    ModifyEvent<AutoModRule> modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler);

    ModifyEvent<AutoModRule> modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler);

    void deleteAutoModRule(String ruleId);

    void deleteAutoModRule(long ruleId);

    GuildPreview getPreview();

    void setMfaLevel(MfaLevel mfaLevel);

    List<VoiceRegion> getVoiceRegions();

    VoiceRegion getOptimalVoiceRegion();

    List<Invite> getInvites();

    Invite getInvite(String code);

    List<Integration> getIntegrations();

    Integration getIntegrationById(String integrationId);

    Integration getIntegrationById(long integrationId);

    void deleteIntegration(String integrationId);

    void deleteIntegration(long integrationId);

    GuildWidget getWidget();

    GuildWidgetSettings getWidgetSettings();

    ModifyEvent<GuildWidgetSettings> modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler);

    GuildWelcomeScreen getWelcomeScreen();

    ModifyEvent<GuildWelcomeScreen> modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler);

    GuildOnboarding getOnboarding();

    ModifyEvent<GuildOnboarding> modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler);

    List<Webhook> getWebhooks();

    Webhook getWebhookById(String webhookId);

    Webhook getWebhookById(long webhookId);

    Webhook getWebhookByName(String webhookName);
}
