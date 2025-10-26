package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.*;
import io.github.dawncord.api.action.automoderule.AutoModRuleCreateAction;
import io.github.dawncord.api.action.automoderule.AutoModRuleModifyAction;
import io.github.dawncord.api.action.emoji.EmojiCreateAction;
import io.github.dawncord.api.action.emoji.EmojiModifyAction;
import io.github.dawncord.api.action.guild.GuildAction;
import io.github.dawncord.api.action.guild.GuildCreateAction;
import io.github.dawncord.api.action.guild.GuildModifyAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelCreateAction;
import io.github.dawncord.api.action.guildchannel.GuildChannelModifyAction;
import io.github.dawncord.api.action.guildrole.GuildRoleCreateAction;
import io.github.dawncord.api.action.guildrole.GuildRoleModifyAction;
import io.github.dawncord.api.action.guildsticker.GuildStickerCreateAction;
import io.github.dawncord.api.action.guildsticker.GuildStickerModifyAction;
import io.github.dawncord.api.action.message.MessageCreateAction;
import io.github.dawncord.api.action.message.MessageModifyAction;
import io.github.dawncord.api.action.command.slashcommand.SlashCommandCreateAction;
import io.github.dawncord.api.action.command.slashcommand.SlashCommandModifyAction;
import io.github.dawncord.api.action.command.subcommand.SubCommandCreateAction;
import io.github.dawncord.api.action.command.subcommand.SubCommandGroupCreateAction;
import io.github.dawncord.api.action.webhook.WebhookCreateAction;
import io.github.dawncord.api.action.webhook.WebhookModifyAction;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.entities.channel.Channel;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.channel.GuildForum;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.modal.Element;
import io.github.dawncord.api.entities.message.modal.Modal;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.InteractionCallbackType;
import io.github.dawncord.api.types.MessageFlag;
import jakarta.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

/**
 * Executes various actions related to guild management and moderation.
 */
public class ActionExecutor {

    /**
     * Executes the specified action handler with the given action class and guild ID.
     *
     * @param <T>         The type of action.
     * @param handler     The action handler.
     * @param actionClass The action class.
     * @param guildId     The ID of the guild.
     */
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

