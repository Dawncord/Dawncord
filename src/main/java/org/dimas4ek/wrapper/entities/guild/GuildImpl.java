package org.dimas4ek.wrapper.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.channel.*;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.channel.thread.ThreadImpl;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRuleImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEventImpl;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.integration.IntegrationImpl;
import org.dimas4ek.wrapper.entities.guild.role.GuildRoleImpl;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeScreen;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidget;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidgetSettings;
import org.dimas4ek.wrapper.entities.image.DiscoverySplash;
import org.dimas4ek.wrapper.entities.image.GuildIcon;
import org.dimas4ek.wrapper.entities.image.Splash;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.MfaLevel;
import org.dimas4ek.wrapper.types.VoiceRegion;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GuildImpl implements Guild {
    private final JsonNode guild;
    private String id;
    private String name;
    private String description;
    private User owner;
    private GuildIcon icon;
    private Splash splash;
    private DiscoverySplash discoverySplash;
    private List<String> features;
    private List<Sticker> stickers;
    private List<GuildChannel> channels;
    private List<GuildMember> members;
    private List<GuildBan> bans;
    private List<GuildRoleImpl> roles;
    private List<GuildCategory> categories;
    private List<Thread> threads;
    private List<GuildScheduledEvent> events;
    private AuditLog auditLog;
    private List<Emoji> emojis;
    private List<AutoModRule> autoModRules;
    private GuildPreview preview;
    private List<VoiceRegion> regions;
    private VoiceRegion optimalRegion;
    private List<Invite> invites;
    private List<Integration> integrations;
    private GuildWidget widget;
    private GuildWidgetSettings widgetSettings;
    private GuildWelcomeScreen welcomeScreen;
    private GuildOnboarding onboarding;
    private List<Webhook> webhooks;

    public GuildImpl(JsonNode guild) {
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = guild.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        if (name == null) {
            name = guild.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = guild.get("description").asText();
        }
        return description;
    }

    @Override
    public User getOwner() {
        if (owner == null) {
            owner = new UserImpl(JsonUtils.fetchEntity(Routes.User(guild.get("owner_id").asText())));
        }
        return owner;
    }

    @Override
    public GuildIcon getIcon() {
        if (icon == null) {
            icon = guild.hasNonNull("icon") ? new GuildIcon(getId(), guild.get("icon").asText()) : null;
        }
        return icon;
    }

    @Override
    public Splash getSplash() {
        if (splash == null) {
            splash = guild.hasNonNull("splash") ? new Splash(getId(), guild.get("splash").asText()) : null;
        }
        return splash;
    }

    @Override
    public DiscoverySplash getDiscoverySplash() {
        if (discoverySplash == null) {
            discoverySplash = guild.hasNonNull("discovery_splash") ? new DiscoverySplash(getId(), guild.get("discovery_splash").asText()) : null;
        }
        return discoverySplash;
    }

    @Override
    public List<String> getFeatures() {
        if (features == null) {
            features = new ArrayList<>();
            for (JsonNode feature : guild.get("features")) {
                features.add(feature.asText());
            }
        }
        return features;
    }

    @Override
    public List<Sticker> getStickers() {
        if (stickers == null) {
            stickers = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Sticker.All(getId())), sticker -> new StickerImpl(sticker, this));
        }
        return stickers;
    }

    @Override
    public Sticker getStickerById(String stickerId) {
        return new StickerImpl(JsonUtils.fetchEntity(Routes.Guild.Sticker.Get(getId(), stickerId)), this);
    }

    @Override
    public Sticker getStickerById(long stickerId) {
        return getStickerById(String.valueOf(stickerId));
    }

    @Override
    public List<Sticker> getStickersByName(String stickerName) {
        return getStickers().stream().filter(sticker -> sticker.getName().equals(stickerName)).toList();
    }

    @Override
    public List<Sticker> getStickersByAuthorId(String userId) {
        return getStickers().stream().filter(sticker -> sticker.getAuthor().getId().equals(userId)).toList();
    }

    @Override
    public List<Sticker> getStickersByAuthorId(long userId) {
        return getStickersByAuthorId(String.valueOf(userId));
    }

    @Override
    public void createSticker(Consumer<GuildStickerCreateAction> handler) {
        ActionExecutor.execute(handler, GuildStickerCreateAction.class, getId());
    }

    @Override
    public void modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler) {
        ActionExecutor.modifyGuildSticker(handler, getId(), stickerId);
    }

    @Override
    public List<GuildChannel> getChannels() {
        if (channels == null) {
            channels = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Channels(getId())), channel -> new GuildChannelImpl(channel, this));
        }
        return channels;
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        JsonNode channelNode = JsonUtils.fetchEntity(Routes.Channel.Get(channelId));
        return channelNode != null ? new GuildChannelImpl(channelNode, this) : null;
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    @Override
    public List<GuildChannel> getChannelsByName(String channelName) {
        return getChannels().stream().filter(channel -> channel.getName().equals(channelName)).toList();
    }

    @Override
    public void createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler) {
        ActionExecutor.createGuildChannel(handler, getId(), type);
    }

    @Override
    public void modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, getChannelById(channelId));
    }

    @Override
    public void modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler) {
        modifyChannel(String.valueOf(channelId), handler);
    }

    @Override
    public void modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getId(), channelId);
    }

    @Override
    public void modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        modifyChannelPosition(String.valueOf(channelId), handler);
    }

    @Override
    public void deleteChannelById(String channelId) {
        ApiClient.delete(Routes.Channel.Get(channelId));
    }

    @Override
    public void deleteChannelById(long channelId) {
        deleteChannelById(String.valueOf(channelId));
    }

    @Override
    public Stage getStageByChannelId(String channelId) {
        return new StageImpl(JsonUtils.fetchEntity(Routes.StageInstance(channelId)), this);
    }

    @Override
    public Stage getStageByChannelId(long channelId) {
        return getStageByChannelId(String.valueOf(channelId));
    }

    @Override
    public void createStage(Consumer<StageCreateAction> handler) {
        ActionExecutor.createStage(handler);
    }

    @Override
    public void modifyStageByChannelId(String channelId, String topic) {
        ApiClient.patch(JsonNodeFactory.instance.objectNode().put("topic", topic), Routes.StageInstance(channelId));
    }

    @Override
    public void deleteStage(String channelId) {
        ApiClient.delete(Routes.StageInstance(channelId));
    }

    @Override
    public List<GuildMember> getMembers() {
        if (members == null) {
            members = JsonUtils.getEntityList(
                    JsonUtils.fetchArrayParams(
                            Routes.Guild.Member.All(getId()),
                            Map.of("limit", "100")
                    ),
                    member -> new GuildMemberImpl(member, this)
            );
        }
        return members;
    }

    @Override
    public GuildMember getMemberById(String memberId) {
        return new GuildMemberImpl(JsonUtils.fetchEntity(Routes.Guild.Member.Get(getId(), memberId)), this);
    }

    @Override
    public GuildMember getMemberById(long memberId) {
        return getMemberById(String.valueOf(memberId));
    }

    @Override
    public List<GuildMember> searchMembers(String username, int limit) {
        return JsonUtils.getEntityList(
                JsonUtils.fetchArrayParams(
                        Routes.Guild.Member.Search(getId()),
                        Map.of(
                                "query", username,
                                "limit", String.valueOf(limit)
                        )
                ),
                guildMember -> new GuildMemberImpl(guildMember, this)
        );
    }

    @Override
    public List<GuildMember> searchMembers(String username) {
        return searchMembers(username, 1);
    }

    @Override
    public void modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler) {
        ActionExecutor.modifyGuildMember(handler, getId(), memberId);
    }

    @Override
    public void modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler) {
        modifyMember(String.valueOf(memberId), handler);
    }

    @Override
    public void modifyMe(Consumer<CurrentMemberModifyAction> handler) {
        ActionExecutor.execute(handler, CurrentMemberModifyAction.class, getId());
    }

    @Override
    public void kickMember(String memberId) {
        ApiClient.delete(Routes.Guild.Member.Get(getId(), memberId));
    }

    @Override
    public void kickMember(long memberId) {
        kickMember(String.valueOf(memberId));
    }

    @Override
    public void banMember(String memberId) {
        ApiClient.put(null, Routes.Guild.Ban.Get(getId(), memberId));
    }

    @Override
    public void banMember(long memberId) {
        banMember(String.valueOf(memberId));
    }

    @Override
    public void unbanMember(String memberId) {
        ApiClient.delete(Routes.Guild.Ban.Get(getId(), memberId));
    }

    @Override
    public void unbanMember(long memberId) {
        unbanMember(String.valueOf(memberId));
    }

    @Override
    public void addMember(String userId) {
        ApiClient.put(null, Routes.Guild.Member.Get(getId(), userId));
    }

    @Override
    public void addMember(long userId) {
        addMember(String.valueOf(userId));
    }

    @Override
    public List<GuildBan> getBans() {
        if (bans == null) {
            bans = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Ban.All(getId())), GuildBan::new);
        }
        return bans;
    }

    @Override
    public GuildBan getBanByUserId(String userId) {
        return getBans().stream().filter(ban -> ban.getUser().getId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public GuildBan getBanByUserId(long userId) {
        return getBanByUserId(String.valueOf(userId));
    }

    @Override
    public List<GuildRoleImpl> getRoles() {
        if (roles == null) {
            roles = JsonUtils.getEntityList(guild.get("roles"), role -> new GuildRoleImpl(role, this));
        }
        return roles;
    }

    @Override
    public GuildRoleImpl getRoleById(String roleId) {
        return getRoles().stream().filter(role -> role.getId().equals(roleId)).findFirst().orElse(null);
    }

    @Override
    public GuildRoleImpl getRoleById(long roleId) {
        return getRoleById(String.valueOf(roleId));
    }

    @Override
    public List<GuildRoleImpl> getRolesByName(String roleName) {
        return getRoles().stream().filter(role -> role.getName().equals(roleName)).toList();
    }

    @Override
    public GuildRoleImpl getPublicRole() {
        return getRoleById(getId());
    }

    @Override
    public void createRole(Consumer<GuildRoleCreateAction> handler) {
        ActionExecutor.createGuildRole(handler, getId(), getFeatures().contains("ROLE_ICONS"));
    }

    @Override
    public void modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, getId(), roleId, getFeatures().contains("ROLE_ICONS"));
    }

    @Override
    public void modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler) {
        modifyRole(String.valueOf(roleId), handler);
    }

    @Override
    public void deleteRole(String roleId) {
        ApiClient.delete(Routes.Guild.Role.Get(getId(), roleId));
    }

    @Override
    public void deleteRole(long roleId) {
        deleteRole(String.valueOf(roleId));
    }

    @Override
    public List<GuildCategory> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
            for (Channel channel : getChannels()) {
                if (channel.getType() == ChannelType.GUILD_CATEGORY) {
                    categories.add(new GuildCategoryImpl(JsonUtils.fetchEntity(Routes.Channel.Get(channel.getId())), this));
                }
            }
        }
        return categories;
    }

    @Override
    public GuildCategory getCategoryById(String categoryId) {
        return getCategories().stream().filter(category -> category.getId().equals(categoryId)).findFirst().orElse(null);
    }

    @Override
    public GuildCategory getCategoryById(long categoryId) {
        return getCategoryById(String.valueOf(categoryId));
    }

    @Override
    public List<GuildCategory> getCategoriesByName(String categoryName) {
        return getCategories().stream().filter(category -> category.getName().equals(categoryName)).toList();
    }

    @Override
    public void modify(Consumer<GuildModifyAction> handler) {
        ActionExecutor.modifyGuild(handler, getId(), getFeatures());
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.Get(getId()));
    }

    @Override
    public boolean hasActiveThreads() {
        return !getActiveThreads().isEmpty();
    }

    @Override
    public int getActiveThreadsCount() {
        return getActiveThreads().size();
    }

    @Override
    public List<Thread> getActiveThreads() {
        if (threads == null) {
            threads = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.ActiveThreads(getId())).get("threads"), thread -> new ThreadImpl(thread, this));
        }
        return threads;
    }

    @Override
    public List<Thread> getPublicArchiveThreads(String channelId) {
        return getChannelById(channelId).getPublicArchiveThreads();
    }

    @Override
    public List<Thread> getPublicArchiveThreads(long channelId) {
        return getPublicArchiveThreads(String.valueOf(channelId));
    }

    @Override
    public List<Thread> getPrivateArchiveThreads(String channelId) {
        return getChannelById(channelId).getPrivateArchiveThreads();
    }

    @Override
    public List<Thread> getPrivateArchiveThreads(long channelId) {
        return getPrivateArchiveThreads(String.valueOf(channelId));
    }

    @Override
    public List<Thread> getJoinedPrivateArchiveThreads(String channelId) {
        return getChannelById(channelId).getJoinedPrivateArchiveThreads();
    }

    @Override
    public List<Thread> getJoinedPrivateArchiveThreads(long channelId) {
        return getJoinedPrivateArchiveThreads(String.valueOf(channelId));
    }

    @Override
    public Thread getThreadById(String threadId) {
        return getActiveThreads().stream().filter(thread -> thread.getId().equals(threadId)).findFirst().orElse(null);
    }

    @Override
    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }

    @Override
    public List<Thread> getActiveThreadsByName(String threadName) {
        return getActiveThreads().stream().filter(thread -> thread.getName().equals(threadName)).toList();
    }

    @Override
    public List<GuildScheduledEvent> getGuildEvents() {
        if (events == null) {
            events = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.ScheduledEvent.All(getId())), event -> new GuildScheduledEventImpl(event, this));
        }
        return events;
    }

    @Override
    public GuildScheduledEvent getGuildEventById(String eventId) {
        return getGuildEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    @Override
    public GuildScheduledEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    @Override
    public List<GuildScheduledEvent> getGuildEventsByName(String eventName) {
        return getGuildEvents().stream().filter(event -> event.getName().equals(eventName)).toList();
    }

    @Override
    public void modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler) {
        ActionExecutor.modifyGuildEvent(handler, getId(), eventId);
    }

    @Override
    public void modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler) {
        modifyGuildEvent(String.valueOf(eventId), handler);
    }

    @Override
    public AuditLog getAuditLog() {
        if (auditLog == null) {
            //todo fix audit log nulls
            auditLog = new AuditLog(JsonUtils.fetchEntity(Routes.Guild.AuditLog(getId())), this);
        }
        return auditLog;
    }

    @Override
    public List<Emoji> getEmojis() {
        if (emojis == null) {
            emojis = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Emoji.All(getId())), emoji -> new EmojiImpl(emoji, this));
        }
        return emojis;
    }

    @Override
    public Emoji getEmojiById(String emojiId) {
        return new EmojiImpl(JsonUtils.fetchEntity(Routes.Guild.Emoji.Get(getId(), emojiId)), this);
    }

    @Override
    public Emoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    @Override
    public List<Emoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.getName().equals(emojiName)).toList();
    }

    @Override
    public List<Emoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    @Override
    public List<Emoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    @Override
    public void createEmoji(Consumer<EmojiCreateAction> handler) {
        ActionExecutor.execute(handler, EmojiCreateAction.class, getId());
    }

    @Override
    public void modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler) {
        ActionExecutor.modifyEmoji(handler, getId(), emojiId);
    }

    @Override
    public void modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler) {
        modifyEmoji(String.valueOf(emojiId), handler);
    }

    @Override
    public void deleteEmoji(String emojiId) {
        ApiClient.delete(Routes.Guild.Emoji.Get(getId(), emojiId));
    }

    @Override
    public void deleteEmoji(long emojiId) {
        deleteEmoji(String.valueOf(emojiId));
    }

    @Override
    public List<AutoModRule> getAutoModRules() {
        if (autoModRules == null) {
            autoModRules = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.AutoMod.All(getId())), rule -> new AutoModRuleImpl(rule, this));
        }
        return autoModRules;
    }

    @Override
    public AutoModRule getAutoModRuleById(String ruleId) {
        return new AutoModRuleImpl(JsonUtils.fetchEntity(Routes.Guild.AutoMod.Get(getId(), ruleId)), this);
    }

    @Override
    public AutoModRule getAutoModRuleById(long ruleId) {
        return getAutoModRuleById(String.valueOf(ruleId));
    }

    @Override
    public List<AutoModRule> getAutoModRuleByName(String ruleName) {
        return getAutoModRules().stream().filter(rule -> rule.getName().equals(ruleName)).toList();
    }

    @Override
    public List<AutoModRule> getAutoModRuleByCreatorId(String userId) {
        return getAutoModRules().stream().filter(rule -> rule.getCreator().getId().equals(userId)).toList();
    }

    @Override
    public List<AutoModRule> getAutoModRuleByCreatorId(long userId) {
        return getAutoModRuleByCreatorId(String.valueOf(userId));
    }

    @Override
    public void createAutoModRule(Consumer<AutoModRuleCreateAction> handler) {
        ActionExecutor.execute(handler, AutoModRuleCreateAction.class, getId());
    }

    @Override
    public void modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler) {
        ActionExecutor.modifyAutoModRule(handler, getId(), getAutoModRuleById(ruleId).getTriggerType());
    }

    @Override
    public void modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler) {
        modifyAutoModRule(String.valueOf(ruleId), handler);
    }

    @Override
    public void deleteAutoModRule(String ruleId) {
        ApiClient.delete(Routes.Guild.AutoMod.Get(getId(), ruleId));
    }

    @Override
    public void deleteAutoModRule(long ruleId) {
        deleteAutoModRule(String.valueOf(ruleId));
    }

    @Override
    public GuildPreview getPreview() {
        if (preview == null) {
            preview = new GuildPreview(JsonUtils.fetchEntity(Routes.Guild.Preview(getId())), this);
        }
        return preview;
    }

    @Override
    public void setMfaLevel(MfaLevel mfaLevel) {
        ApiClient.post(
                JsonNodeFactory.instance.objectNode()
                        .put("level", mfaLevel.getValue()),
                Routes.Guild.Mfa(getId())
        );
    }

    @Override
    public List<VoiceRegion> getVoiceRegions() {
        if (regions == null) {
            regions = new ArrayList<>();
            for (JsonNode region : JsonUtils.fetchArray(Routes.Guild.VoiceRegions(getId()))) {
                regions.add(EnumUtils.getEnumObject(region, "id", VoiceRegion.class));
            }
        }
        return regions;
    }

    @Override
    public VoiceRegion getOptimalVoiceRegion() {
        if (optimalRegion == null) {
            for (JsonNode region : JsonUtils.fetchArray(Routes.Guild.VoiceRegions(getId()))) {
                if (region.get("optimal").asBoolean()) {
                    optimalRegion = EnumUtils.getEnumObject(region, "id", VoiceRegion.class);
                    break;
                }
            }
        }
        return optimalRegion;
    }

    @Override
    public List<Invite> getInvites() {
        if (invites == null) {
            invites = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Invites(getId())), invite -> new InviteImpl(invite, this));
        }
        return invites;
    }

    @Override
    public Invite getInvite(String code) {
        return getInvites().stream().filter(invite -> invite.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Integration> getIntegrations() {
        if (integrations == null) {
            integrations = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Integration.All(getId())), integration -> new IntegrationImpl(integration, this));
        }
        return integrations;
    }

    @Override
    public Integration getIntegrationById(String integrationId) {
        return getIntegrations().stream().filter(integration -> integration.getId().equals(integrationId)).findFirst().orElse(null);
    }

    @Override
    public Integration getIntegrationById(long integrationId) {
        return getIntegrationById(String.valueOf(integrationId));
    }

    @Override
    public void deleteIntegration(String integrationId) {
        ApiClient.delete(Routes.Guild.Integration.Get(getId(), integrationId));
    }

    @Override
    public void deleteIntegration(long integrationId) {
        deleteIntegration(String.valueOf(integrationId));
    }

    @Override
    public GuildWidget getWidget() {
        if (widget == null) {
            widget = new GuildWidget(JsonUtils.fetchEntity(Routes.Guild.Widget.Get(getId())), this);
        }
        return widget;
    }

    @Override
    public GuildWidgetSettings getWidgetSettings() {
        if (widgetSettings == null) {
            widgetSettings = new GuildWidgetSettings(JsonUtils.fetchEntity(Routes.Guild.Widget.Settings(getId())), this);
        }
        return widgetSettings;
    }

    @Override
    public void modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, getId());
    }

    @Override
    public GuildWelcomeScreen getWelcomeScreen() {
        if (welcomeScreen == null) {
            JsonNode welcomeScreenNode = JsonUtils.fetchEntity(Routes.Guild.WelcomeScreen(getId()));
            welcomeScreen = welcomeScreenNode != null ? new GuildWelcomeScreen(welcomeScreenNode, this) : null;
        }
        return welcomeScreen;
    }

    @Override
    public void modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, getId());
    }

    @Override
    public GuildOnboarding getOnboarding() {
        if (onboarding == null) {
            onboarding = new GuildOnboarding(JsonUtils.fetchEntity(Routes.Guild.Onboarding(getId())), this);
        }
        return onboarding;
    }

    @Override
    public void modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, getId());
    }

    @Override
    public List<Webhook> getWebhooks() {
        if (webhooks == null) {
            webhooks = JsonUtils.getEntityList(JsonUtils.fetchArray(Routes.Guild.Webhooks(getId())), webhook -> new WebhookImpl(webhook, this));
        }
        return webhooks;
    }

    @Override
    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findFirst().orElse(null);
    }

    @Override
    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    @Override
    public Webhook getWebhookByName(String webhookName) {
        return getWebhooks().stream().filter(webhook -> webhook.getName().equals(webhookName)).findFirst().orElse(null);
    }
}
