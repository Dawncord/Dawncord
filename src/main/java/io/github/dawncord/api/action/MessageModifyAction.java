package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.entities.message.component.ComponentBuilder;
import io.github.dawncord.api.entities.message.embed.Embed;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.types.AllowedMention;
import io.github.dawncord.api.types.MessageFlag;
import io.github.dawncord.api.utils.AttachmentUtils;
import io.github.dawncord.api.utils.ComponentUtils;
import io.github.dawncord.api.utils.EmbedUtils;
import io.github.dawncord.api.utils.MessageUtils;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an action for updating a message.
 *
 * @see Message
 */
public class MessageModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Message message;
    private final InteractionData interactionData;
    private final Map<String, String> actions;
    private boolean hasChanges = false;
    private List<File> attachments;

    /**
     * Create a new {@link MessageModifyAction}
     *
     * @param message The message to be modified.
     */
    public MessageModifyAction(Message message) {
        this.message = message;
        this.interactionData = null;
        this.jsonObject = mapper.createObjectNode();
        this.actions = new HashMap<>();
    }

    /**
     * Create a new {@link MessageModifyAction}
     *
     * @param message         The message to be modified.
     * @param interactionData The interaction data associated with the modification.
     */
    public MessageModifyAction(Message message, InteractionData interactionData) {
        this.message = message;
        this.interactionData = interactionData;
        this.jsonObject = mapper.createObjectNode();
        this.actions = new HashMap<>();
    }

    /**
     * Create a new {@link MessageModifyAction}
     */
    private MessageModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    private MessageModifyAction setAction(String method, String url) {
        actions.put(method, url);
        hasChanges = true;
        return this;
    }

    /**
     * Adds a reaction to the message with the given emoji ID or name.
     *
     * @param emojiIdOrName the ID or name of the emoji to react with
     * @return the modified message action object
     */
    public MessageModifyAction addReaction(String emojiIdOrName) {
        return setAction("put", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, "@me"));
    }

    /**
     * Removes a reaction from the message with the given emoji ID or name.
     *
     * @param emojiIdOrName the ID or name of the emoji to remove the reaction for
     * @return the modified message action object
     */
    public MessageModifyAction removeReaction(String emojiIdOrName) {
        return setAction("delete", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, "@me"));
    }

    /**
     * Removes a reaction from the message with the given emoji ID or name for the specified user.
     *
     * @param emojiIdOrName the ID or name of the emoji to remove the reaction for
     * @param user          the user whose reaction to remove
     * @return the modified message action object
     */
    public MessageModifyAction removeReaction(String emojiIdOrName, User user) {
        return setAction("delete", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, user.getId()));
    }

    /**
     * Clears all reactions from the message.
     *
     * @return the modified message action object
     */
    public MessageModifyAction clearReactions() {
        return setAction("delete", Routes.Channel.Message.Reaction.All(message.getChannel().getId(), message.getId()));
    }

    /**
     * Clears all reactions from the message for the specified emoji ID or name.
     *
     * @param emojiIdOrName the ID or name of the emoji to clear reactions for
     * @return the modified message action object
     */
    public MessageModifyAction clearReactions(String emojiIdOrName) {
        return setAction("delete", Routes.Channel.Message.Reaction.Get(message.getChannel().getId(), message.getId(), emojiIdOrName));
    }

    /**
     * Sets the content for the message.
     *
     * @param content the new content of the message
     * @return the modified message action object
     */
    public MessageModifyAction setContent(String content) {
        return setProperty("content", content);
    }

    /**
     * Sets the embeds for the message.
     *
     * @param embeds the embeds to be set
     * @return the modified message modify action object
     */
    public MessageModifyAction setEmbeds(Embed... embeds) {
        return setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
    }

    /**
     * Sets the components for the message.
     *
     * @param components the component builders to be set
     * @return the modified message modify action object
     */
    public MessageModifyAction setComponents(ComponentBuilder... components) {
        return setProperty("components", ComponentUtils.createComponents(List.of(components)));
    }

    /**
     * Sets the attachments for the message.
     *
     * @param files the files to be attached
     * @return the modified message action object
     */
    public MessageModifyAction setAttachments(File... files) {
        if (files != null && files.length > 0) {
            if (this.attachments == null || this.attachments.isEmpty()) {
                this.attachments = new ArrayList<>(List.of(files));
                hasChanges = true;
            }
        }

        return this;
    }

    /**
     * Sets the suppressEmbeds for the message.
     *
     * @param enabled a boolean indicating whether to suppress embeds or not
     * @return the modified MessageModifyAction object
     */
    public MessageModifyAction setSuppressEmbeds(boolean enabled) {
        int value = 0;
        for (MessageFlag flag : message.getFlags()) {
            value |= flag.getValue();
        }
        if (enabled) {
            value |= MessageFlag.SUPPRESS_EMBEDS.getValue();
            setProperty("flags", value);
        }
        return this;
    }

    /**
     * Sets the allowed mentions for the message.
     *
     * @param allowedMentions the allowed mentions to be set
     * @return the modified message modify action object
     */
    public MessageModifyAction setAllowedMentions(AllowedMention... allowedMentions) {
        MessageUtils.setAllowedMentions(jsonObject, allowedMentions);
        return this;
    }

    /**
     * Updates the mentions for the message with the provided user IDs.
     *
     * @param userIds the user IDs to mention
     * @return the modified message modify action object
     */
    public MessageModifyAction mentionUsers(String... userIds) {
        MessageUtils.updateMentions(jsonObject, userIds, "users");
        return this;
    }

    /**
     * Updates the mentions for the message with the provided role IDs.
     *
     * @param roleIds the role IDs to mention
     * @return the modified message action object
     */
    public MessageModifyAction mentionRoles(String... roleIds) {
        MessageUtils.updateMentions(jsonObject, roleIds, "roles");
        return this;
    }

    /**
     * Ends the poll associated with the message.
     *
     * @return the modified MessageModifyAction object
     */
    public MessageModifyAction endPoll() {
        ApiClient.post(null, Routes.Channel.Message.Poll.End(message.getChannel().getId(), message.getId()));
        return this;
    }

    /**
     * Removes the components from the message.
     *
     * @return the modified message modify action object
     */
    public MessageModifyAction removeComponents() {
        return setProperty("components", mapper.createArrayNode());
    }

    /**
     * Removes the attachments from the message.
     *
     * @return the modified message modify action object
     */
    public MessageModifyAction removeAttachments() {
        return setProperty("attachments", mapper.createArrayNode());
    }

    /**
     * Removes the embeds from the message.
     *
     * @return the modified message modify action object
     */
    public MessageModifyAction removeEmbeds() {
        return setProperty("embeds", mapper.createArrayNode());
    }

    private void submit() {
        if (hasChanges) {
            if (!actions.isEmpty()) {
                for (Map.Entry<String, String> entry : actions.entrySet()) {
                    switch (entry.getKey()) {
                        case "put":
                            ApiClient.put(null, entry.getValue());
                            break;
                        case "delete":
                            ApiClient.delete(entry.getValue());
                            break;
                    }
                }
            }
            if (interactionData != null) {
                if (attachments != null && !attachments.isEmpty()) {
                    MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, attachments);
                    ApiClient.patchAttachments(multipartBuilder, Routes.OriginalMessage(interactionData.getInteraction().getInteractionToken()));
                } else {
                    ApiClient.patch(jsonObject, Routes.OriginalMessage(interactionData.getInteraction().getInteractionToken()));
                }
            } else {
                if (attachments != null && !attachments.isEmpty()) {
                    MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, attachments);
                    ApiClient.patchAttachments(multipartBuilder, Routes.Channel.Message.Get(message.getChannel().getId(), message.getId()));
                } else {
                    ApiClient.patch(jsonObject, Routes.Channel.Message.Get(message.getChannel().getId(), message.getId()));
                }
            }
            hasChanges = false;
        }
    }
}
