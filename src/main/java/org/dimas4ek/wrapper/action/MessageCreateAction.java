package org.dimas4ek.wrapper.action;

import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.utils.AttachmentUtils;
import org.dimas4ek.wrapper.utils.ComponentUtils;
import org.dimas4ek.wrapper.utils.EmbedUtils;
import org.json.JSONObject;
import org.slf4j.helpers.CheckReturnValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageCreateAction {
    private final JSONObject jsonObject;
    private final JSONObject channel;
    private List<Embed> embeds;
    private List<File> attachments;
    private List<ComponentBuilder> components;

    public MessageCreateAction(JSONObject jsonObject, JSONObject channel) {
        this.jsonObject = jsonObject;
        this.channel = channel;
    }

    @CheckReturnValue
    public MessageCreateAction addEmbeds(Embed... embeds) {
        if (embeds != null && embeds.length > 0) {
            if (this.embeds == null || this.embeds.isEmpty()) {
                this.embeds = new ArrayList<>();
                this.embeds.addAll(List.of(embeds));
            }
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

    @CheckReturnValue
    public MessageCreateAction addComponents(ComponentBuilder... components) {
        if (components != null && components.length > 0) {
            if (this.components == null || this.components.isEmpty()) {
                this.components = new ArrayList<>();
                this.components.addAll(List.of(components));
            }
        }

        return this;
    }

    public void submit() {
        if (embeds != null && !embeds.isEmpty()) {
            jsonObject.put("embeds", EmbedUtils.createEmbedsArray(embeds));
        }
        if (components != null && !components.isEmpty()) {
            jsonObject.put("components", ComponentUtils.createComponents(components));
        }

        if (attachments != null && !attachments.isEmpty()) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.creteMultipartBuilder(jsonObject, attachments);
            ApiClient.postAttachments(multipartBuilder, "/channels/" + channel.getString("id") + "/messages");
        } else {
            ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/messages");
        }
    }
}
