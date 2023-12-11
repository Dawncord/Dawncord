package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.Webhook;
import org.dimas4ek.wrapper.entities.channel.GuildCategory;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.Invite;
import org.dimas4ek.wrapper.entities.channel.Stage;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.role.GuildRoleImpl;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeScreen;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidget;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidgetSettings;
import org.dimas4ek.wrapper.entities.image.DiscoverySplash;
import org.dimas4ek.wrapper.entities.image.GuildIcon;
import org.dimas4ek.wrapper.entities.image.Splash;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.MfaLevel;
import org.dimas4ek.wrapper.types.VoiceRegion;

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

    void createSticker(Consumer<GuildStickerCreateAction> handler);

    void modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler);

    List<GuildChannel> getChannels();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);

    List<GuildChannel> getChannelsByName(String channelName);

    void createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler);

    void modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler);

    void modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler);

    void modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler);

    void modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler);

    void deleteChannelById(String channelId);

    void deleteChannelById(long channelId);

    Stage getStageByChannelId(String channelId);

    Stage getStageByChannelId(long channelId);

    void createStage(Consumer<StageCreateAction> handler);

    void modifyStageByChannelId(String channelId, String topic);

    void deleteStage(String channelId);

    List<GuildMember> getMembers();

    GuildMember getMemberById(String memberId);

    GuildMember getMemberById(long memberId);

    List<GuildMember> searchMembers(String username, int limit);

    List<GuildMember> searchMembers(String username);

    void modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler);

    void modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler);

    void modifyMe(Consumer<CurrentMemberModifyAction> handler);

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

    void createRole(Consumer<GuildRoleCreateAction> handler);

    void modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler);

    void modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler);

    void deleteRole(String roleId);

    void deleteRole(long roleId);

    List<GuildCategory> getCategories();

    GuildCategory getCategoryById(String categoryId);

    GuildCategory getCategoryById(long categoryId);

    List<GuildCategory> getCategoriesByName(String categoryName);

    void modify(Consumer<GuildModifyAction> handler);

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

    List<GuildEvent> getGuildEvents();

    GuildEvent getGuildEventById(String eventId);

    GuildEvent getGuildEventById(long eventId);

    List<GuildEvent> getGuildEventsByName(String eventName);

    void modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler);

    void modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler);

    AuditLog getAuditLog();

    List<Emoji> getEmojis();

    Emoji getEmojiById(String emojiId);

    Emoji getEmojiById(long emojiId);

    List<Emoji> getEmojisByName(String emojiName);

    List<Emoji> getEmojisByCreatorId(String userId);

    List<Emoji> getEmojisByCreatorId(long userId);

    void createEmoji(Consumer<EmojiCreateAction> handler);

    void modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler);

    void modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler);

    void deleteEmoji(String emojiId);

    void deleteEmoji(long emojiId);

    List<AutoModRule> getAutoModRules();

    AutoModRule getAutoModRuleById(String ruleId);

    AutoModRule getAutoModRuleById(long ruleId);

    List<AutoModRule> getAutoModRuleByName(String ruleName);

    List<AutoModRule> getAutoModRuleByCreatorId(String userId);

    List<AutoModRule> getAutoModRuleByCreatorId(long userId);

    void createAutoModRule(Consumer<AutoModRuleCreateAction> handler);

    void modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler);

    void modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler);

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

    void modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler);

    GuildWelcomeScreen getWelcomeScreen();

    void modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler);

    GuildOnboarding getOnboarding();

    void modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler);

    List<Webhook> getWebhooks();

    Webhook getWebhookById(String webhookId);

    Webhook getWebhookById(long webhookId);

    Webhook getWebhookByName(String webhookName);
}
