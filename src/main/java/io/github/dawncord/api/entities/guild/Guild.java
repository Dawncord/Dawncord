package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Constants;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.*;
import io.github.dawncord.api.action.automoderule.AutoModRuleCreateAction;
import io.github.dawncord.api.action.automoderule.AutoModRuleModifyAction;
import io.github.dawncord.api.action.emoji.EmojiCreateAction;
import io.github.dawncord.api.action.emoji.EmojiModifyAction;
import io.github.dawncord.api.action.guild.GuildModifyAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelCreateAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelModifyAction;
import io.github.dawncord.api.action.guildrole.GuildRoleCreateAction;
import io.github.dawncord.api.action.guildrole.GuildRoleModifyAction;
import io.github.dawncord.api.action.guildsticker.GuildStickerCreateAction;
import io.github.dawncord.api.action.guildsticker.GuildStickerModifyAction;
import io.github.dawncord.api.entities.*;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.entities.channel.*;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.guild.audit.AuditLog;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.guild.welcomescreen.GuildWelcomeScreen;
import io.github.dawncord.api.entities.guild.widget.GuildWidget;
import io.github.dawncord.api.entities.guild.widget.GuildWidgetSettings;
import io.github.dawncord.api.entities.image.DiscoverySplash;
import io.github.dawncord.api.entities.image.GuildIcon;
import io.github.dawncord.api.entities.image.Splash;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.entities.message.sticker.StickerImpl;
import io.github.dawncord.api.event.CreateEvent;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Represents a guild in a Discord server.
 */
