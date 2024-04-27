package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MultipartBody;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.message.Message;
import org.dimas4ek.dawncord.entities.message.component.ComponentBuilder;
import org.dimas4ek.dawncord.entities.message.embed.Embed;
import org.dimas4ek.dawncord.entities.message.poll.PollData;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.types.AllowedMention;
import org.dimas4ek.dawncord.types.InteractionCallbackType;
import org.dimas4ek.dawncord.types.MessageFlag;
import org.dimas4ek.dawncord.utils.*;

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
    private String createdId;

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

    public MessageCreateAction(InteractionData interactionData) {
        this.interactionData = interactionData;
        this.jsonObject = mapper.createObjectNode();
        this.channelId = null;
    }

    private void setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
    }

    private void setReplyProperty(String key, Object value) {
        if (jsonObject.has("data")) {
            ((ObjectNode) jsonObject.get("data")).set(key, mapper.valueToTree(value));
        } else {
            jsonObject.set(key, mapper.valueToTree(value));
        }
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

    public MessageCreateAction setPoll(PollData poll) {
        if (poll != null) {
            if (interactionData != null) {
                setReplyProperty("poll", PollUtils.createPoll(poll));
            } else {
                setProperty("poll", PollUtils.createPoll(poll));
            }
            hasChanges = true;
        }
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
                    .put("fail_if_not_exists", JsonUtils.fetch(Routes.Channel.Message.Get(message.getChannel().getId(), message.getId())) == null ? true : null)
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

    private void postMessage(ObjectNode jsonObject, String url) {
        JsonNode jsonNode;
        if (attachments != null && !attachments.isEmpty()) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, attachments);
            jsonNode = ApiClient.postAttachments(multipartBuilder, url);
        } else {
            jsonNode = ApiClient.post(jsonObject, url);
        }
        if (jsonNode != null && jsonNode.has("id")) {
            createdId = jsonNode.get("id").asText();
        }
    }

    private void patchMessage(ObjectNode jsonObject, String url) {
        JsonNode jsonNode;
        if (attachments != null && !attachments.isEmpty()) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.createMultipartBuilder(jsonObject, attachments);
            jsonNode = ApiClient.postAttachments(multipartBuilder, url);
        } else {
            jsonNode = ApiClient.patch(jsonObject, url);
        }
        if (jsonNode.has("id") && jsonNode.has("id")) {
            createdId = jsonNode.get("id").asText();
        }
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            if (interactionData != null) {
                postMessage(jsonObject, Routes.Reply(interactionData.getInteraction().getInteractionId(), interactionData.getInteraction().getInteractionToken()));
            } else {
                postMessage(jsonObject, Routes.Channel.Message.All(channelId));
            }
            hasChanges = false;
        }
        //jsonObject.removeAll();
    }

    private void submitDefer() {
        if (hasChanges) {
            if (interactionData != null) {
                patchMessage(jsonObject, Routes.OriginalMessage(interactionData.getInteraction().getInteractionToken()));
            }
            hasChanges = false;
        }
        //jsonObject.removeAll();
    }
}
