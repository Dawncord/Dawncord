package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.message.Message;
import org.dimas4ek.wrapper.entities.message.embed.Embed;
import org.dimas4ek.wrapper.types.AllowedMention;
import org.dimas4ek.wrapper.types.MessageFlag;
import org.dimas4ek.wrapper.utils.EmbedUtils;
import org.dimas4ek.wrapper.utils.MessageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Message message;
    private final Map<String, String> actions;
    private boolean hasChanges = false;

    public MessageModifyAction(Message message) {
        this.message = message;
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
        return setAction("put", "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/reactions/" + emojiIdOrName + "/@me");
    }

    public MessageModifyAction removeReaction(String emojiIdOrName) {
        return setAction("delete", "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/reactions/" + emojiIdOrName + "/@me");
    }

    public MessageModifyAction removeReaction(String emojiIdOrName, User user) {
        return setAction("delete", "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/reactions/" + emojiIdOrName + "/" + user.getId());
    }

    public MessageModifyAction clearReactions() {
        return setAction("delete", "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/reactions");
    }

    public MessageModifyAction clearReactions(String emojiIrOrName) {
        return setAction("delete", "/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/reactions/" + emojiIrOrName);
    }

    public MessageModifyAction setContent(String content) {
        return setProperty("content", content);
    }

    public MessageModifyAction setEmbeds(Embed... embeds) {
        return setProperty("embeds", EmbedUtils.createEmbedsArray(List.of(embeds)));
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
            ApiClient.patch(jsonObject, "/channels/" + message.getChannel().getId() + "/messages/" + message.getId());
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
