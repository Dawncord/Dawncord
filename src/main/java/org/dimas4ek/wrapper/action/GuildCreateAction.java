package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.types.*;
import org.dimas4ek.wrapper.utils.IOUtils;

import java.lang.reflect.Method;
import java.util.function.Consumer;

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

    private void setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
    }

    public GuildCreateAction setName(String name) {
        setProperty("name", name);
        return this;
    }

    public GuildCreateAction setIcon(String path) {
        if (!path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    public GuildCreateAction setVerificationLevel(VerificationLevel level) {
        setProperty("verification_level", level.getValue());
        return this;
    }

    public GuildCreateAction setNotificationLevel(NotificationLevel level) {
        setProperty("default_message_notifications", level.getValue());
        return this;
    }

    public GuildCreateAction setContentFilterLevel(ContentFilterLevel level) {
        setProperty("explicit_content_filter", level.getValue());
        return this;
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
        setProperty("afk_channel_id", channelId - 1);
        return this;
    }

    public GuildCreateAction setAfkTimeout(int seconds) {
        setProperty("afk_timeout", seconds);
        return this;
    }

    public GuildCreateAction createSystemChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        setProperty("system_channel_id", channelId - 1);
        return this;
    }

    public GuildCreateAction setSystemChannelFlags(SystemChannelFlag... flags) {
        long value = 0;
        for (SystemChannelFlag flag : flags) {
            value |= flag.getValue();
        }
        setProperty("system_channel_flags", value);
        return this;
    }

    private void createGuildChannel(ChannelType type, String name) {
        ((ArrayNode) jsonObject.get("channels")).add(
                mapper.createObjectNode()
                        .put("id", channelId++)
                        .put("type", type.getValue())
                        .put("name", name)
        );
    }

    private void submit() {
        if (hasChanges) {
            ApiClient.post(jsonObject, "/guilds");
            hasChanges = false;
        }
        jsonObject.removeAll();
    }

    public class CategoryCreateAction {
        private final int parentId;

        public CategoryCreateAction(int parentId) {
            this.parentId = parentId;
        }

        public CategoryCreateAction addChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return this;
        }

        public CategoryCreateAction addAfkChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            setProperty("afk_channel_id", channelId - 1);
            return this;
        }

        public CategoryCreateAction addSystemChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            setProperty("system_channel_id", channelId - 1);
            return this;
        }

        private void createGuildChannelUnderCategory(ChannelType type, String name) {
            ((ArrayNode) jsonObject.get("channels")).add(
                    mapper.createObjectNode()
                            .put("id", channelId++)
                            .put("type", type.getValue())
                            .put("name", name)
                            .put("parent_id", parentId)
            );
        }
    }
}