    private static <T extends Action<?>> String execute(T action, Consumer<T> handler, boolean returnId) {
        handler.accept(action);
        invokeSubmit(action, Action.class);
        try {
            return returnId ? invokeGetId(action, action.getClass()) : null;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a guild using the specified handler.
     *
     * @param handler the handler that modifies the GuildCreateAction object
     * @return the ID of the created guild
     */
    public static String createGuild(Consumer<GuildCreateAction> handler) {
        GuildCreateAction action = new GuildCreateAction();
        return execute(action, handler, true);
    }

    /**
     * Modifies a guild using the specified handler, guild ID, and guild features.
     *
     * @param handler       The guild modification handler.
     * @param guildId       The ID of the guild.
     * @param guildFeatures The features of the guild.
     */
    public static void modifyGuild(Consumer<GuildModifyAction> handler, String guildId, List<String> guildFeatures) {
        GuildModifyAction action = new GuildModifyAction(guildId, guildFeatures);
        execute(action, handler, false);
    }

    /**
     * Creates an auto-mod rule using the specified handler and guild ID.
     *
     * @param handler The auto-mod rule creation handler.
     * @param guildId The ID of the guild.
     * @return The ID of the created auto-mod rule.
     */
    public static String createAutoModRule(Consumer<AutoModRuleCreateAction> handler, String guildId) {
        AutoModRuleCreateAction action = new AutoModRuleCreateAction(guildId);
        return execute(action, handler, true);
    }

    /**
     * Modifies an auto-mod rule using the specified handler, guild ID, and trigger type.
     *
     * @param handler     The auto-mod rule modification handler.
     * @param guildId     The ID of the guild.
     * @param triggerType The trigger type of the auto-mod rule.
     */
    public static void modifyAutoModRule(Consumer<AutoModRuleModifyAction> handler, String guildId, AutoModTriggerType triggerType) {
        AutoModRuleModifyAction action = new AutoModRuleModifyAction(guildId, triggerType);
        execute(action, handler, false);
    }

    /**
     * Modifies a guild channel using the specified handler and channel.
     *
     * @param handler The channel modification handler.
     * @param channel The guild channel to modify.
     */
    public static void modifyChannel(Consumer<GuildChannelModifyAction> handler, GuildChannel channel) {
        GuildChannelModifyAction action = new GuildChannelModifyAction(channel.getType(), channel.getId());
        execute(action, handler, false);
    }

    /**
     * Modifies the position of a guild channel using the specified handler, guild ID, and channel ID.
     *
     * @param handler   The channel position modification handler.
     * @param guildId   The ID of the guild.
     * @param channelId The ID of the channel.
     */
    public static void modifyChannelPosition(Consumer<GuildChannelPositionModifyAction> handler, String guildId, String channelId) {
        GuildChannelPositionModifyAction action = new GuildChannelPositionModifyAction(guildId, channelId);
        execute(action, handler, false);
    }

    /**
     * Creates an emoji using the specified handler and guild ID.
     *
     * @param handler The emoji creation handler.
     * @param guildId The ID of the guild.
     * @return The ID of the created emoji.
     */
    public static String createEmoji(Consumer<EmojiCreateAction> handler, String guildId) {
        EmojiCreateAction action = new EmojiCreateAction(guildId);
        return execute(action, handler, true);
    }

    /**
     * Modifies an emoji using the specified handler, guild ID, and emoji ID.
     *
     * @param handler The emoji modification handler.
     * @param guildId The ID of the guild.
     * @param emojiId The ID of the emoji.
     */
    public static void modifyEmoji(Consumer<EmojiModifyAction> handler, String guildId, String emojiId) {
        EmojiModifyAction action = new EmojiModifyAction(guildId, emojiId);
        execute(action, handler, false);
    }

    /**
     * Modifies a guild event using the specified handler, guild ID, and event ID.
     *
     * @param handler The guild event modification handler.
     * @param guildId The ID of the guild.
     * @param eventId The ID of the event.
     */
    public static void modifyGuildEvent(Consumer<GuildEventModifyAction> handler, String guildId, String eventId) {
        GuildEventModifyAction action = new GuildEventModifyAction(guildId, eventId);
        execute(action, handler, false);
    }

    /**
     * Modifies a guild member using the specified handler, guild ID, and member ID.
     *
     * @param handler  The guild member modification handler.
     * @param guildId  The ID of the guild.
     * @param memberId The ID of the member.
     */
    public static void modifyGuildMember(Consumer<GuildMemberModifyAction> handler, String guildId, String memberId) {
        GuildMemberModifyAction action = new GuildMemberModifyAction(guildId, memberId);
        execute(action, handler, false);
    }

    /**
     * Creates a guild channel using the specified handler, guild ID, and channel type.
     *
     * @param handler The guild channel creation handler.
     * @param guildId The ID of the guild.
     * @param type    The type of the channel.
     * @return The ID of the created guild channel.
     */
    public static String createGuildChannel(Consumer<GuildChannelCreateAction> handler, String guildId, ChannelType type) {
        GuildChannelCreateAction action = new GuildChannelCreateAction(guildId, type);
        return execute(action, handler, true);
    }

    /**
     * Creates a guild role using the specified handler, guild ID, and role icon availability.
     *
     * @param handler      The guild role creation handler.
     * @param guildId      The ID of the guild.
     * @param hasRoleIcons Indicates if the role has icons.
     * @return The ID of the created guild role.
     */
    public static String createGuildRole(Consumer<GuildRoleCreateAction> handler, String guildId, boolean hasRoleIcons) {
        GuildRoleCreateAction action = new GuildRoleCreateAction(guildId, hasRoleIcons);
        return execute(action, handler, true);
    }

    /**
     * Modifies a guild role using the specified handler, guild ID, role ID, and role icon availability.
     *
     * @param handler      The guild role modification handler.
     * @param guildId      The ID of the guild.
     * @param roleId       The ID of the role.
     * @param hasRoleIcons Indicates if the role has icons.
     */
    public static void modifyGuildRole(Consumer<GuildRoleModifyAction> handler, String guildId, String roleId, boolean hasRoleIcons) {
        GuildRoleModifyAction action = new GuildRoleModifyAction(guildId, roleId, hasRoleIcons);
        execute(action, handler, false);
    }

    /**
     * Creates a sticker using the specified handler and guild.
     *
     * @param handler The sticker creation handler.
     * @param guild   The guild where the sticker will be created.
     * @return The ID of the created sticker.
     */
    public static String createSticker(Consumer<GuildStickerCreateAction> handler, Guild guild) {
        GuildStickerCreateAction action = new GuildStickerCreateAction(guild);
        return execute(action, handler, true);
    }

    /**
     * Modifies a guild sticker using the specified handler, guild ID, and sticker ID.
     *
     * @param handler   The guild sticker modification handler.
     * @param guildId   The ID of the guild.
     * @param stickerId The ID of the sticker.
     */
    public static void modifyGuildSticker(Consumer<GuildStickerModifyAction> handler, String guildId, String stickerId) {
        GuildStickerModifyAction action = new GuildStickerModifyAction(guildId, stickerId);
        execute(action, handler, false);
    }

    /**
     * Creates a channel invite using the specified handler and channel.
     *
     * @param handler The channel invite creation handler.
     * @param channel The channel where the invite will be created.
     */
    public static void createChannelInvite(Consumer<InviteCreateAction> handler, GuildChannel channel) {
        InviteCreateAction action = new InviteCreateAction(channel.getId(), channel.getType());
        execute(action, handler, false);
    }

    /**
     * Creates a poll using the specified handler and ID.
     *
     * @param handler The poll creation handler.
     * @param id      The ID of the poll.
     * @return The ID of the created poll.
     */
    public static String createPoll(Consumer<PollCreateAction> handler, String id) {
        PollCreateAction action = new PollCreateAction(id);
        return execute(action, handler, true);
    }

    /**
     * Creates a message using the specified handler, message content, channel ID, and interaction data.
     *
     * @param handler         The message creation handler.
     * @param message         The content of the message.
     * @param channelId       The ID of the channel where the message will be sent.
     * @param interactionData The interaction data associated with the message (can be null).
     * @return The ID of the created message.
     */
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
        try {
            return invokeGetId(action, MessageCreateAction.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Defers editing a message using the specified handler, interaction data, and message.
     *
     * @param handler The message modification handler.
     * @param data    The interaction data associated with the message.
     * @param message The message to be edited.
     */
    public static void deferEdit(Consumer<MessageModifyAction> handler, InteractionData data, Message message) {
        modifyMessage(handler, message);

        ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
        jsonObject.put("type", InteractionCallbackType.UPDATE_MESSAGE.getValue());
        ApiClient.post(jsonObject, Routes.Reply(data.getInteraction().getInteractionId(), data.getInteraction().getInteractionToken()));
    }

    /**
     * Defers replying to an interaction with a message using the specified handler, interaction data, and ephemeral flag.
     *
     * @param handler   The message creation handler.
     * @param data      The interaction data associated with the message.
     * @param ephemeral Whether the message should be ephemeral or not.
     */
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

    /**
     * Replies with a modal using the specified handler and interaction data.
     *
     * @param handler The modal creation handler.
     * @param data    The interaction data associated with the modal.
     */
    public static void replyModal(Consumer<ModalCreateAction> handler, InteractionData data) {
        ModalCreateAction action = new ModalCreateAction(data);
        execute(action, handler, false);
    }

    /**
     * Replies with a modal using the specified modal and interaction data.
     *
     * @param modal The modal to be replied with.
     * @param data  The interaction data associated with the modal.
     */
    public static void replyModal(Modal modal, InteractionData data) {
        Consumer<ModalCreateAction> handler = action -> {
            if (modal.getTitle() != null) {
                action.setTitle(modal.getTitle());
            }
            if (modal.getCustomId() != null) {
                action.setCustomId(modal.getCustomId());
            }
            if (modal.getElements() != null) {
                action.setElements(modal.getElements().toArray(new Element[0]));
            }
        };
        replyModal(handler, data);
    }

    /**
     * Modifies a message using the specified handler, message, and interaction data.
     *
     * @param handler The message modification handler.
     * @param message The message to be modified.
     * @param data    The interaction data associated with the message.
     */
    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message, InteractionData data) {
        MessageModifyAction action = new MessageModifyAction(message, data);
        execute(action, handler, false);
    }

    /**
     * Modifies a message using the specified handler and message.
     *
     * @param handler The message modification handler.
     * @param message The message to be modified.
     */
    public static void modifyMessage(Consumer<MessageModifyAction> handler, Message message) {
        MessageModifyAction action = new MessageModifyAction(message, null);
        execute(action, handler, false);
    }

    /**
     * Starts a new thread with the specified message and channel using the given handler.
     *
     * @param handler The thread creation handler.
     * @param message The message to start the thread with.
     * @param channel The channel where the thread will be started.
     * @return The ID of the newly created thread.
     */
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
        try {
            return invokeGetId(action, ThreadCreateAction.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts a new forum thread with the specified name in the given guild forum.
     *
     * @param handler The thread creation handler.
     * @param forum   The guild forum where the thread will be started.
     * @param name    The name of the thread.
     * @return The ID of the newly created thread.
     */
    public static String startForumThread(Consumer<ThreadCreateAction> handler, GuildForum forum, String name) {
        ThreadCreateAction action = new ThreadCreateAction(forum, name);
        return execute(action, handler, true);
    }

    /**
     * Starts a new thread from the specified message with the given name using the provided handler.
     *
     * @param handler The thread creation handler.
     * @param message The message to start the thread from.
     * @param name    The name of the thread.
     * @return The ID of the newly created thread.
     */
    public static String startThreadFromMessage(@Nullable Consumer<ThreadCreateAction> handler, Message message, String name) {
        ThreadCreateAction action = new ThreadCreateAction(message, name);
        if (handler != null) {
            handler.accept(action);
        }
        invokeSubmit(action, ThreadCreateAction.class);
        try {
            return invokeGetId(action, ThreadCreateAction.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modifies the application using the provided handler.
     *
     * @param handler The application modification handler.
     */
    public static void modifyApplication(Consumer<ApplicationModifyAction> handler) {
        ApplicationModifyAction action = new ApplicationModifyAction();
        execute(action, handler, false);
    }

    /**
     * Creates a webhook with the specified ID using the provided handler.
     *
     * @param handler The webhook creation handler.
     * @param id      The ID for the new webhook.
     * @return The ID of the created webhook.
     */
    public static String createWebhook(Consumer<WebhookCreateAction> handler, String id) {
        WebhookCreateAction action = new WebhookCreateAction(id);
        return execute(action, handler, true);
    }

    /**
     * Modifies an existing webhook with the specified ID using the provided handler.
     *
     * @param handler The webhook modification handler.
     * @param id      The ID of the webhook to modify.
     */
    public static void modifyWebhook(Consumer<WebhookModifyAction> handler, String id) {
        WebhookModifyAction action = new WebhookModifyAction(id);
        execute(action, handler, false);
    }

    /**
     * Creates a new stage using the provided handler.
     *
     * @param handler The stage creation handler.
     * @return The ID of the created stage.
     */
    public static String createStage(Consumer<StageCreateAction> handler) {
        StageCreateAction action = new StageCreateAction();
        return execute(action, handler, true);
    }

    /**
     * Creates a new slash command with the given name and description using the provided handler.
     *
     * @param handler     The slash command creation handler.
     * @param name        The name of the slash command.
     * @param description The description of the slash command.
     */
    public static void createSlashCommand(Consumer<SlashCommandCreateAction> handler, String name, String description) {
        SlashCommandCreateAction action = new SlashCommandCreateAction(name, description);
        execute(action, handler, false);
    }

    /**
     * Modifies an existing slash command with the specified ID using the provided handler.
     *
     * @param handler The slash command modification handler.
     * @param id      The ID of the slash command to modify.
     */
    public static void modifySlashCommand(Consumer<SlashCommandModifyAction> handler, String id) {
        SlashCommandModifyAction action = new SlashCommandModifyAction(id);
        execute(action, handler, false);
    }

    /**
     * Creates a new sub-command with the specified name, description, and list of sub-commands using the provided handler.
     *
     * @param handler        The sub-command creation handler.
     * @param name           The name of the sub-command.
     * @param description    The description of the sub-command.
     * @param subCommandList The list of sub-commands associated with the sub-command.
     */
    public static void createSubCommand(Consumer<SubCommandCreateAction> handler, String name, String description, List<SubCommand> subCommandList) {
        SubCommandCreateAction action = new SubCommandCreateAction(name, description, subCommandList);
        execute(action, handler, false);
    }

    /**
     * Creates a new sub-command group with the specified name, description, and list of sub-command groups using the provided handler.
     *
     * @param handler             The sub-command group creation handler.
     * @param name                The name of the sub-command group.
     * @param description         The description of the sub-command group.
     * @param subCommandGroupList The list of sub-command groups associated with the sub-command group.
     */
    public static void createSubCommandGroup(Consumer<SubCommandGroupCreateAction> handler, String name, String description, List<SubCommandGroup> subCommandGroupList) {
        SubCommandGroupCreateAction action = new SubCommandGroupCreateAction(name, description, subCommandGroupList);
        execute(action, handler, false);
    }
    
    private static String invokeGetId(Object action, Class<?> clazz) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod("getCreatedId");
                method.setAccessible(true);
                return (String) method.invoke(action);
            } catch(NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
            catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NoSuchMethodException("Method getCreatedId was not found");
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
