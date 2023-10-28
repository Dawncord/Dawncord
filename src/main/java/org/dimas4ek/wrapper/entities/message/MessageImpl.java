package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.channel.GuildChannelImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.message.component.ActionRow;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.entities.message.sticker.Sticker;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.types.MessageType;
import org.dimas4ek.wrapper.utils.EmbedUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    public long getIdLong() {
        return Long.parseLong(getId());
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
        return new GuildChannelImpl(JsonUtils.fetchEntity("/channels/" + message.getString("channel_id")));
    }

    @Override
    public Guild getGuild() {
        return getChannel().getGuild();
    }


    @Override
    public String getContent() {
        return message.getString("content");
    }

    @Override
    public User getFrom() {
        return new UserImpl(JsonUtils.fetchEntity("/users/" + message.getString("user_id")));
    }

    @Override
    public List<Attachment> getAttachments() {
        return JsonUtils.getEntityList(message.getJSONArray("attachments"), Attachment::new);
    }

    @Override
    public List<Embed> getEmbeds() {
        return JsonUtils.getEntityList(message.getJSONArray("embeds"), EmbedUtils::getEmbedFromJson);
    }

    @Override
    public List<User> getMentions() {
        return JsonUtils.getEntityList(message.getJSONArray("mentions"), UserImpl::new);
    }

    @Override
    public List<GuildRole> getMentionRoles() {
        List<GuildRole> roles = new ArrayList<>();
        JSONArray mentionRoles = message.getJSONArray("mention_roles");
        for (int i = 0; i < mentionRoles.length(); i++) {
            roles.add(getGuild().getRoleById(mentionRoles.getString(i)));
        }
        return roles;
    }

    @Override
    public List<Reaction> getReactions() {
        return JsonUtils.getEntityList(message.getJSONArray("reactions"), ReactionImpl::new);
    }

    @Override
    public Reaction getReactionByEmojiIdOrName(String emojiIdOrName) {
        return getReactions().stream().filter(reaction -> reaction.getEmoji().equals(emojiIdOrName)).findAny().orElse(null);
    }

    @Override
    public Activity getActivity() {
        return new ActivityImpl(message.getJSONObject("activity"));
    }

    @Override
    public Application getApplication() {
        return new ApplicationImpl(message.getJSONObject("application"));
    }

    @Override
    public Message getReferencedMessage() {
        return message.has("referenced_message") && !message.isNull("referenced_message")
                ? new MessageImpl(message.getJSONObject("referenced_message"))
                : null;
    }

    @Override
    public Thread getThread() {
        return message.has("thread")
                ? new ThreadImpl(message.getJSONObject("thread"))
                : null;
    }

    @Override
    public List<Sticker> getStickers() {
        return MessageUtils.retrieveStickersFromMessage(message, getGuild());
    }

    @Override
    public List<ActionRow> getActionRows() {
        return JsonUtils.getEntityList(message.getJSONArray("components"), ActionRow::new);
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
        return MessageUtils.getZonedDateTime(message, "timestamp");
    }

    @Override
    public ZonedDateTime getEditedTimestamp() {
        return MessageUtils.getZonedDateTime(message, "edited_timestamp");
    }
}
