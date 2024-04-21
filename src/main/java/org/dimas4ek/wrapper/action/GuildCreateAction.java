package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.types.*;
import org.dimas4ek.wrapper.utils.IOUtils;

import java.lang.reflect.Method;
import java.util.function.Consumer;

//todo add create guild
public class GuildCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private int roleId = 1;
    private int channelId = 1;
    private boolean hasChanges = false;

    public GuildCreateAction() {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.set("roles", mapper.createArrayNode());
        this.jsonObject.set("channels", mapper.createArrayNode());
    }

    private GuildCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public GuildCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public GuildCreateAction setIcon(String path) {
        if (!path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildCreateAction setVerificationLevel(VerificationLevel level) {
        return setProperty("verification_level", level.getValue());
    }

    public GuildCreateAction setNotificationLevel(NotificationLevel level) {
        return setProperty("default_message_notifications", level.getValue());
    }

    public GuildCreateAction setContentFilterLevel(ContentFilterLevel level) {
        return setProperty("explicit_content_filter", level.getValue());
    }

    public GuildCreateAction createRole(Consumer<GuildRoleCreateAction> handler) {
        GuildRoleCreateAction action = new GuildRoleCreateAction(null, false);
        handler.accept(action);
        try {
            Method executeMethod = GuildRoleCreateAction.class.getDeclaredMethod("getJsonObject");
            executeMethod.setAccessible(true);
            ((ArrayNode) jsonObject.get("roles")).add(((ObjectNode) executeMethod.invoke(action)).put("id", roleId++));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public GuildCreateAction createChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return this;
    }

    public GuildCreateAction createCategory(String name, Consumer<CategoryCreateAction> handler) {
        createGuildChannel(ChannelType.GUILD_CATEGORY, name);
        handler.accept(new CategoryCreateAction(channelId - 1));
        return this;
    }

    public GuildCreateAction createAfkChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return setProperty("afk_channel_id", channelId - 1);
    }

    public GuildCreateAction setAfkTimeout(int seconds) {
        return setProperty("afk_timeout", seconds);
    }

    public GuildCreateAction createSystemChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return setProperty("system_channel_id", channelId - 1);
    }

    public GuildCreateAction setSystemChannelFlags(SystemChannelFlag... flags) {
        long value = 0;
        for (SystemChannelFlag flag : flags) {
            value |= flag.getValue();
        }
        return setProperty("system_channel_flags", value);
    }

    private void createGuildChannel(ChannelType type, String name) {
        ObjectNode channel = mapper.createObjectNode()
                .put("id", channelId++)
                .put("type", type.getValue())
                .put("name", name);
        if (jsonObject.has("channels") && jsonObject.hasNonNull("channels")) {
            ((ArrayNode) jsonObject.get("channels")).add(channel);
        } else {
            jsonObject.set("channels", mapper.createArrayNode().add(channel));
        }
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Guild.All());
            hasChanges = false;
        }
        jsonObject.removeAll();
    }

    public class CategoryCreateAction {
        private final int parentId;

        public CategoryCreateAction(int parentId) {
            this.parentId = parentId;
        }

        private CategoryCreateAction setProperty(String name, Object value) {
            jsonObject.set(name, mapper.valueToTree(value));
            hasChanges = true;
            return this;
        }

        public CategoryCreateAction addChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return this;
        }

        public CategoryCreateAction addAfkChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return setProperty("afk_channel_id", channelId - 1);
        }

        public CategoryCreateAction addSystemChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return setProperty("system_channel_id", channelId - 1);
        }

        private void createGuildChannelUnderCategory(ChannelType type, String name) {
            ObjectNode channel = mapper.createObjectNode()
                    .put("id", channelId++)
                    .put("type", type.getValue())
                    .put("name", name)
                    .put("parent_id", parentId);
            if (jsonObject.has("channels") && jsonObject.hasNonNull("channels")) {
                ((ArrayNode) jsonObject.get("channels")).add(channel);
            } else {
                jsonObject.set("channels", mapper.createArrayNode().add(channel));
            }
        }
    }
}
