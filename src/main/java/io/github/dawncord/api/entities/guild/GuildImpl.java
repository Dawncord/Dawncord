package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Constants;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.*;
import io.github.dawncord.api.entities.*;
import io.github.dawncord.api.entities.channel.*;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.channel.thread.ThreadImpl;
import io.github.dawncord.api.entities.guild.audit.AuditLog;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.entities.guild.automod.AutoModRuleImpl;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEventImpl;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.entities.guild.integration.IntegrationImpl;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.entities.guild.role.GuildRoleImpl;
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
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.MfaLevel;
import io.github.dawncord.api.types.VerificationLevel;
import io.github.dawncord.api.types.VoiceRegion;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementation of the {@link Guild} interface.
 * Represents a guild in a Discord server.
 */
public class GuildImpl implements Guild {
    private final JsonNode guild;
    private final JsonNode presences;

    /**
     * Constructs a new GuildImpl object with the specified JSON node representing the guild.
     *
     * @param guild The JSON node representing the guild.
     */
    public GuildImpl(JsonNode guild) {
        this.guild = guild;
        this.presences = guild.has("presences") ? guild.get("presences") : null;
    }

    @Override
    public String getId() {
        return guild.get("id").asText();
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return guild.get("name").asText();
    }

    @Override
    public String getDescription() {
        return guild.get("description").asText();
    }

    @Override
    public User getOwner() {
        return new UserImpl(JsonUtils.fetch(Routes.User(guild.get("owner_id").asText())));
    }

    @Override
    public GuildIcon getIcon() {
        return guild.hasNonNull("icon") ? new GuildIcon(getId(), guild.get("icon").asText()) : null;
    }

    @Override
    public Splash getSplash() {
        return guild.hasNonNull("splash") ? new Splash(getId(), guild.get("splash").asText()) : null;
    }

    @Override
    public DiscoverySplash getDiscoverySplash() {
        return guild.hasNonNull("discovery_splash") ? new DiscoverySplash(getId(), guild.get("discovery_splash").asText()) : null;
    }

    @Override
    public List<String> getFeatures() {
        List<String> features = new ArrayList<>();
        for (JsonNode feature : guild.get("features")) {
            features.add(feature.asText());
        }
        return features;
    }

    @Override
    public VerificationLevel getVerificationLevel() {
        return EnumUtils.getEnumObject(guild, "verification_level", VerificationLevel.class);
    }

