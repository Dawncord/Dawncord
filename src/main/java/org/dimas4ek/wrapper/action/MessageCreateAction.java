package org.dimas4ek.wrapper.action;

import okhttp3.MultipartBody;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.message.component.ComponentBuilder;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.utils.AttachmentUtils;
import org.dimas4ek.wrapper.utils.ComponentUtils;
import org.dimas4ek.wrapper.utils.EmbedUtils;
import org.json.JSONObject;

import java.io.File;

public class MessageCreateAction {
    private final JSONObject jsonObject;
    private final JSONObject channel;

    public MessageCreateAction(JSONObject jsonObject, JSONObject channel) {
        this.jsonObject = jsonObject;
        this.channel = channel;
    }

    public MessageCreateAction addEmbeds(Embed... embeds) {
        if (embeds != null && embeds.length > 0) {
            jsonObject.put("embeds", EmbedUtils.createEmbedsArray(embeds));
            ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/messages");
        }

        return this;
    }

    public MessageCreateAction addAttachments(File... files) {
        if (files != null && files.length > 0) {
            MultipartBody.Builder multipartBuilder = AttachmentUtils.creteMultipartBuilder(jsonObject, files);
            ApiClient.postAttachments(multipartBuilder, "/channels/" + channel.getString("id") + "/messages");
        }

        return this;
    }

    public MessageCreateAction addComponents(ComponentBuilder... components) {
        if (components != null) {
            jsonObject.put("components", ComponentUtils.createComponents(components));
            ApiClient.post(jsonObject, "/channels/" + channel.getString("id") + "/messages");
        }

        return this;
    }
}
