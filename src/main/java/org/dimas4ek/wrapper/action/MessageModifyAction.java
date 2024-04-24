package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.types.AllowedMention;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.utils.AttachmentUtils;
import org.dimas4ek.wrapper.utils.ComponentUtils;
import org.dimas4ek.wrapper.utils.EmbedUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Message message;
    private final InteractionData interactionData;
    private final Map<String, String> actions;
    private boolean hasChanges = false;
    private List<File> attachments;

    public MessageModifyAction(Message message) {
        this.message = message;
        this.interactionData = null;
        this.jsonObject = mapper.createObjectNode();
        this.actions = new HashMap<>();
    }

    public MessageModifyAction(Message message, InteractionData interactionData) {
        this.message = message;
        this.interactionData = interactionData;
        this.jsonObject = mapper.createObjectNode();
        this.actions = new HashMap<>();
    }

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

    public MessageModifyAction addReaction(String emojiIdOrName) {
        return setAction("put", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, "@me"));
    }

    public MessageModifyAction removeReaction(String emojiIdOrName) {
        return setAction("delete", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, "@me"));
    }

    public MessageModifyAction removeReaction(String emojiIdOrName, User user) {
        return setAction("delete", Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emojiIdOrName, user.getId()));
    }

    public MessageModifyAction clearReactions() {
        return setAction("delete", Routes.Channel.Message.Reaction.All(message.getChannel().getId(), message.getId()));
    }

    public MessageModifyAction clearReactions(String emojiIdOrName) {
        return setAction("delete", Routes.Channel.Message.Reaction.Get(message.getChannel().getId(), message.getId(), emojiIdOrName));
    }

    public MessageModifyAction setContent(String content) {
        return setProperty("content", content);
    }

    public MessageModifyAction setEmbeds(Embed... embeds) {
        return setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
    }

    public MessageModifyAction setComponents(ComponentBuilder... components) {
        return setProperty("components", ComponentUtils.createComponents(List.of(components)));
    }

    public MessageModifyAction setAttachments(File... files) {
        if (files != null && files.length > 0) {
            if (this.attachments == null || this.attachments.isEmpty()) {
                this.attachments = new ArrayList<>(List.of(files));
                hasChanges = true;
            }
        }

        return this;
    }

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

    public MessageModifyAction setAllowedMentions(AllowedMention... allowedMentions) {
        MessageUtils.setAllowedMentions(jsonObject, allowedMentions);
        return this;
    }

    public MessageModifyAction mentionUsers(String... userIds) {
        MessageUtils.updateMentions(jsonObject, userIds, "users");
        return this;
    }

    public MessageModifyAction mentionRoles(String... roleIds) {
        MessageUtils.updateMentions(jsonObject, roleIds, "roles");
        return this;
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
        jsonObject.removeAll();
    }
}
