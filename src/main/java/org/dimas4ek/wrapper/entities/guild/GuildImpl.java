package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.EmojiImpl;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.*;
import org.dimas4ek.wrapper.entities.guild.audit.AuditLog;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRuleImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildEventImpl;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.integration.IntegrationImpl;
import org.dimas4ek.wrapper.entities.guild.welcomescreen.GuildWelcomeScreen;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidget;
import org.dimas4ek.wrapper.entities.guild.widget.GuildWidgetSettings;
import org.dimas4ek.wrapper.entities.image.DiscoverySplash;
import org.dimas4ek.wrapper.entities.image.Splash;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.message.sticker.StickerImpl;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.role.GuildRoleImpl;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.ChannelType;
import org.dimas4ek.wrapper.types.GuildFeature;
import org.dimas4ek.wrapper.types.MfaLevel;
import org.dimas4ek.wrapper.types.VoiceRegion;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GuildImpl implements Guild {
    private final JSONObject guild;

    public GuildImpl(JSONObject guild) {
        this.guild = guild;
    }

    @Override
    public String getId() {
        return guild.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getName() {
        return guild.getString("name");
    }

    @Override
    public String getDescription() {
        return guild.getString("description");
    }

    @Override
    public User getOwner() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + guild.getString("owner_id")));
    }

    @Override
    public Splash getSplash() {
        String splash = guild.optString("splash", null);
        return splash != null ? new Splash(getId(), splash) : null;
    }

    @Override
    public DiscoverySplash getDiscoverySplash() {
        String splash = guild.optString("discovery_splash", null);
        return splash != null ? new DiscoverySplash(getId(), splash) : null;
    }

    @Override
    public List<GuildFeature> getFeatures() {
        return EnumUtils.getEnumList(guild.getJSONArray("features"), GuildFeature.class);
    }

    @Override
    public List<Sticker> getStickers() {
        return JsonUtils.getEntityList(guild.getJSONArray("stickers"), StickerImpl::new);
    }

    @Override
    public Sticker getStickerById(String stickerId) {
        return getStickers().stream().filter(sticker -> sticker.getId().equals(stickerId)).findFirst().orElse(null);
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
    public List<GuildChannel> getChannels() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/channels"), GuildChannelImpl::new);
    }

    @Override
    public GuildChannel getChannelById(String channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public GuildChannel getChannelById(long channelId) {
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + channelId));
    }

    @Override
    public List<GuildChannel> getChannelsByName(String channelName) {
        return getChannels().stream().filter(channel -> channel.getName().equals(channelName)).toList();
    }

    @Override
    public void modifyChannel(String id, Consumer<GuildChannelModifyAction> handler) {
        ActionExecutor.modifyChannel(handler, getChannelById(id));
    }

    @Override
    public void modifyChannel(long id, Consumer<GuildChannelModifyAction> handler) {
        modifyChannel(String.valueOf(id), handler);
    }

    @Override
    public void modifyChannelPosition(String channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        ActionExecutor.modifyChannelPosition(handler, getId(), getChannelById(channelId).getId());
    }

    @Override
    public void modifyChannelPosition(long channelId, Consumer<GuildChannelPositionModifyAction> handler) {
        modifyChannelPosition(String.valueOf(channelId), handler);
    }

    @Override
    public List<GuildMember> getMembers() {
        JSONArray members = ApiClient.getJsonArrayParams(
                "/guilds/" + getId() + "/members",
                Map.of("limit", "100")
        );
        return JsonUtils.getEntityList(members, guildMember -> new GuildMemberImpl(guild, guildMember));
    }

    @Override
    public GuildMember getMemberById(String memberId) {
        return new GuildMemberImpl(guild, JsonUtils.fetchEntity("/guilds/" + getId() + "/members/" + memberId));
    }

    @Override
    public GuildMember getMemberById(long memberId) {
        return getMemberById(String.valueOf(memberId));
    }

    @Override
    public List<GuildMember> searchMembers(String username, int limit) {
        JSONArray members = ApiClient.getJsonArrayParams(
                "/guilds/" + getId() + "/members/search",
                Map.of(
                        "query", username,
                        "limit", String.valueOf(limit)
                )
        );

        return JsonUtils.getEntityList(members, guildMember -> new GuildMemberImpl(guild, guildMember));
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
    public void kickMember(String memberId) {
        ApiClient.delete("/guilds/" + getId() + "/members/" + memberId);
    }

    @Override
    public void kickMember(long memberId) {
        kickMember(String.valueOf(memberId));
    }

    @Override
    public void banMember(String memberId) {
        ApiClient.put(null, "/guilds/" + getId() + "/bans/" + memberId);
    }

    @Override
    public void banMember(long memberId) {
        banMember(String.valueOf(memberId));
    }

    @Override
    public void unbanMember(String memberId) {
        ApiClient.delete("/guilds/" + getId() + "/bans/" + memberId);
    }

    @Override
    public void unbanMember(long memberId) {
        unbanMember(String.valueOf(memberId));
    }

    @Override
    public List<GuildBan> getBans() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/bans"), GuildBan::new);
    }

    @Override
    public GuildBan getBanByUserId(String userId) {
        return new GuildBan(JsonUtils.fetchEntity("/guilds/" + getId() + "/bans/" + userId));
    }

    @Override
    public GuildBan getBanByUserId(long userId) {
        return getBanByUserId(String.valueOf(userId));
    }

    @Override
    public List<GuildRole> getRoles() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/roles"), (JSONObject t) -> new GuildRoleImpl(this, t));
    }

    @Override
    public GuildRole getRoleById(String roleId) {
        return getRoles().stream().filter(role -> role.getId().equals(roleId)).findAny().orElse(null);
    }

    @Override
    public GuildRole getRoleById(long roleId) {
        return getRoleById(String.valueOf(roleId));
    }

    @Override
    public GuildRole getPublicRole() {
        return getRoleById(getId());
    }

    @Override
    public void createRole(Consumer<GuildRoleCreateAction> handler) {
        ActionExecutor.execute(handler, GuildRoleCreateAction.class, getId());
    }

    @Override
    public void modifyRole(String roleId, Consumer<GuildRoleModifyAction> handler) {
        ActionExecutor.modifyGuildRole(handler, getId(), roleId);
    }

    @Override
    public void modifyRole(long roleId, Consumer<GuildRoleModifyAction> handler) {
        modifyRole(String.valueOf(roleId), handler);
    }

    @Override
    public void deleteRole(String roleId) {
        ApiClient.delete("/guilds/" + getId() + "/roles/" + roleId);
    }

    @Override
    public void deleteRole(long roleId) {
        deleteRole(String.valueOf(roleId));
    }

    @Override
    public List<GuildRole> getRolesByName(String roleName) {
        return getRoles().stream().filter(role -> role.getName().equals(roleName)).toList();
    }

    @Override
    public List<GuildCategory> getCategories() {
        List<GuildCategory> categories = new ArrayList<>();
        for (Channel channel : getChannels()) {
            if (channel.getType() == ChannelType.GUILD_CATEGORY) {
                categories.add(new GuildCategoryImpl(JsonUtils.fetchEntity("/channels/" + channel.getId())));
            }
        }
        return categories;
    }

    @Override
    public GuildCategory getCategoryById(String categoryId) {
        return getCategories().stream().filter(category -> category.getId().equals(categoryId)).findAny().orElse(null);
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
        ActionExecutor.execute(handler, GuildModifyAction.class, getId());
    }

    @Override
    public void delete() {
        ApiClient.delete("/guilds/" + getId());
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
        return JsonUtils.getEntityList(JsonUtils.fetchEntity("/guilds/" + getId() + "/threads/active").getJSONArray("threads"), ThreadImpl::new);
    }

    @Override
    public List<Thread> getPublicArchiveThreads(String channelId) {
        //todo add before and limit
        return getChannelById(channelId).getPublicArchiveThreads();
    }

    @Override
    public List<Thread> getPublicArchiveThreads(long channelId) {
        return getPublicArchiveThreads(String.valueOf(channelId));
    }

    @Override
    public List<Thread> getPrivateArchiveThreads(String channelId) {
        //todo add before and limit
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
        return getActiveThreads().stream().filter(thread -> thread.getId().equals(threadId)).findAny().orElse(null);
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
    public List<GuildEvent> getGuildEvents() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/scheduled-events"), GuildEventImpl::new);
    }

    @Override
    public GuildEvent getGuildEventById(String eventId) {
        return new GuildEventImpl(JsonUtils.fetchEntity("/guilds/" + getId() + "/scheduled-events" + eventId));
    }

    @Override
    public GuildEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    @Override
    public List<GuildEvent> getGuildEventsByName(String eventName) {
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
        return new AuditLog(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/audit-logs"));
    }

    @Override
    public List<Emoji> getEmojis() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/emojis"), (JSONObject t) -> new EmojiImpl(this, t));
    }

    @Override
    public Emoji getEmojiById(String emojiId) {
        return new EmojiImpl(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/emojis/" + emojiId));
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
        ApiClient.delete("/guilds/" + getId() + "/emojis/" + emojiId);
    }

    @Override
    public void deleteEmoji(long emojiId) {
        deleteEmoji(String.valueOf(emojiId));
    }

    @Override
    public List<AutoModRule> getAutoModRules() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/auto-moderation/rules"), AutoModRuleImpl::new);
    }

    @Override
    public AutoModRule getAutoModRuleById(String ruleId) {
        return new AutoModRuleImpl(JsonUtils.fetchEntity("/guilds/" + getId() + "/auto-moderation/rules/" + ruleId));
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
        ApiClient.delete("/guilds/" + getId() + "/auto-moderation/rules/" + ruleId);
    }

    @Override
    public void deleteAutoModRule(long ruleId) {
        deleteAutoModRule(String.valueOf(ruleId));
    }

    @Override
    public GuildPreview getPreview() {
        return new GuildPreview(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/preview"));
    }

    @Override
    public void setMfaLevel(MfaLevel mfaLevel) {
        ApiClient.post(
                new JSONObject()
                        .put("level", mfaLevel.getValue()),
                "/guilds/" + getId() + "/mfa"
        );
    }

    @Override
    public List<VoiceRegion> getVoiceRegions() {
        List<VoiceRegion> regions = new ArrayList<>();
        JSONArray regionsArray = JsonUtils.fetchArray("/guilds/" + getId() + "/regions");
        for (int i = 0; i < regionsArray.length(); i++) {
            regions.add(EnumUtils.getEnumObject(regionsArray.getJSONObject(i), "id", VoiceRegion.class));
        }
        return regions;
    }

    @Override
    public VoiceRegion getOptimalVoiceRegion() {
        JSONArray regionsArray = JsonUtils.fetchArray("/guilds/" + getId() + "/regions");
        for (int i = 0; i < regionsArray.length(); i++) {
            if (regionsArray.getJSONObject(i).getBoolean("optimal")) {
                return EnumUtils.getEnumObject(regionsArray.getJSONObject(i), "id", VoiceRegion.class);
            }
        }
        return null;
    }

    @Override
    public List<Invite> getInvites() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/invites"), InviteImpl::new);
    }

    @Override
    public List<Integration> getIntegrations() {
        return JsonUtils.getEntityList(JsonUtils.fetchArray("/guilds/" + getId() + "/integrations"), (JSONObject t) -> new IntegrationImpl(this, t));
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
        ApiClient.delete("/guilds/" + getId() + "/integrations/" + integrationId);
    }

    @Override
    public void deleteIntegration(long integrationId) {
        deleteIntegration(String.valueOf(integrationId));
    }

    @Override
    public GuildWidget getWidget() {
        //todo check
        return new GuildWidget(JsonUtils.fetchEntity("/guilds/" + getId() + "/widget.json"));
    }

    @Override
    public GuildWidgetSettings getWidgetSettings() {
        //todo check
        return new GuildWidgetSettings(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/widget"));
    }

    @Override
    public void modifyWidgetSettings(Consumer<GuildWidgetSettingsModifyAction> handler) {
        //todo check
        ActionExecutor.execute(handler, GuildWidgetSettingsModifyAction.class, getId());
    }

    @Override
    public GuildWelcomeScreen getWelcomeScreen() {
        //todo check
        return new GuildWelcomeScreen(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/welcome-screen"));
    }

    @Override
    public void modifyWelcomeScreen(Consumer<GuildWelcomeScreenModifyAction> handler) {
        //todo check
        ActionExecutor.execute(handler, GuildWelcomeScreenModifyAction.class, getId());
    }

    @Override
    public GuildOnboarding getOnboarding() {
        //todo check
        return new GuildOnboarding(this, JsonUtils.fetchEntity("/guilds/" + getId() + "/onboarding"));
    }

    @Override
    public void modifyOnboarding(Consumer<GuildOnboardingModifyAction> handler) {
        //todo check
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, getId());
    }

    @Override
    public void modifyMe(Consumer<CurrentMemberModifyAction> handler) {
        ActionExecutor.execute(handler, CurrentMemberModifyAction.class, getId());
    }
}
