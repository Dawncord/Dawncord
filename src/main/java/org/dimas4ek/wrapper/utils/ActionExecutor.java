package org.dimas4ek.wrapper.utils;

import jakarta.annotation.Nullable;
import org.dimas4ek.wrapper.action.*;
import org.dimas4ek.wrapper.entities.channel.Channel;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildForum;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class ActionExecutor {

    public static <T> void execute(Consumer<T> handler, Class<T> actionClass, String guildId) {
        try {
            T action = actionClass.getDeclaredConstructor(String.class).newInstance(guildId);
            handler.accept(action);

            Method executeMethod = actionClass.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyAutoModRule(Consumer<AutoModRuleModifyAction> handler, String guildId, AutoModTriggerType triggerType) {
        AutoModRuleModifyAction action = new AutoModRuleModifyAction(guildId, triggerType);
        handler.accept(action);
        try {
            Method executeMethod = AutoModRuleModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyChannel(Consumer<GuildChannelModifyAction> handler, GuildChannel channel) {
        GuildChannelModifyAction action = new GuildChannelModifyAction(channel);
        handler.accept(action);
        try {
            Method executeMethod = GuildChannelModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyChannelPosition(Consumer<GuildChannelPositionModifyAction> handler, String guildId, String channelId) {
        GuildChannelPositionModifyAction action = new GuildChannelPositionModifyAction(guildId, channelId);
        handler.accept(action);
        try {
            Method executeMethod = GuildChannelPositionModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyEmoji(Consumer<EmojiModifyAction> handler, String guildId, String emojiId) {
        EmojiModifyAction action = new EmojiModifyAction(guildId, emojiId);
        handler.accept(action);
        try {
            Method executeMethod = EmojiModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyGuildEvent(Consumer<GuildEventModifyAction> handler, String guildId, String eventId) {
        GuildEventModifyAction action = new GuildEventModifyAction(guildId, eventId);
        handler.accept(action);
        try {
            Method executeMethod = GuildEventModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyGuildMember(Consumer<GuildMemberModifyAction> handler, String guildId, String memberId) {
        GuildMemberModifyAction action = new GuildMemberModifyAction(guildId, memberId);
        handler.accept(action);
        try {
            Method executeMethod = GuildMemberModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyGuildRole(Consumer<GuildRoleModifyAction> handler, String guildId, String roleId) {
        GuildRoleModifyAction action = new GuildRoleModifyAction(guildId, roleId);
        handler.accept(action);
        try {
            Method executeMethod = GuildRoleModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createChannelInvite(Consumer<InviteCreateAction> handler, GuildChannel channel) {
        InviteCreateAction action = new InviteCreateAction(channel);
        handler.accept(action);
        try {
            Method executeMethod = InviteCreateAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject createMessage(@Nullable Consumer<MessageCreateAction> handler, String message, String channelId, InteractionData interactionData) {
        MessageCreateAction action;
        if (interactionData != null) {
            action = new MessageCreateAction(message, channelId, interactionData);
        } else {
            action = new MessageCreateAction(message, channelId);
        }
        if (handler != null) {
            handler.accept(action);
        }
        try {
            Method executeMethod = MessageCreateAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            return (JSONObject) executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message) {
        MessageModifyAction action = new MessageModifyAction(message);
        handler.accept(action);
        try {
            Method executeMethod = MessageModifyAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            Method executeMethod = ThreadCreateAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startForumThread(Consumer<ThreadCreateAction> handler, GuildForum forum, String name) {
        ThreadCreateAction action = new ThreadCreateAction(forum, name);
        if (handler != null) {
            handler.accept(action);
        }
        try {
            Method executeMethod = ThreadCreateAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startThreadFromMessage(@Nullable Consumer<ThreadCreateAction> handler, Message message, String name) {
        ThreadCreateAction action = new ThreadCreateAction(message, name);
        if (handler != null) {
            handler.accept(action);
        }
        try {
            Method executeMethod = ThreadCreateAction.class.getDeclaredMethod("submit");
            executeMethod.setAccessible(true);
            executeMethod.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
