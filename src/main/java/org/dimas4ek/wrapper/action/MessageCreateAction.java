package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.interaction.InteractionData;
import org.dimas4ek.wrapper.types.AllowedMention;
import org.dimas4ek.wrapper.types.InteractionCallbackType;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private List<File> attachments;
    private int flags;
    private boolean hasChanges = false;
    private final InteractionData interactionData;

    public MessageCreateAction(String content, String channelId, InteractionData interactionData) {
        this.interactionData = interactionData;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("type", InteractionCallbackType.CHANNEL_MESSAGE_WITH_SOURCE.getValue());
        this.jsonObject.set("data", mapper.createObjectNode());
        if (content != null) {
            ((ObjectNode) this.jsonObject.get("data")).put("content", content);
            hasChanges = true;
        }
        this.channelId = channelId;
        flags = 0;
    }

    public MessageCreateAction(String content, String channelId) {
        this.interactionData = null;
        this.jsonObject = mapper.createObjectNode();
        if (content != null) {
            this.jsonObject.put("content", content);
            hasChanges = true;
        }
        this.channelId = channelId;
        flags = 0;
    }

    private void setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
    }

    private void setReplyProperty(String key, Object value) {
        ((ObjectNode) jsonObject.get("data")).set(key, mapper.valueToTree(value));
    }

    public MessageCreateAction setContent(String content) {
        if (interactionData != null) {
            setReplyProperty("content", content);
        } else {
            setProperty("content", content);
        }
        hasChanges = true;
        return this;
    }

    public MessageCreateAction setEmbeds(Embed... embeds) {
        if (embeds != null && embeds.length > 0) {
            if (interactionData != null) {
                setReplyProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
            } else {
                setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
            }
            hasChanges = true;
        }

        return this;
    }

    public MessageCreateAction setComponents(ComponentBuilder... components) {
        if (components != null && components.length > 0) {
            if (interactionData != null) {
                setReplyProperty("components", ComponentUtils.createComponents(List.of(components)));
            } else {
                setProperty("components", ComponentUtils.createComponents(List.of(components)));
            }
            hasChanges = true;
        }

        return this;
    }

    public MessageCreateAction setAttachments(File... files) {
        if (files != null && files.length > 0) {
            if (this.attachments == null || this.attachments.isEmpty()) {
                this.attachments = new ArrayList<>(List.of(files));
                hasChanges = true;
            }
        }

        return this;
    }

    public MessageCreateAction setAllowedMentions(AllowedMention... allowedMentions) {
        if (interactionData != null) {
            MessageUtils.setAllowedMentions(jsonObject.get("data"), allowedMentions);
        } else {
            MessageUtils.setAllowedMentions(jsonObject, allowedMentions);
        }
        return this;
    }

    public MessageCreateAction mentionUsers(String... userIds) {
        if (interactionData != null) {
            MessageUtils.updateMentions(jsonObject.get("data"), userIds, "users");
        } else {
            MessageUtils.updateMentions(jsonObject, userIds, "users");
        }
        return this;
    }

    public MessageCreateAction mentionRoles(String... roleIds) {
        if (interactionData != null) {
            MessageUtils.updateMentions(jsonObject.get("data"), roleIds, "roles");
        } else {
            MessageUtils.updateMentions(jsonObject, roleIds, "roles");
        }
        return this;
    }

    public MessageCreateAction setMessageReference(Message message) {
        if (interactionData == null) {
            setProperty("message_reference", mapper.createObjectNode()
                    .put("message_id", message.getId())
                    .put("channel_id", message.getChannel().getId())
                    .put("guild_id", message.getGuild().getId())
                    .put("fail_if_not_exists", JsonUtils.fetchEntity("/channels/" + message.getChannel().getId() + "/messages/" + message.getId()) == null ? true : null)
            );
        }
        return this;
    }

    public MessageCreateAction setStickers(Sticker... stickers) {
        if (stickers != null && stickers.length > 0) {
            if (interactionData == null) {
                setProperty("sticker_ids", mapper.createArrayNode().add(mapper.valueToTree(stickers)));
            }
        }
        return this;
    }

    public MessageCreateAction setEphemeral(boolean enabled) {
        if (enabled) {
            flags |= MessageFlag.EPHEMERAL.getValue();
        }
        if (interactionData != null) {
            setReplyProperty("flags", flags);
        } else {
            setProperty("flags", flags);
        }
        return this;
    }

    public MessageCreateAction setSuppressEmbeds(boolean enabled) {
        if (enabled) {
            flags |= MessageFlag.SUPPRESS_EMBEDS.getValue();
        }
        if (interactionData != null) {
            setReplyProperty("flags", flags);
        } else {
            setProperty("flags", flags);
        }
        return this;
    }

    public MessageCreateAction setSuppressNotifications(boolean enabled) {
        if (enabled) {
            flags |= MessageFlag.SUPPRESS_NOTIFICATIONS.getValue();
        }
        if (interactionData == null) {
            setProperty("flags", flags);
        }
        return this;
    }

    private void submit() {
        if (hasChanges) {
            if (interactionData != null) {
                postMessage(jsonObject, "/interactions/" + interactionData.getInteraction().getInteractionId() + "/" + interactionData.getInteraction().getInteractionToken() + "/callback");
            } else {
                postMessage(jsonObject, "/channels/" + channelId + "/messages");
            }
            hasChanges = false;
        }
        jsonObject.removeAll();
    }

    void postMessage(ObjectNode jsonObject, String url) {
        if (attachments != null && !attachments.isEmpty()) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, attachments);
            ApiClient.postAttachments(multipartBuilder, url);
        } else {
            ApiClient.post(jsonObject, url);
        }
    }
}
