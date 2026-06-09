package io.github.dawncord.api.action.message;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.message.Message;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.types.MessageFlag;
import io.github.dawncord.api.utils.AttachmentUtils;
import okhttp3.MultipartBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an action for updating a message.
 *
 * @see Message
 */
public class MessageModifyAction extends MessageAction {
    private final Message message;
    private final Map<String, String> actions;

    /**
     * Create a new {@link MessageModifyAction}
     *
     * @param message         The message to be modified.
     * @param interactionData The interaction data associated with the modification.
     */
    public MessageModifyAction(Message message, InteractionData interactionData) {
        super(interactionData);
        this.message = message;
        this.actions = new HashMap<>();
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
        setProperty("components", mapper.createArrayNode());
        return this;
    }

    /**
     * Removes the attachments from the message.
     *
     * @return the modified message modify action object
     */
    public MessageModifyAction removeAttachments() {
        setProperty("attachments", mapper.createArrayNode());
        return this;
    }

    /**
     * Removes the embeds from the message.
     *
     * @return the modified message modify action object
     */
    public MessageModifyAction removeEmbeds() {
        setProperty("embeds", mapper.createArrayNode());
        return this;
    }

    @Override
    protected void submit() {
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
                    ApiClient.patchAttachments(multipartBuilder, Routes.OriginalMessage(interactionData.interaction().interactionToken()));
                } else {
                    ApiClient.patch(jsonObject, Routes.OriginalMessage(interactionData.interaction().interactionToken()));
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
