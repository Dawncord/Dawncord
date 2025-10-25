package io.github.dawncord.api.action.message;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.entities.message.component.ComponentBuilder;
import io.github.dawncord.api.entities.message.embed.Embed;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.types.AllowedMention;
import io.github.dawncord.api.utils.ComponentUtils;
import io.github.dawncord.api.utils.EmbedUtils;
import io.github.dawncord.api.utils.MessageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class MessageAction extends Action<MessageAction> {
    protected final InteractionData interactionData;
    protected List<File> attachments;

    protected MessageAction(InteractionData interactionData) {
        super();
        this.interactionData = interactionData;
    }

    /**
     * Sets the content for the message
     *
     * @param content the content to be set
     * @return the modified MessageAction object
     */
    public MessageAction setContent(String content) {
        return setProperty("content", content);
    }

    /**
     * Sets the embeds for the message.
     *
     * @param embeds the embeds to be set
     * @return the modified MessageAction object
     */
    public MessageAction setEmbeds(Embed... embeds) {
        return setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
    }

    /**
     * Sets the components for the message.
     *
     * @param components the component builders to be set
     * @return the modified MessageAction object
     */
    public MessageAction setComponents(ComponentBuilder... components) {
        return setProperty("components", ComponentUtils.createComponents(List.of(components)));
    }

    /**
     * Sets the attachments for the message.
     *
     * @param files the files to be attached
     * @return the modified MessageAction object
     */
    public MessageAction setAttachments(File... files) {
        if (files != null && files.length > 0) {
            if (this.attachments == null || this.attachments.isEmpty()) {
                this.attachments = new ArrayList<>(List.of(files));
                hasChanges = true;
            }
        }
        return this;
    }

    /**
     * Sets the allowed mentions for the message.
     *
     * @param allowedMentions the allowed mentions to be set
     * @return the modified MessageAction object
     */
    public MessageAction setAllowedMentions(AllowedMention... allowedMentions) {
        MessageUtils.setAllowedMentions(jsonObject, allowedMentions);
        return this;
    }

    /**
     * Updates the mentions for the message with the provided user IDs.
     *
     * @param userIds the user IDs to mention
     * @return the modified MessageAction object
     */
    public MessageAction mentionUsers(String... userIds) {
        MessageUtils.updateMentions(jsonObject, userIds, "users");
        return this;
    }

    /**
     * Updates the mentions for the message with the provided role IDs.
     *
     * @param roleIds the role IDs to mention
     * @return the modified MessageAction object
     */
    public MessageAction mentionRoles(String... roleIds) {
        MessageUtils.updateMentions(jsonObject, roleIds, "roles");
        return this;
    }
}