public class Guild implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode guild;
    private String id;
    private String name;
    private GuildIcon icon;
    private Splash splash;
    private DiscoverySplash discoverySplash;
    private Boolean isOwner;
    private User owner;
    private List<PermissionType> permissions;
    private GuildChannel afkChannel;
    private Integer afkTimeout;
    private Boolean widgetEnabled;
    private GuildChannel widgetChannel;
    private VerificationLevel verificationLevel;
    private MessageNotificationLevel messageNotificationLevel;
    private ContentFilterLevel contentFilterLevel;
    private List<GuildRole> roles;
    private List<GuildChannel> channels;
    private List<Thread> activeThreads;
    private List<GuildCategory> categories;
    private List<GuildMember> members;
    private List<CustomEmoji> emojis;
    private List<GuildFeature> features;
    private List<GuildScheduledEvent> guildEvents;
    private List<GuildBan> bans;
    private List<Invite> invites;
    private List<Integration> integrations;
    private GuildWidget widget;
    private GuildWidgetSettings widgetSettings;
    private AuditLog auditLog;
    private List<AutoModRule> autoModRules;
    private MfaLevel mfaLevel;
    private Application application;
    private GuildChannel systemChannel;
    private List<SystemChannelFlag> systemChannelFlags;
    private GuildChannel rulesChannel;
    private Integer maxPresences;
    private Integer maxMembers;
    private String vanityUrlCode;
    private String description;
    private List<VoiceRegion> voiceRegions;
    private VoiceRegion optimalVoiceRegion;
    //private Banner banner;
    private PremiumTier premiumTier;
    private Integer boosts;
    private Locale preferredLocale;
    private GuildChannel updatesChannel;
    private Integer maxVideoChannelUsers;
    private Integer maxStageVideoChannelUsers;
    private Integer memberCount;
    private Integer presenceCount;
    private GuildWelcomeScreen welcomeScreen;
    private GuildOnboarding onboarding;
    private GuildPreview preview;
    private List<Webhook> webhooks;
    //private NsfwLevel nsfwLevel;
    private List<Sticker> stickers;
    private Boolean boostProgressBar;
    private GuildChannel safetyAlertsChannel;
    //private IncidentsData incidentsData;

    /**
     * Constructs a new GuildImpl object with the specified JSON node representing the guild.
     *
     * @param guild The JSON node representing the guild.
     */
    public Guild(JsonNode guild) {
        this.guild = guild;
        loader = new LazyLoader(guild);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public String getDescription() {
        description = loader.loadString(description, "description");
        return description;
    }

    public boolean isOwner() {
        JsonNode thisGuild = getBotGuild();
        isOwner = loader.loadIfExists(
                isOwner,
                thisGuild,
                "owner",
                () -> thisGuild.get("owner").asBoolean()
        );
        return isOwner;
    }

    public User getOwner() {
        owner = loader.loadIfExists(owner, "owner_id",
                () -> new UserImpl(JsonUtils.fetch(Routes.User(guild.get("owner_id").asText()))));
        return owner;
    }

    public List<PermissionType> getPermissions() {
        permissions = loader.load(permissions,
                () -> EnumUtils.getEnumListFromLong(getBotGuild(), "permissions", PermissionType.class));
        return permissions;
    }

    public GuildChannel getAfkChannel() {
        afkChannel = loader.loadIfExists(afkChannel, "afk_channel_id",
                () -> getChannelById(guild.get("afk_channel_id").asText()));
        return afkChannel;
    }

    public int getAfkTimeout() {
        afkTimeout = loader.loadInt(afkTimeout, "afk_timeout");
        return afkTimeout;
    }

    public boolean isWidgetEnabled() {
        widgetEnabled = loader.loadBoolean(widgetEnabled, "widget_enabled");
        return widgetEnabled;
    }

    public GuildChannel getWidgetChannel() {
        widgetChannel = loader.loadIfExists(widgetChannel, "widget_channel_id",
                () -> getChannelById(guild.get("widget_channel_id").asText()));
        return widgetChannel;
    }

    public GuildIcon getIcon() {
        icon = loader.loadIfExists(icon, "icon",
                () -> new GuildIcon(getId(), guild.get("icon").asText()));
        return icon;
    }

    public Splash getSplash() {
        splash = loader.loadIfExists(splash, "splash",
                () -> new Splash(getId(), guild.get("splash").asText()));
        return splash;
    }

    public DiscoverySplash getDiscoverySplash() {
        discoverySplash = loader.loadIfExists(discoverySplash, "discovery_splash",
                () -> new DiscoverySplash(getId(), guild.get("discovery_splash").asText()));
        return discoverySplash;
    }

    //todo check
    public List<GuildFeature> getFeatures() {
        features = loader.loadEnumValues(features, "features", GuildFeature.class);
        return features;
    }

    public VerificationLevel getVerificationLevel() {
        verificationLevel = loader.loadEnumObject(verificationLevel, "verification_level", VerificationLevel.class);
        return verificationLevel;
    }

    public MessageNotificationLevel getMessageNotificationLevel() {
        messageNotificationLevel = loader.loadEnumObject(messageNotificationLevel, "default_message_notifications", MessageNotificationLevel.class);
        return messageNotificationLevel;
    }

    public ContentFilterLevel getContentFilterLevel() {
        contentFilterLevel = loader.loadEnumObject(contentFilterLevel, "explicit_content_filter", ContentFilterLevel.class);
        return contentFilterLevel;
    }

    public List<Sticker> getStickers() {
        stickers = loader.loadEntityList(stickers, "stickers",
                JsonUtils.fetch(Routes.Guild.Sticker.All(getId())),
                sticker -> new StickerImpl(sticker, this)
        );
        return stickers;
    }

    public boolean isBoostProgressBarEnabled() {
        boostProgressBar = loader.loadBoolean(boostProgressBar, "premium_progress_bar_enabled");
        return boostProgressBar;
    }

    public GuildChannel getSafetyAlertsChannel() {
        safetyAlertsChannel = loader.loadIfExists(safetyAlertsChannel, "safety_alerts_channel_id",
                () -> getChannelById(guild.get("safety_alerts_channel_id").asText()));
        return safetyAlertsChannel;
    }

    public Sticker getStickerById(String stickerId) {
        return new StickerImpl(JsonUtils.fetch(Routes.Guild.Sticker.Get(getId(), stickerId)), this);
    }

    public Sticker getStickerById(long stickerId) {
        return getStickerById(String.valueOf(stickerId));
    }

    public List<Sticker> getStickersByName(String stickerName) {
        return getStickers().stream().filter(sticker -> sticker.getName().equals(stickerName)).toList();
    }

    public List<Sticker> getStickersByAuthorId(String userId) {
        return getStickers().stream().filter(sticker -> sticker.getAuthor().getId().equals(userId)).toList();
    }

    public List<Sticker> getStickersByAuthorId(long userId) {
        return getStickersByAuthorId(String.valueOf(userId));
    }

    public CreateEvent<Sticker> createSticker(Consumer<GuildStickerCreateAction> handler) {
        String id = ActionExecutor.createSticker(handler, this);
        return new CreateEvent<>(getStickerById(id));
    }

    public ModifyEvent<Sticker> modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler) {
        ActionExecutor.modifyGuildSticker(handler, getId(), stickerId);
        return new ModifyEvent<>(getStickerById(stickerId));
    }

    public List<GuildChannel> getChannels() {
        channels = loader.loadEntityList(
                channels,
                JsonUtils.fetch(Routes.Guild.Channels(getId())),
                channel -> new GuildChannel(channel, this)
        );
        return channels;
    }

    public GuildChannel getChannelById(String channelId) {
        JsonNode channelNode = JsonUtils.fetch(Routes.Channel.Get(channelId));
        return channelNode != null ? new GuildChannel(channelNode, this) : null;
    }

    public GuildChannel getChannelById(long channelId) {
        return getChannelById(String.valueOf(channelId));
    }

    public List<GuildChannel> getChannelsByName(String channelName) {
        return getChannels().stream().filter(channel -> channel.getName().equals(channelName)).toList();
    }

    public CreateEvent<GuildChannel> createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler) {
        String id = ActionExecutor.createGuildChannel(handler, getId(), type);
        return new CreateEvent<>(getChannelById(id));
    }

    public ModifyEvent<GuildChannel> modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, getChannelById(channelId));
        return new ModifyEvent<>(getChannelById(channelId));
    }

    public ModifyEvent<GuildChannel> modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler) {
        return modifyChannel(String.valueOf(channelId), handler);
    }

    public ModifyEvent<GuildChannel> modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getId(), channelId);
        return new ModifyEvent<>(getChannelById(channelId));
    }

    public ModifyEvent<GuildChannel> modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        return modifyChannelPosition(String.valueOf(channelId), handler);
    }

    public void deleteChannelById(String channelId) {
        ApiClient.delete(Routes.Channel.Get(channelId));
    }

    public void deleteChannelById(long channelId) {
        deleteChannelById(String.valueOf(channelId));
    }

    public Stage getStageByChannelId(String channelId) {
        return new Stage(JsonUtils.fetch(Routes.StageInstance(channelId)), this);
    }

    public Stage getStageByChannelId(long channelId) {
        return getStageByChannelId(String.valueOf(channelId));
    }

    public CreateEvent<Stage> createStage(Consumer<StageCreateAction> handler) {
        String channelId = ActionExecutor.createStage(handler);
        return new CreateEvent<>(getStageByChannelId(channelId));
    }

    public ModifyEvent<Stage> modifyStageByChannelId(String channelId, String topic) {
        ApiClient.patch(JsonNodeFactory.instance.objectNode().put("topic", topic), Routes.StageInstance(channelId));
        return new ModifyEvent<>(getStageByChannelId(channelId));
    }

    public void deleteStage(String channelId) {
        ApiClient.delete(Routes.StageInstance(channelId));
    }

    public List<GuildMember> getMembers() {
        members = loader.loadEntityList(
                members,
                JsonUtils.fetchParams(
                        Routes.Guild.Member.All(getId()),
                        Map.of("limit", "100")
                ),
                member -> new GuildMemberImpl(member, this)
        );
        return members;
    }

    public GuildMember getMemberById(String memberId) {
        return new GuildMemberImpl(JsonUtils.fetch(Routes.Guild.Member.Get(getId(), memberId)), this);
    }

    public GuildMember getMemberById(long memberId) {
        return getMemberById(String.valueOf(memberId));
    }

    public List<GuildMember> searchMembers(String username, int limit) {
        return JsonUtils.getEntityList(
                JsonUtils.fetchParams(
                        Routes.Guild.Member.Search(getId()),
                        Map.of(
                                "query", username,
                                "limit", String.valueOf(limit)
                        )
                ),
                guildMember -> new GuildMemberImpl(guildMember, this)
        );
    }

    public List<GuildMember> searchMember(String username) {
        return searchMembers(username, 1);
    }

    public ModifyEvent<GuildMember> modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler) {
        ActionExecutor.modifyGuildMember(handler, getId(), memberId);
        return new ModifyEvent<>(getMemberById(memberId));
    }

    public ModifyEvent<GuildMember> modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler) {
        return modifyMember(String.valueOf(memberId), handler);
    }

    public ModifyEvent<GuildMember> modifyMe(Consumer<CurrentMemberModifyAction> handler) {
        ActionExecutor.execute(handler, CurrentMemberModifyAction.class, getId());
        return new ModifyEvent<>(getMemberById(Constants.BOT_ID));
    }

    public void kickMember(String memberId) {
        ApiClient.delete(Routes.Guild.Member.Get(getId(), memberId));
    }

    public void kickMember(long memberId) {
        kickMember(String.valueOf(memberId));
    }

    public void banMember(String memberId) {
        ApiClient.put(null, Routes.Guild.Ban.Get(getId(), memberId));
    }

    public void banMember(long memberId) {
        banMember(String.valueOf(memberId));
    }

    public void unbanMember(String memberId) {
        ApiClient.delete(Routes.Guild.Ban.Get(getId(), memberId));
    }

    public void unbanMember(long memberId) {
        unbanMember(String.valueOf(memberId));
    }

    public void addMember(String accessToken, String userId) {
        ApiClient.put(
                JsonNodeFactory.instance.objectNode()
                        .put("access_token", accessToken),
                Routes.Guild.Member.Get(getId(), userId)
        );
    }

    public void addMember(String accessToken, long userId) {
        addMember(accessToken, String.valueOf(userId));
    }

    public List<GuildBan> getBans() {
        bans = loader.loadEntityList(
                bans,
                JsonUtils.fetch(Routes.Guild.Ban.All(getId())),
                GuildBan::new
        );
        return bans;
    }

    public GuildBan getBanByUserId(String userId) {
        return getBans().stream().filter(ban -> ban.getUser().getId().equals(userId)).findFirst().orElse(null);
    }

    public GuildBan getBanByUserId(long userId) {
        return getBanByUserId(String.valueOf(userId));
    }

    public List<GuildRole> getRoles() {
        roles = loader.loadEntityList(
                roles,
                JsonUtils.fetch(Routes.Guild.Role.All(getId())),
                role -> new GuildRole(role, this)
        );
        return roles;
    }

    public GuildRole getRoleById(String roleId) {
        return getRoles().stream().filter(role -> role.getId().equals(roleId)).findFirst().orElse(null);
    }

    public GuildRole getRoleById(long roleId) {
        return getRoleById(String.valueOf(roleId));
    }

    public List<GuildRole> getRolesByName(String roleName) {
        return getRoles().stream().filter(role -> role.getName().equals(roleName)).toList();
    }

    public GuildRole getPublicRole() {
        return getRoleById(getId());
    }

    public CreateEvent<GuildRole> createRole(Consumer<GuildRoleCreateAction> handler) {
        String id = ActionExecutor.createGuildRole(handler, getId(), getFeatures().contains(GuildFeature.ROLE_ICONS));
        return new CreateEvent<>(getRoleById(id));
    }

    public ModifyEvent<GuildRole> modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, getId(), roleId, getFeatures().contains(GuildFeature.ROLE_ICONS));
        return new ModifyEvent<>(getRoleById(roleId));
    }

    public ModifyEvent<GuildRole> modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler) {
        return modifyRole(String.valueOf(roleId), handler);
    }

    public void deleteRole(String roleId) {
        ApiClient.delete(Routes.Guild.Role.Get(getId(), roleId));
    }

    public void deleteRole(long roleId) {
        deleteRole(String.valueOf(roleId));
    }

    public List<GuildCategory> getCategories() {
        categories = loader.load(categories, () -> {
            categories = new ArrayList<>();
            for (Channel channel : getChannels()) {
                if (channel.getType() == ChannelType.GUILD_CATEGORY) {
                    categories.add(new GuildCategory(JsonUtils.fetch(Routes.Channel.Get(channel.getId())), this));
                }
            }
            return categories;
        });
        return categories;
    }

    public GuildCategory getCategoryById(String categoryId) {
        return getCategories().stream().filter(category -> category.getId().equals(categoryId)).findFirst().orElse(null);
    }

    public GuildCategory getCategoryById(long categoryId) {
        return getCategoryById(String.valueOf(categoryId));
    }

    public List<GuildCategory> getCategoriesByName(String categoryName) {
        return getCategories().stream().filter(category -> category.getName().equals(categoryName)).toList();
    }

    public ModifyEvent<Guild> modify(Consumer<GuildModifyAction> handler) {
        ActionExecutor.modifyGuild(handler, getId(), getFeatures());
        return new ModifyEvent<>(new Guild(JsonUtils.fetch(Routes.Guild.Get(getId()))));
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.Get(getId()));
    }

    public boolean hasActiveThreads() {
        return !getActiveThreads().isEmpty();
    }

    public int getActiveThreadsCount() {
        return getActiveThreads().size();
    }

    public List<Thread> getActiveThreads() {
        activeThreads = loader.loadEntityList(
                activeThreads,
                JsonUtils.fetch(Routes.Guild.ActiveThreads(getId())).get("threads"),
                thread -> new Thread(thread, this)
        );
        return activeThreads;
    }

    public List<Thread> getPublicArchiveThreads(String channelId) {
        return getChannelById(channelId).getPublicArchiveThreads();
    }

    public List<Thread> getPublicArchiveThreads(long channelId) {
        return getPublicArchiveThreads(String.valueOf(channelId));
    }

    public List<Thread> getPrivateArchiveThreads(String channelId) {
        return getChannelById(channelId).getPrivateArchiveThreads();
    }

    public List<Thread> getPrivateArchiveThreads(long channelId) {
        return getPrivateArchiveThreads(String.valueOf(channelId));
    }

    public List<Thread> getJoinedPrivateArchiveThreads(String channelId) {
        return getChannelById(channelId).getJoinedPrivateArchiveThreads();
    }

    public List<Thread> getJoinedPrivateArchiveThreads(long channelId) {
        return getJoinedPrivateArchiveThreads(String.valueOf(channelId));
    }

    public Thread getThreadById(String threadId) {
        return getActiveThreads().stream().filter(thread -> thread.getId().equals(threadId)).findFirst().orElse(null);
    }

    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }

    public List<Thread> getActiveThreadsByName(String threadName) {
        return getActiveThreads().stream().filter(thread -> thread.getName().equals(threadName)).toList();
    }

    public List<GuildScheduledEvent> getGuildEvents() {
        guildEvents = loader.loadEntityList(
                guildEvents,
                JsonUtils.fetch(Routes.Guild.ScheduledEvent.All(getId())),
                GuildScheduledEvent::new
        );
        return guildEvents;
    }

    public GuildScheduledEvent getGuildEventById(String eventId) {
        return getGuildEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    public GuildScheduledEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    public List<GuildScheduledEvent> getGuildEventsByName(String eventName) {
        return getGuildEvents().stream().filter(event -> event.getName().equals(eventName)).toList();
    }

    public ModifyEvent<GuildScheduledEvent> modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler) {
        ActionExecutor.modifyGuildEvent(handler, getId(), eventId);
        return new ModifyEvent<>(getGuildEventById(eventId));
    }

    public ModifyEvent<GuildScheduledEvent> modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler) {
        return modifyGuildEvent(String.valueOf(eventId), handler);
    }

    public AuditLog getAuditLog() {
        auditLog = loader.load(auditLog, () -> new AuditLog(JsonUtils.fetch(Routes.Guild.AuditLog(getId())), this));
        return auditLog;
    }

    public List<CustomEmoji> getEmojis() {
        emojis = loader.loadEntityList(
                emojis,
                JsonUtils.fetch(Routes.Guild.Emoji.All(getId())),
                emoji -> new CustomEmojiImpl(emoji, this)
        );
        return emojis;
    }

    public CustomEmoji getEmojiById(String emojiId) {
        return new CustomEmojiImpl(JsonUtils.fetch(Routes.Guild.Emoji.Get(getId(), emojiId)), this);
    }

    public CustomEmoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    public List<CustomEmoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.name().equals(emojiName)).toList();
    }

    public List<CustomEmoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    public List<CustomEmoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    public CreateEvent<CustomEmoji> createEmoji(Consumer<EmojiCreateAction> handler) {
        String id = ActionExecutor.createEmoji(handler, getId());
        return new CreateEvent<>(getEmojiById(id));
    }

    public ModifyEvent<CustomEmoji> modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler) {
        ActionExecutor.modifyEmoji(handler, getId(), emojiId);
        return new ModifyEvent<>(getEmojiById(emojiId));
    }

    public ModifyEvent<CustomEmoji> modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler) {
        return modifyEmoji(String.valueOf(emojiId), handler);
    }

    public void deleteEmoji(String emojiId) {
        ApiClient.delete(Routes.Guild.Emoji.Get(getId(), emojiId));
    }

    public void deleteEmoji(long emojiId) {
        deleteEmoji(String.valueOf(emojiId));
    }

    public List<AutoModRule> getAutoModRules() {
        autoModRules = loader.loadEntityList(
                autoModRules,
                JsonUtils.fetch(Routes.Guild.AutoMod.All(getId())),
                rule -> new AutoModRule(rule, this)
        );
        return autoModRules;
    }

    public MfaLevel getMfaLevel() {
        mfaLevel = loader.loadEnumObject(mfaLevel, "mfa_level", MfaLevel.class);
        return mfaLevel;
    }

    public void setMfaLevel(MfaLevel mfaLevel) {
        ApiClient.post(
                JsonNodeFactory.instance.objectNode()
                        .put("level", mfaLevel.getValue()),
                Routes.Guild.Mfa(getId())
        );
    }

    public Application getApplication() {
        application = loader.loadIfExists(application, "application_id",
                () -> new Application(JsonUtils.fetch(Routes.Application(guild.get("application_id").asText()))));
        return application;
    }

    public GuildChannel getSystemChannel() {
        systemChannel = loader.loadIfExists(systemChannel, "system_channel_id",
                () -> getChannelById(guild.get("system_channel_id").asText()));
        return systemChannel;
    }

    public List<SystemChannelFlag> getSystemChannelFlags() {
        systemChannelFlags = loader.loadEnumListFromLong(systemChannelFlags, "system_channel_flags", SystemChannelFlag.class);
        return systemChannelFlags;
    }

    public GuildChannel getRulesChannel() {
        rulesChannel = loader.loadIfExists(rulesChannel, "rules_channel_id",
                () -> getChannelById(guild.get("rules_channel_id").asText()));
        return rulesChannel;
    }

    public int getMaxPresences() {
        maxPresences = loader.loadInt(maxPresences, "max_presences");
        return maxPresences;
    }

    public int getMaxMembers() {
        maxMembers = loader.loadInt(maxMembers, "max_members");
        return maxMembers;
    }

    public String getVanityUrlCode() {
        vanityUrlCode = loader.loadString(vanityUrlCode, "vanity_url_code");
        return vanityUrlCode;
    }

    public AutoModRule getAutoModRuleById(String ruleId) {
        return new AutoModRule(JsonUtils.fetch(Routes.Guild.AutoMod.Get(getId(), ruleId)), this);
    }

    public AutoModRule getAutoModRuleById(long ruleId) {
        return getAutoModRuleById(String.valueOf(ruleId));
    }

    public List<AutoModRule> getAutoModRuleByName(String ruleName) {
        return getAutoModRules().stream().filter(rule -> rule.getName().equals(ruleName)).toList();
    }

    public List<AutoModRule> getAutoModRuleByCreatorId(String userId) {
        return getAutoModRules().stream().filter(rule -> rule.getCreator().getId().equals(userId)).toList();
    }

    public List<AutoModRule> getAutoModRuleByCreatorId(long userId) {
        return getAutoModRuleByCreatorId(String.valueOf(userId));
    }

    public CreateEvent<AutoModRule> createAutoModRule(Consumer<AutoModRuleCreateAction> handler) {
        String id = ActionExecutor.createAutoModRule(handler, getId());
        return new CreateEvent<>(getAutoModRuleById(id));
    }

    public ModifyEvent<AutoModRule> modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler) {
        ActionExecutor.modifyAutoModRule(handler, getId(), getAutoModRuleById(ruleId).getTriggerType());
        return new ModifyEvent<>(getAutoModRuleById(ruleId));
    }

    public ModifyEvent<AutoModRule> modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler) {
        return modifyAutoModRule(String.valueOf(ruleId), handler);
    }

    public void deleteAutoModRule(String ruleId) {
        ApiClient.delete(Routes.Guild.AutoMod.Get(getId(), ruleId));
    }

    public void deleteAutoModRule(long ruleId) {
        deleteAutoModRule(String.valueOf(ruleId));
    }

    public GuildPreview getPreview() {
        preview = loader.load(preview, () -> new GuildPreview(JsonUtils.fetch(Routes.Guild.Preview(getId())), this));
        return preview;
    }

    public List<VoiceRegion> getVoiceRegions() {
        voiceRegions = loader.load(voiceRegions, () -> {
            voiceRegions = new ArrayList<>();
            for (JsonNode region : JsonUtils.fetch(Routes.Guild.VoiceRegions(getId()))) {
                voiceRegions.add(EnumUtils.getEnumObject(region, "id", VoiceRegion.class));
                if (region.get("optimal").asBoolean()) {
                    optimalVoiceRegion = EnumUtils.getEnumObject(region, "id", VoiceRegion.class);
                }
            }
            return voiceRegions;
        });
        return voiceRegions;
    }

    public VoiceRegion getOptimalVoiceRegion() {
        return optimalVoiceRegion;
    }

    public PremiumTier getPremiumTier() {
        premiumTier = loader.loadEnumObject(premiumTier, "premium_tier", PremiumTier.class);
        return premiumTier;
    }

    public int getBoosts() {
        boosts = loader.loadInt(boosts, "premium_subscription_count");
        return boosts;
    }

    public Locale getPreferredLocale() {
        preferredLocale = loader.loadEnumObject(preferredLocale, "preferred_locale", Locale.class);
        return preferredLocale;
    }

    public GuildChannel getUpdatesChannel() {
        updatesChannel = loader.loadIfExists(updatesChannel, "public_updates_channel_id",
                () -> getChannelById(guild.get("public_updates_channel_id").asText()));
        return updatesChannel;
    }

    public int getMaxVideoChannelUsers() {
        maxVideoChannelUsers = loader.loadInt(maxVideoChannelUsers, "max_video_channel_users");
        return maxVideoChannelUsers;
    }

    public int getMaxStageVideoChannelUsers() {
        maxStageVideoChannelUsers = loader.loadInt(maxStageVideoChannelUsers, "max_stage_video_channel_users");
        return maxStageVideoChannelUsers;
    }

    public int getMemberCount() {
        memberCount = loader.loadInt(memberCount, "approximate_member_count");
        return memberCount;
    }

    public int getPresenceCount() {
        presenceCount = loader.loadInt(presenceCount, "approximate_presence_count");
        return presenceCount;
    }

    public List<Invite> getInvites() {
        invites = loader.loadEntityList(
                invites,
                JsonUtils.fetch(Routes.Guild.Invites(getId())),
                invite -> new Invite(invite, this)
        );
        return invites;
    }

    public Invite getInvite(String code) {
        return getInvites().stream().filter(invite -> invite.getCode().equals(code)).findFirst().orElse(null);
    }

    public List<Integration> getIntegrations() {
        integrations = loader.loadEntityList(
                integrations,
                JsonUtils.fetch(Routes.Guild.Integration.All(getId())),
                integration -> new Integration(integration, this)
        );
        return integrations;
    }

    public Integration getIntegrationById(String integrationId) {
        return getIntegrations().stream().filter(integration -> integration.getId().equals(integrationId)).findFirst().orElse(null);
    }

    public Integration getIntegrationById(long integrationId) {
        return getIntegrationById(String.valueOf(integrationId));
    }

    public void deleteIntegration(String integrationId) {
        ApiClient.delete(Routes.Guild.Integration.Get(getId(), integrationId));
    }

    public void deleteIntegration(long integrationId) {
        deleteIntegration(String.valueOf(integrationId));
    }

    public GuildWidget getWidget() {
        widget = loader.load(widget, () -> new GuildWidget(JsonUtils.fetch(Routes.Guild.Widget.Get(getId())), this));
        return widget;
    }

    public GuildWidgetSettings getWidgetSettings() {
        widgetSettings = loader.load(widgetSettings, () -> new GuildWidgetSettings(JsonUtils.fetch(Routes.Guild.Widget.Settings(getId())), this));
        return widgetSettings;
    }

    public ModifyEvent<GuildWidgetSettings> modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, getId());
        return new ModifyEvent<>(getWidgetSettings());
    }

    public GuildWelcomeScreen getWelcomeScreen() {
        welcomeScreen = loader.loadIfExists(welcomeScreen, "welcome_screen",
                () -> new GuildWelcomeScreen(JsonUtils.fetch(Routes.Guild.WelcomeScreen(getId())), this));
        return welcomeScreen;
    }

    public ModifyEvent<GuildWelcomeScreen> modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, getId());
        return new ModifyEvent<>(getWelcomeScreen());
    }

    public GuildOnboarding getOnboarding() {
        onboarding = loader.load(onboarding, () -> new GuildOnboarding(JsonUtils.fetch(Routes.Guild.Onboarding(getId())), this));
        return onboarding;
    }

    public ModifyEvent<GuildOnboarding> modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, getId());
        return new ModifyEvent<>(getOnboarding());
    }

    public List<Webhook> getWebhooks() {
        webhooks = loader.loadEntityList(
                webhooks,
                JsonUtils.fetch(Routes.Guild.Webhooks(getId())),
                webhook -> new WebhookImpl(webhook, this)
        );
        return webhooks;
    }

    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findFirst().orElse(null);
    }

    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    public Webhook getWebhookByName(String webhookName) {
        return getWebhooks().stream().filter(webhook -> webhook.getName().equals(webhookName)).findFirst().orElse(null);
    }

    private JsonNode getBotGuild() {
        JsonNode botGuilds = JsonUtils.fetch(Routes.BotGuilds());
        return StreamSupport.stream(botGuilds.spliterator(), false)
                .filter(guildNode -> {
                    JsonNode idNode = guildNode.get("id");
                    return idNode != null && getId().equals(idNode.asText());
                })
                .findFirst()
                .orElse(null);
    }
}
