package org.dimas4ek.dawncord.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Nullable;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.*;
import org.dimas4ek.dawncord.command.SubCommand;
import org.dimas4ek.dawncord.command.SubCommandGroup;
import org.dimas4ek.dawncord.entities.channel.Channel;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.channel.GuildForum;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.entities.message.Message;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.types.AutoModTriggerType;
import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.InteractionCallbackType;
import org.dimas4ek.dawncord.types.MessageFlag;

import java.lang.reflect.InvocationTargetException;
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
        /*try {
            Method executeMethod = GuildModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        invokeSubmit(action, GuildModifyAction.class);
    }

    public static String createAutoModRule(Consumer<AutoModRuleCreateAction> handler, String guildId) {
        AutoModRuleCreateAction action = new AutoModRuleCreateAction(guildId);
        handler.accept(action);
        invokeSubmit(action, AutoModRuleCreateAction.class);
        return invokeGetId(action, AutoModRuleCreateAction.class);
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

    public static String createEmoji(Consumer<EmojiCreateAction> handler, String guildId) {
        EmojiCreateAction action = new EmojiCreateAction(guildId);
        handler.accept(action);
        invokeSubmit(action, EmojiCreateAction.class);
        return invokeGetId(action, EmojiCreateAction.class);
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

    public static String createGuildChannel(Consumer<GuildChannelCreateAction> handler, String guildId, ChannelType type) {
        GuildChannelCreateAction action = new GuildChannelCreateAction(guildId, type);
        handler.accept(action);
        invokeSubmit(action, GuildChannelCreateAction.class);
        return invokeGetId(action, GuildChannelCreateAction.class);
    }

    public static String createGuildRole(Consumer<GuildRoleCreateAction> handler, String guildId, boolean hasRoleIcons) {
        GuildRoleCreateAction action = new GuildRoleCreateAction(guildId, hasRoleIcons);
        handler.accept(action);
        invokeSubmit(action, GuildRoleCreateAction.class);
        return invokeGetId(action, GuildRoleCreateAction.class);
    }

    public static void modifyGuildRole(Consumer<GuildRoleModifyAction> handler, String guildId, String roleId, boolean hasRoleIcons) {
        GuildRoleModifyAction action = new GuildRoleModifyAction(guildId, roleId, hasRoleIcons);
        handler.accept(action);
        invokeSubmit(action, GuildRoleModifyAction.class);
    }

    public static String createSticker(Consumer<GuildStickerCreateAction> handler, Guild guild) {
        GuildStickerCreateAction action = new GuildStickerCreateAction(guild);
        handler.accept(action);
        invokeSubmit(action, GuildStickerCreateAction.class);
        return invokeGetId(action, GuildStickerCreateAction.class);
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

    public static String createPoll(Consumer<PollCreateAction> handler, String id) {
        PollCreateAction action = new PollCreateAction(id);
        handler.accept(action);
        invokeSubmit(action, PollCreateAction.class);
        return invokeGetId(action, PollCreateAction.class);
    }

    public static String createMessage(@Nullable Consumer<MessageCreateAction> handler, String message, String channelId, InteractionData interactionData) {
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
        return invokeGetId(action, MessageCreateAction.class);
    }

    public static void deferEdit(Consumer<MessageModifyAction> handler, InteractionData data, Message message) {
        MessageModifyAction action = new MessageModifyAction(message);
        handler.accept(action);
        invokeSubmit(action, MessageModifyAction.class);

        ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
        jsonObject.put("type", InteractionCallbackType.UPDATE_MESSAGE.getValue());
        ApiClient.post(jsonObject, Routes.Reply(data.getInteraction().getInteractionId(), data.getInteraction().getInteractionToken()));
    }

    public static void deferReply(Consumer<MessageCreateAction> handler, InteractionData data, boolean ephemeral) {
        if (handler == null) {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put("type", InteractionCallbackType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE.getValue());
            if (ephemeral) {
                jsonObject.put("flags", MessageFlag.EPHEMERAL.getValue());
            }
            ApiClient.post(jsonObject, Routes.Reply(data.getInteraction().getInteractionId(), data.getInteraction().getInteractionToken()));
        } else {
            MessageCreateAction action = new MessageCreateAction(data);
            handler.accept(action);
            try {
                Method executeMethod = MessageCreateAction.class.getDeclaredMethod("submitDefer");
                executeMethod.setAccessible(true);
                executeMethod.invoke(action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void replyModal(Consumer<ModalCreateAction> handler, InteractionData data) {
        ModalCreateAction action = new ModalCreateAction(data);
        handler.accept(action);
        invokeSubmit(action, ModalCreateAction.class);
    }

    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message, InteractionData data) {
        MessageModifyAction action = new MessageModifyAction(message, data);
        handler.accept(action);
        invokeSubmit(action, MessageModifyAction.class);
    }

    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message) {
        MessageModifyAction action = new MessageModifyAction(message);
        handler.accept(action);
        invokeSubmit(action, MessageModifyAction.class);
    }

    public static String startThread(@Nullable Consumer<ThreadCreateAction> handler, Message message, Channel channel) {
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
        return invokeGetId(action, ThreadCreateAction.class);
    }

    public static String startForumThread(Consumer<ThreadCreateAction> handler, GuildForum forum, String name) {
        ThreadCreateAction action = new ThreadCreateAction(forum, name);
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
        return invokeGetId(action, ThreadCreateAction.class);
    }

    public static String startThreadFromMessage(@Nullable Consumer<ThreadCreateAction> handler, Message message, String name) {
        ThreadCreateAction action = new ThreadCreateAction(message, name);
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
        return invokeGetId(action, ThreadCreateAction.class);
    }

    public static void modifyApplication(Consumer<ApplicationModifyAction> handler) {
        ApplicationModifyAction action = new ApplicationModifyAction();
        handler.accept(action);
        invokeSubmit(action, ApplicationModifyAction.class);
    }

    public static String createWebhook(Consumer<WebhookCreateAction> handler, String id) {
        WebhookCreateAction action = new WebhookCreateAction(id);
        handler.accept(action);
        invokeSubmit(action, WebhookCreateAction.class);
        return invokeGetId(action, WebhookCreateAction.class);
    }

    public static void modifyWebhook(Consumer<WebhookModifyAction> handler, String id) {
        WebhookModifyAction action = new WebhookModifyAction(id);
        handler.accept(action);
        invokeSubmit(action, WebhookModifyAction.class);
    }

    public static String createStage(Consumer<StageCreateAction> handler) {
        StageCreateAction action = new StageCreateAction();
        handler.accept(action);
        invokeSubmit(action, StageCreateAction.class);
        return invokeGetId(action, StageCreateAction.class);
    }

    public static void createSlashCommand(Consumer<SlashCommandCreateAction> handler, String name, String description) {
        SlashCommandCreateAction action = new SlashCommandCreateAction(name, description);
        handler.accept(action);
        invokeSubmit(action, SlashCommandCreateAction.class);
    }

    public static void modifySlashCommand(Consumer<SlashCommandModifyAction> handler, String id) {
        SlashCommandModifyAction action = new SlashCommandModifyAction(id);
        handler.accept(action);
        invokeSubmit(action, SlashCommandModifyAction.class);
    }

    public static void createSubCommand(Consumer<SubCommandCreateAction> handler, String name, String description, List<SubCommand> subCommandList) {
        SubCommandCreateAction action = new SubCommandCreateAction(name, description, subCommandList);
        handler.accept(action);
        invokeSubmit(action, SubCommandCreateAction.class);
    }

    public static void createSubCommandGroup(Consumer<SubCommandGroupCreateAction> handler, String name, String description, List<SubCommandGroup> subCommandGroupList) {
        SubCommandGroupCreateAction action = new SubCommandGroupCreateAction(name, description, subCommandGroupList);
        handler.accept(action);
        invokeSubmit(action, SubCommandGroupCreateAction.class);
    }

    private static String invokeGetId(Object action, Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod("getCreatedId");
            method.setAccessible(true);
            return (String) method.invoke(action);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeSubmit(Object action, Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod("submit");
            method.setAccessible(true);
            method.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