    @Override
    public List<Sticker> getStickers() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Sticker.All(getId())), sticker -> new StickerImpl(sticker, this));
    }

    @Override
    public Sticker getStickerById(String stickerId) {
        return new StickerImpl(JsonUtils.fetch(Routes.Guild.Sticker.Get(getId(), stickerId)), this);
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
    public CreateEvent<Sticker> createSticker(Consumer<GuildStickerCreateAction> handler) {
        String id = ActionExecutor.createSticker(handler, this);
        return new CreateEvent<>(getStickerById(id));
    }

    @Override
    public ModifyEvent<Sticker> modifySticker(String stickerId, Consumer<GuildStickerModifyAction> handler) {
        ActionExecutor.modifyGuildSticker(handler, getId(), stickerId);
        return new ModifyEvent<>(getStickerById(stickerId));
    }

    @Override
    public List<GuildChannel> getChannels() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Channels(getId())), channel -> new GuildChannelImpl(channel, this));
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        JsonNode channelNode = JsonUtils.fetch(Routes.Channel.Get(channelId));
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
    public CreateEvent<GuildChannel> createChannel(ChannelType type, Consumer<GuildChannelCreateAction> handler) {
        String id = ActionExecutor.createGuildChannel(handler, getId(), type);
        return new CreateEvent<>(getChannelById(id));
    }

    @Override
    public ModifyEvent<GuildChannel> modifyChannel(String channelId, Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, getChannelById(channelId));
        return new ModifyEvent<>(getChannelById(channelId));
    }

    @Override
    public ModifyEvent<GuildChannel> modifyChannel(long channelId, Consumer<GuildChannelModifyAction> handler) {
        return modifyChannel(String.valueOf(channelId), handler);
    }

    @Override
    public ModifyEvent<GuildChannel> modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getId(), channelId);
        return new ModifyEvent<>(getChannelById(channelId));
    }

    @Override
    public ModifyEvent<GuildChannel> modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        return modifyChannelPosition(String.valueOf(channelId), handler);
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
        return new StageImpl(JsonUtils.fetch(Routes.StageInstance(channelId)), this);
    }

    @Override
    public Stage getStageByChannelId(long channelId) {
        return getStageByChannelId(String.valueOf(channelId));
    }

    @Override
    public CreateEvent<Stage> createStage(Consumer<StageCreateAction> handler) {
        String channelId = ActionExecutor.createStage(handler);
        return new CreateEvent<>(getStageByChannelId(channelId));
    }

    @Override
    public ModifyEvent<Stage> modifyStageByChannelId(String channelId, String topic) {
        ApiClient.patch(JsonNodeFactory.instance.objectNode().put("topic", topic), Routes.StageInstance(channelId));
        return new ModifyEvent<>(getStageByChannelId(channelId));
    }

    @Override
    public void deleteStage(String channelId) {
        ApiClient.delete(Routes.StageInstance(channelId));
    }

    @Override
    public List<GuildMember> getMembers() {
        return guild.has("members") ?
                JsonUtils.getEntityList(guild.get("members"), member -> new GuildMemberImpl(member, presences, this))
                :
                JsonUtils.getEntityList(
                        JsonUtils.fetchParams(
                                Routes.Guild.Member.All(getId()),
                                Map.of("limit", "100")
                        ),
                        member -> new GuildMemberImpl(member, presences, this)
                );
    }

    @Override
    public GuildMember getMemberById(String memberId) {
        return new GuildMemberImpl(JsonUtils.fetch(Routes.Guild.Member.Get(getId(), memberId)), presences, this);
    }

    @Override
    public GuildMember getMemberById(long memberId) {
        return getMemberById(String.valueOf(memberId));
    }

    @Override
    public List<GuildMember> searchMembers(String username, int limit) {
        return JsonUtils.getEntityList(
                JsonUtils.fetchParams(
                        Routes.Guild.Member.Search(getId()),
                        Map.of(
                                "query", username,
                                "limit", String.valueOf(limit)
                        )
                ),
                guildMember -> new GuildMemberImpl(guildMember, presences, this)
        );
    }

    @Override
    public List<GuildMember> searchMembers(String username) {
        return searchMembers(username, 1);
    }

    @Override
    public ModifyEvent<GuildMember> modifyMember(String memberId, Consumer<GuildMemberModifyAction> handler) {
        ActionExecutor.modifyGuildMember(handler, getId(), memberId);
        return new ModifyEvent<>(getMemberById(memberId));
    }

    @Override
    public ModifyEvent<GuildMember> modifyMember(long memberId, Consumer<GuildMemberModifyAction> handler) {
        return modifyMember(String.valueOf(memberId), handler);
    }

    @Override
    public ModifyEvent<GuildMember> modifyMe(Consumer<CurrentMemberModifyAction> handler) {
        ActionExecutor.execute(handler, CurrentMemberModifyAction.class, getId());
        return new ModifyEvent<>(getMemberById(Constants.BOT_ID));
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
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Ban.All(getId())), GuildBan::new);
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
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Role.All(getId())), role -> new GuildRoleImpl(role, this));
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
    public CreateEvent<GuildRole> createRole(Consumer<GuildRoleCreateAction> handler) {
        String id = ActionExecutor.createGuildRole(handler, getId(), getFeatures().contains("ROLE_ICONS"));
        return new CreateEvent<>(getRoleById(id));
    }

    @Override
    public ModifyEvent<GuildRole> modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, getId(), roleId, getFeatures().contains("ROLE_ICONS"));
        return new ModifyEvent<>(getRoleById(roleId));
    }

    @Override
    public ModifyEvent<GuildRole> modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler) {
        return modifyRole(String.valueOf(roleId), handler);
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
        List<GuildCategory> categories = new ArrayList<>();
        for (Channel channel : getChannels()) {
            if (channel.getType() == ChannelType.GUILD_CATEGORY) {
                categories.add(new GuildCategoryImpl(JsonUtils.fetch(Routes.Channel.Get(channel.getId())), this));
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
    public ModifyEvent<Guild> modify(Consumer<GuildModifyAction> handler) {
        ActionExecutor.modifyGuild(handler, getId(), getFeatures());
        return new ModifyEvent<>(new GuildImpl(JsonUtils.fetch(Routes.Guild.Get(getId()))));
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
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.ActiveThreads(getId())).get("threads"), thread -> new ThreadImpl(thread, this));
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
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.ScheduledEvent.All(getId())), event -> new GuildScheduledEventImpl(event, this));
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
    public ModifyEvent<GuildScheduledEvent> modifyGuildEvent(String eventId, Consumer<GuildEventModifyAction> handler) {
        ActionExecutor.modifyGuildEvent(handler, getId(), eventId);
        return new ModifyEvent<>(getGuildEventById(eventId));
    }

    @Override
    public ModifyEvent<GuildScheduledEvent> modifyGuildEvent(long eventId, Consumer<GuildEventModifyAction> handler) {
        return modifyGuildEvent(String.valueOf(eventId), handler);
    }

    @Override
    public AuditLog getAuditLog() {
        //todo fix audit log nulls
        return new AuditLog(JsonUtils.fetch(Routes.Guild.AuditLog(getId())), this);
    }

    @Override
    public List<CustomEmoji> getEmojis() {
        return JsonUtils.getEntityList(
                JsonUtils.fetch(Routes.Guild.Emoji.All(getId())),
                emoji -> new CustomEmojiImpl(emoji, this)
        );
    }

    @Override
    public CustomEmoji getEmojiById(String emojiId) {
        return new CustomEmojiImpl(JsonUtils.fetch(Routes.Guild.Emoji.Get(getId(), emojiId)), this);
    }

    @Override
    public CustomEmoji getEmojiById(long emojiId) {
        return getEmojiById(String.valueOf(emojiId));
    }

    @Override
    public List<CustomEmoji> getEmojisByName(String emojiName) {
        return getEmojis().stream().filter(emoji -> emoji.getName().equals(emojiName)).toList();
    }

    @Override
    public List<CustomEmoji> getEmojisByCreatorId(String userId) {
        return getEmojis().stream().filter(emoji -> emoji.getCreator().getId().equals(userId)).toList();
    }

    @Override
    public List<CustomEmoji> getEmojisByCreatorId(long userId) {
        return getEmojisByCreatorId(String.valueOf(userId));
    }

    @Override
    public CreateEvent<CustomEmoji> createEmoji(Consumer<EmojiCreateAction> handler) {
        String id = ActionExecutor.createEmoji(handler, getId());
        return new CreateEvent<>(getEmojiById(id));
    }

    @Override
    public ModifyEvent<CustomEmoji> modifyEmoji(String emojiId, Consumer<EmojiModifyAction> handler) {
        ActionExecutor.modifyEmoji(handler, getId(), emojiId);
        return new ModifyEvent<>(getEmojiById(emojiId));
    }

    @Override
    public ModifyEvent<CustomEmoji> modifyEmoji(long emojiId, Consumer<EmojiModifyAction> handler) {
        return modifyEmoji(String.valueOf(emojiId), handler);
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
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.AutoMod.All(getId())), rule -> new AutoModRuleImpl(rule, this));
    }

    @Override
    public AutoModRule getAutoModRuleById(String ruleId) {
        return new AutoModRuleImpl(JsonUtils.fetch(Routes.Guild.AutoMod.Get(getId(), ruleId)), this);
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
    public CreateEvent<AutoModRule> createAutoModRule(Consumer<AutoModRuleCreateAction> handler) {
        String id = ActionExecutor.createAutoModRule(handler, getId());
        return new CreateEvent<>(getAutoModRuleById(id));
    }

    @Override
    public ModifyEvent<AutoModRule> modifyAutoModRule(String ruleId, Consumer<AutoModRuleModifyAction> handler) {
        ActionExecutor.modifyAutoModRule(handler, getId(), getAutoModRuleById(ruleId).getTriggerType());
        return new ModifyEvent<>(getAutoModRuleById(ruleId));
    }

    @Override
    public ModifyEvent<AutoModRule> modifyAutoModRule(long ruleId, Consumer<AutoModRuleModifyAction> handler) {
        return modifyAutoModRule(String.valueOf(ruleId), handler);
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
        return new GuildPreview(JsonUtils.fetch(Routes.Guild.Preview(getId())), this);
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
        List<VoiceRegion> regions = new ArrayList<>();
        for (JsonNode region : JsonUtils.fetch(Routes.Guild.VoiceRegions(getId()))) {
            regions.add(EnumUtils.getEnumObject(region, "id", VoiceRegion.class));
        }
        return regions;
    }

    @Override
    public VoiceRegion getOptimalVoiceRegion() {
        for (JsonNode region : JsonUtils.fetch(Routes.Guild.VoiceRegions(getId()))) {
            if (region.get("optimal").asBoolean()) {
                return EnumUtils.getEnumObject(region, "id", VoiceRegion.class);
            }
        }
        return null;
    }

    @Override
    public List<Invite> getInvites() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Invites(getId())), invite -> new InviteImpl(invite, this));
    }

    @Override
    public Invite getInvite(String code) {
        return getInvites().stream().filter(invite -> invite.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Integration> getIntegrations() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Integration.All(getId())), integration -> new IntegrationImpl(integration, this));
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
        return new GuildWidget(JsonUtils.fetch(Routes.Guild.Widget.Get(getId())), this);
    }

    @Override
    public GuildWidgetSettings getWidgetSettings() {
        return new GuildWidgetSettings(JsonUtils.fetch(Routes.Guild.Widget.Settings(getId())), this);
    }

    @Override
    public ModifyEvent<GuildWidgetSettings> modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, getId());
        return new ModifyEvent<>(getWidgetSettings());
    }

    @Override
    public GuildWelcomeScreen getWelcomeScreen() {
        JsonNode welcomeScreenNode = JsonUtils.fetch(Routes.Guild.WelcomeScreen(getId()));
        return welcomeScreenNode != null ? new GuildWelcomeScreen(welcomeScreenNode, this) : null;
    }

    @Override
    public ModifyEvent<GuildWelcomeScreen> modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler) {
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, getId());
        return new ModifyEvent<>(getWelcomeScreen());
    }

    @Override
    public GuildOnboarding getOnboarding() {
        return new GuildOnboarding(JsonUtils.fetch(Routes.Guild.Onboarding(getId())), this);
    }

    @Override
    public ModifyEvent<GuildOnboarding> modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, getId());
        return new ModifyEvent<>(getOnboarding());
    }

    @Override
    public List<Webhook> getWebhooks() {
        return JsonUtils.getEntityList(JsonUtils.fetch(Routes.Guild.Webhooks(getId())), webhook -> new WebhookImpl(webhook, this));
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
