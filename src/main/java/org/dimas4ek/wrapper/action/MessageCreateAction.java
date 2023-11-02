package org.dimas4ek.wrapper.action;

import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.types.AllowedMention;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.utils.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageCreateAction {
    private final JSONObject jsonObject;
    private final JSONObject channel;
    private List<File> attachments;
    private int flags;
    private boolean hasChanges;

    public MessageCreateAction(JSONObject jsonObject, JSONObject channel) {
        this.jsonObject = jsonObject;
        this.channel = channel;
        flags = 0;
        hasChanges = false;
    }

    private void setProperty(String key, Object value) {
        jsonObject.put(key, value);
        hasChanges = true;
    }

    @CheckReturnValue
    public MessageCreateAction addEmbeds(Embed... embeds) {
        if (embeds != null && embeds.length > 0) {
            setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
        }

        return this;
    }

    @CheckReturnValue
    public MessageCreateAction addComponents(ComponentBuilder... components) {
        if (components != null && components.length > 0) {
            setProperty("components", ComponentUtils.createComponents(List.of(components)));
        }

        return this;
    }

    @CheckReturnValue
    public MessageCreateAction addAttachments(File... files) {
        if (files != null && files.length > 0) {
            if (this.attachments == null || this.attachments.isEmpty()) {
                this.attachments = new ArrayList<>();
                this.attachments.addAll(List.of(files));
            }
        }

        return this;
    }

    public MessageCreateAction setAllowedMentions(AllowedMention... allowedMentions) {
        MessageUtils.setAllowedMentions(jsonObject, allowedMentions);
        return this;
    }

    public MessageCreateAction mentionUsers(String... userIds) {
        MessageUtils.updateMentions(jsonObject, userIds, "users");
        return this;
    }

    public MessageCreateAction mentionRoles(String... roleIds) {
        MessageUtils.updateMentions(jsonObject, roleIds, "roles");
        return this;
    }

    public MessageCreateAction setMessageReference(Message message) {
        setProperty("message_reference", new JSONObject()
                .put("message_id", message.getId())
                .put("channel_id", message.getChannel().getId())
                .put("guild_id", message.getGuild().getId())
                .put("fail_if_not_exists", JsonUtils.fetchEntity("/channels/" + message.getChannel().getId() + "/messages/" + message.getId()) == null
                        ? true : null)
        );
        return this;
    }

    public MessageCreateAction setStickers(Sticker... stickers) {
        if (stickers != null && stickers.length > 0) {
            setProperty("sticker_ids", new JSONArray().put(stickers));
        }
        return this;
    }

    public MessageCreateAction setSuppressEmbeds(boolean enabled) {
        if (enabled) {
            flags |= MessageFlag.SUPPRESS_EMBEDS.getValue();
        }
        setProperty("flags", flags);
        return this;
    }

    public MessageCreateAction setSuppressNotifications(boolean enabled) {
        if (enabled) {
            flags |= MessageFlag.SUPPRESS_NOTIFICATIONS.getValue();
        }
        setProperty("flags", flags);
        return this;
    }

    public void submit() {
        if (hasChanges) {
            if (attachments != null && !attachments.isEmpty()) {
                MultipartBody.Builder multipartBuilder = AttachmentUtils.creteMultipartBuilder(jsonObject, attachments);
                ApiClient.postAttachments(multipartBuilder, "/channels/" + channel.getString("id") + "/messages");
            } else {
                ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/messages");
            }
            hasChanges = false;
            jsonObject.clear();
        }
    }
}
