package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildCategory;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.*;
import io.github.dawncord.api.utils.IOUtils;

import java.lang.reflect.Method;
import java.util.function.Consumer;

//todo add create guild

/**
 * Represents an action for creating a guild.
 *
 * @see Guild
 */
public class GuildCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private int roleId = 1;
    private int channelId = 1;
    private boolean hasChanges = false;

    /**
     * Create a new {@link GuildCreateAction}
     */
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

    /**
     * Sets the name property for the guild.
     *
     * @param name the name to be set
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the icon for the guild.
     *
     * @param path the path of the icon image file
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setIcon(String path) {
        if (!path.substring(path.lastIndexOf(".") + 1).equals("gif")) {
            setProperty("icon", IOUtils.setImageData(path));
        }
        return this;
    }

    /**
     * Sets the verification level for the guild.
     *
     * @param level the verification level to be set
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setVerificationLevel(VerificationLevel level) {
        return setProperty("verification_level", level.getValue());
    }

    /**
     * Sets the notification level for the guild.
     *
     * @param level the notification level to be set
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setNotificationLevel(NotificationLevel level) {
        return setProperty("default_message_notifications", level.getValue());
    }

    /**
     * Sets the content filter level for the guild.
     *
     * @param level the content filter level to be set
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setContentFilterLevel(ContentFilterLevel level) {
        return setProperty("explicit_content_filter", level.getValue());
    }

    /**
     * Creates a role using the given handler and adds it to the list of roles in the guild.
     *
     * @param handler a Consumer that takes a GuildRoleCreateAction object and modifies it
     * @return the modified GuildCreateAction object
     */
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

    /**
     * Creates a channel of the specified type and name in the guild.
     *
     * @param type the type of the channel to create
     * @param name the name of the channel to create
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction createChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return this;
    }

    /**
     * Creates a category with the specified name and executes the given handler on it.
     *
     * @param name    the name of the category to create
     * @param handler a Consumer that takes a CategoryCreateAction object and modifies it
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction createCategory(String name, Consumer<CategoryCreateAction> handler) {
        createGuildChannel(ChannelType.GUILD_CATEGORY, name);
        handler.accept(new CategoryCreateAction(channelId - 1));
        return this;
    }

    /**
     * Creates an AFK channel of the specified type and name in the guild.
     *
     * @param type the type of the channel to create
     * @param name the name of the channel to create
     * @return the modified GuildCreateAction object with the AFK channel ID set
     */
    public GuildCreateAction createAfkChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return setProperty("afk_channel_id", channelId - 1);
    }

    /**
     * Sets the AFK timeout for the guild.
     *
     * @param seconds the AFK timeout in seconds
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction setAfkTimeout(int seconds) {
        return setProperty("afk_timeout", seconds);
    }

    /**
     * Creates a system channel of the specified type and name in the guild.
     *
     * @param type the type of the channel to create
     * @param name the name of the channel to create
     * @return the modified GuildCreateAction object
     */
    public GuildCreateAction createSystemChannel(ChannelType type, String name) {
        createGuildChannel(type, name);
        return setProperty("system_channel_id", channelId - 1);
    }

    /**
     * Sets the system channel flags for the guild.
     *
     * @param flags the system channel flags to set
     * @return the modified GuildCreateAction object
     */
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

    /**
     * Represents an action for creating a guild category.
     *
     * @see GuildCategory
     */
    public class CategoryCreateAction {
        private final int parentId;

        /**
         * Create a new {@link CategoryCreateAction}
         *
         * @param parentId The ID of the parent category or guild where the category will be created.
         */
        public CategoryCreateAction(int parentId) {
            this.parentId = parentId;
        }

        private CategoryCreateAction setProperty(String name, Object value) {
            jsonObject.set(name, mapper.valueToTree(value));
            hasChanges = true;
            return this;
        }

        /**
         * Adds a new channel to the category.
         *
         * @param type The type of the channel to be created.
         * @param name The name of the channel.
         * @return The CategoryCreateAction instance to allow method chaining.
         */
        public CategoryCreateAction addChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return this;
        }

        /**
         * Adds an AFK channel to the category.
         *
         * @param type The type of the AFK channel to be created.
         * @param name The name of the AFK channel.
         * @return The CategoryCreateAction instance to allow method chaining.
         */
        public CategoryCreateAction addAfkChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return setProperty("afk_channel_id", channelId - 1); // Assuming channelId is defined elsewhere
        }

        /**
         * Adds a system channel to the category.
         *
         * @param type The type of the system channel to be created.
         * @param name The name of the system channel.
         * @return The CategoryCreateAction instance to allow method chaining.
         */
        public CategoryCreateAction addSystemChannel(ChannelType type, String name) {
            createGuildChannelUnderCategory(type, name);
            return setProperty("system_channel_id", channelId - 1); // Assuming channelId is defined elsewhere
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
