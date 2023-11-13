package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.channel.GuildCategory;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.Invite;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeScreen;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidget;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidgetSettings;
import org.dimas4ek.wrapper.entities.image.DiscoverySplash;
import org.dimas4ek.wrapper.entities.image.Splash;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.types.GuildFeature;
import org.dimas4ek.wrapper.types.MfaLevel;
import org.dimas4ek.wrapper.types.VoiceRegion;

import java.util.List;

public interface Guild {
    String getId();

    long getIdLong();

    String getName();

    String getDescription();

    User getOwner();

    Splash getSplash();

    DiscoverySplash getDiscoverySplash();

    List<GuildFeature> getFeatures();

    List<Sticker> getStickers();

    Sticker getStickerById(String stickerId);

    Sticker getStickerById(long stickerId);

    List<Sticker> getStickersByName(String stickerName);

    List<Sticker> getStickersByAuthorId(String userId);

    List<Sticker> getStickersByAuthorId(long userId);

    List<GuildChannel> getChannels();

    GuildChannel getChannelById(String channelId);

    GuildChannel getChannelById(long channelId);

    List<GuildChannel> getChannelsByName(String channelName);

    ChannelPositionModifyAction modifyChannelPosition(String channelId);

    ChannelPositionModifyAction modifyChannelPosition(long channelId);

    List<GuildMember> getMembers();

    GuildMember getMemberById(String memberId);

    GuildMember getMemberById(long memberId);

    List<GuildMember> searchMembers(String username, int limit);

    List<GuildMember> searchMembers(String username);

    //todo add guild member

    GuildMemberModifyAction modifyMember(String memberId);

    GuildMemberModifyAction modifyMember(long memberId);

    CurrentMemberModifyAction modifyMe();

    void kickMember(String memberId);

    void kickMember(long memberId);

    void banMember(String memberId);

    void banMember(long memberId);

    void unbanMember(String memberId);

    void unbanMember(long memberId);

    List<GuildBan> getBans();

    GuildBan getBanByUserId(String userId);

    GuildBan getBanByUserId(long userId);

    List<GuildRole> getRoles();

    GuildRole getRoleById(String roleId);

    GuildRole getRoleById(long roleId);

    List<GuildRole> getRolesByName(String roleName);

    GuildRole getPublicRole();

    GuildRoleCreateAction createRole();

    GuildRoleModifyAction modifyRole(String roleId);

    GuildRoleModifyAction modifyRole(long roleId);

    void deleteRole(String roleId);

    void deleteRole(long roleId);

    List<GuildCategory> getCategories();

    GuildCategory getCategoryById(String categoryId);

    GuildCategory getCategoryById(long categoryId);

    List<GuildCategory> getCategoriesByName(String categoryName);

    GuildModifyAction modify();

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

    AuditLog getAuditLog();

    List<Emoji> getEmojis();

    Emoji getEmojiById(String emojiId);

    Emoji getEmojiById(long emojiId);

    List<Emoji> getEmojisByName(String emojiName);

    List<Emoji> getEmojisByCreatorId(String userId);

    List<Emoji> getEmojisByCreatorId(long userId);

    EmojiCreateAction createEmoji();

    EmojiModifyAction modifyEmoji(String emojiId);

    EmojiModifyAction modifyEmoji(long emojiId);

    void deleteEmoji(String emojiId);

    void deleteEmoji(long emojiId);

    List<AutoModRule> getAutoModRules();

    AutoModRule getAutoModRuleById(String ruleId);

    AutoModRule getAutoModRuleById(long ruleId);

    List<AutoModRule> getAutoModRuleByName(String ruleName);

    List<AutoModRule> getAutoModRuleByCreatorId(String userId);

    List<AutoModRule> getAutoModRuleByCreatorId(long userId);

    AutoModRuleCreateAction createAutoModRule();

    AutoModRuleModifyAction modifyAutoModRule(String ruleId);

    AutoModRuleModifyAction modifyAutoModRule(long ruleId);

    void deleteAutoModRule(String ruleId);

    void deleteAutoModRule(long ruleId);

    GuildPreview getPreview();

    void setMfaLevel(MfaLevel mfaLevel);

    List<VoiceRegion> getVoiceRegions();

    VoiceRegion getOptimalVoiceRegion();

    List<Invite> getInvites();

    List<Integration> getIntegrations();

    Integration getIntegrationById(String integrationId);

    Integration getIntegrationById(long integrationId);

    void deleteIntegration(String integrationId);

    void deleteIntegration(long integrationId);

    GuildWidget getWidget();

    GuildWidgetSettings getWidgetSettings();

    GuildWidgetSettingsModifyAction modifyWidgetSettings();

    GuildWelcomeScreen getWelcomeScreen();

    GuildWelcomeScreenModifyAction modifyWelcomeScreen();

    GuildOnboarding getOnboarding();

    GuildOnboardingModifyAction modifyOnboarding();
}
