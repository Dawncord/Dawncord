package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.types.MessageType;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageImpl implements Message {
    private final JSONObject message;

    public MessageImpl(JSONObject message) {
        this.message = message;
    }

    @Override
    public String getId() {
        return message.getString("id");
    }

    @Override
    public String getType() {
        for (MessageType type : MessageType.values()) {
            if (message.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    @Override
    public GuildChannel getChannel() {
        return new GuildChannelImpl(ApiClient.getJsonObject("/channels/" + message.getString("channel_id")));
    }

    @Override
    public String getContent() {
        return message.getString("content");
    }

    @Override
    public User getFrom() {
        return new UserImpl(ApiClient.getJsonObject("/users/" + message.getString("user_id")));
    }

    @Override
    public List<Attachment> getAttachments() {
        JSONArray attachments = message.getJSONArray("attachments");
        return JsonUtils.getEntityList(attachments, Attachment::new);
    }

    @Override
    public List<Embed> getEmbeds() {
        JSONArray embeds = message.getJSONArray("embeds");
        return JsonUtils.getEntityList(embeds, Embed::new);
    }

    @Override
    public List<User> getMentions() {
        JSONArray mentions = message.getJSONArray("mentions");
        return JsonUtils.getEntityList(mentions, UserImpl::new);
    }

    @Override
    public List<ActionRow> getActionRows() {
        JSONArray actionRows = message.getJSONArray("components");
        return JsonUtils.getEntityList(actionRows, ActionRow::new);
    }

    @Override
    public boolean isPinned() {
        return message.getBoolean("pinned");
    }

    @Override
    public boolean isMentionEveryone() {
        return message.getBoolean("mention_everyone");
    }

    @Override
    public boolean isTTS() {
        return message.getBoolean("tts");
    }

    @Override
    public ZonedDateTime getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(message.getString("timestamp"), formatter);
    }

    @Override
    public ZonedDateTime getEditedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return ZonedDateTime.parse(message.getString("edited_timestamp"), formatter);
    }
}
