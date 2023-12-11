package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.channel.Channel;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildForum;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.types.ChannelType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

public class ActionExecutor {
    public static <T> void execute(Consumer<T> handler, Class<T> actionClass, String guildId) {
        T action = null;
        try {
            action = actionClass.getDeclaredConstructor(String.class).newInstance(guildId);
            handler.accept(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        invokeSubmit(action, actionClass);
    }

    public static void modifyGuild(Consumer<GuildModifyAction> handler, String guildId, List<String> guildFeatures) {
        GuildModifyAction action = new GuildModifyAction(guildId, guildFeatures);
        handler.accept(action);
        try {
            Method executeMethod = GuildModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //invokeSubmit(action, GuildModifyAction.class);
    }

    public static void modifyAutoModRule(Consumer<AutoModRuleModifyAction> handler, String guildId, AutoModTriggerType triggerType) {
        AutoModRuleModifyAction action = new AutoModRuleModifyAction(guildId, triggerType);
        handler.accept(action);
        invokeSubmit(action, AutoModRuleModifyAction.class);
    }

    public static void modifyChannel(Consumer<GuildChannelModifyAction> handler, GuildChannel channel) {
        GuildChannelModifyAction action = new GuildChannelModifyAction(channel);
        handler.accept(action);
        invokeSubmit(action, GuildChannelModifyAction.class);
    }

    public static void modifyChannelPosition(Consumer<GuildChannelPositionModifyAction> handler, String guildId, String channelId) {
        GuildChannelPositionModifyAction action = new GuildChannelPositionModifyAction(guildId, channelId);
        handler.accept(action);
        invokeSubmit(action, GuildChannelPositionModifyAction.class);
    }

    public static void modifyEmoji(Consumer<EmojiModifyAction> handler, String guildId, String emojiId) {
        EmojiModifyAction action = new EmojiModifyAction(guildId, emojiId);
        handler.accept(action);
        invokeSubmit(action, EmojiModifyAction.class);
    }

    public static void modifyGuildEvent(Consumer<GuildEventModifyAction> handler, String guildId, String eventId) {
        GuildEventModifyAction action = new GuildEventModifyAction(guildId, eventId);
        handler.accept(action);
        invokeSubmit(action, GuildEventModifyAction.class);
    }

    public static void modifyGuildMember(Consumer<GuildMemberModifyAction> handler, String guildId, String memberId) {
        GuildMemberModifyAction action = new GuildMemberModifyAction(guildId, memberId);
        handler.accept(action);
        invokeSubmit(action, GuildMemberModifyAction.class);
    }

    public static void createGuildChannel(Consumer<GuildChannelCreateAction> handler, String guildId, ChannelType type) {
        GuildChannelCreateAction action = new GuildChannelCreateAction(guildId, type);
        handler.accept(action);
        invokeSubmit(action, GuildChannelCreateAction.class);
    }

    public static void createGuildRole(Consumer<GuildRoleCreateAction> handler, String guildId, boolean hasRoleIcons) {
        GuildRoleCreateAction action = new GuildRoleCreateAction(guildId, hasRoleIcons);
        handler.accept(action);
        invokeSubmit(action, GuildRoleCreateAction.class);
    }

    public static void modifyGuildRole(Consumer<GuildRoleModifyAction> handler, String guildId, String roleId, boolean hasRoleIcons) {
        GuildRoleModifyAction action = new GuildRoleModifyAction(guildId, roleId, hasRoleIcons);
        handler.accept(action);
        invokeSubmit(action, GuildRoleModifyAction.class);
    }

    public static void modifyGuildSticker(Consumer<GuildStickerModifyAction> handler, String guildId, String stickerId) {
        GuildStickerModifyAction action = new GuildStickerModifyAction(guildId, stickerId);
        handler.accept(action);
        invokeSubmit(action, GuildStickerModifyAction.class);
    }

    public static void createChannelInvite(Consumer<InviteCreateAction> handler, GuildChannel channel) {
        InviteCreateAction action = new InviteCreateAction(channel);
        handler.accept(action);
        invokeSubmit(action, InviteCreateAction.class);
    }

    public static JsonNode createMessage(@Nullable Consumer<MessageCreateAction> handler, String message, String channelId, InteractionData interactionData) {
        MessageCreateAction action;
        if (interactionData != null) {
            action = new MessageCreateAction(message, channelId, interactionData);
        } else {
            action = new MessageCreateAction(message, channelId);
        }
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, MessageCreateAction.class);
        return null;
    }

    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message) {
        MessageModifyAction action = new MessageModifyAction(message);
        handler.accept(action);
        invokeSubmit(action, MessageModifyAction.class);
    }

    public static void startThread(@Nullable Consumer<ThreadCreateAction> handler, Message message, Channel channel) {
        ThreadCreateAction action;
        if (message == null) {
            action = new ThreadCreateAction(channel);
        } else {
            action = new ThreadCreateAction(message, channel);
        }
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
    }

    public static void startForumThread(Consumer<ThreadCreateAction> handler, GuildForum forum, String name) {
        ThreadCreateAction action = new ThreadCreateAction(forum, name);
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
    }

    public static void startThreadFromMessage(@Nullable Consumer<ThreadCreateAction> handler, Message message, String name) {
        ThreadCreateAction action = new ThreadCreateAction(message, name);
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
    }

    public static void modifyApplication(Consumer<ApplicationModifyAction> handler) {
        ApplicationModifyAction action = new ApplicationModifyAction();
        handler.accept(action);
        invokeSubmit(action, ApplicationModifyAction.class);
    }

    public static void createWebhook(Consumer<WebhookCreateAction> handler, String id) {
        WebhookCreateAction action = new WebhookCreateAction(id);
        handler.accept(action);
        invokeSubmit(action, WebhookCreateAction.class);
    }

    public static void modifyWebhook(Consumer<WebhookModifyAction> handler, String id) {
        WebhookModifyAction action = new WebhookModifyAction(id);
        handler.accept(action);
        invokeSubmit(action, WebhookModifyAction.class);
    }

    public static void createStage(Consumer<StageCreateAction> handler) {
        StageCreateAction action = new StageCreateAction();
        handler.accept(action);
        invokeSubmit(action, StageCreateAction.class);
    }

    private static void invokeSubmit(Object action, Class<?> clazz) {
        try {
            Method executeMethod = clazz.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
