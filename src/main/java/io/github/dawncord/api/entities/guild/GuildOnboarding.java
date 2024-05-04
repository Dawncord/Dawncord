package io.github.dawncord.api.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.action.GuildOnboardingModifyAction;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.role.GuildRoleImpl;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.OnboardingMode;
import io.github.dawncord.api.types.PromptType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents guild onboarding settings.
 */
public class GuildOnboarding {
    private final JsonNode onboarding;
    private static Guild guild;
    private List<Prompt> prompts;
    private List<GuildChannel> channels;
    private Boolean isEnabled;
    private OnboardingMode mode;

    /**
     * Constructs a new GuildOnboarding with the provided onboarding data and guild.
     *
     * @param onboarding The JSON data representing the guild onboarding settings.
     * @param guild      The guild to which the onboarding settings belong.
     */
    public GuildOnboarding(JsonNode onboarding, Guild guild) {
        this.onboarding = onboarding;
        GuildOnboarding.guild = guild;
    }

    /**
     * Retrieves the prompts for guild onboarding.
     *
     * @return The list of prompts for guild onboarding.
     */
    public List<Prompt> getPrompts() {
        if (prompts == null) {
            prompts = JsonUtils.getEntityList(onboarding.get("prompts"), prompt -> new Prompt(prompt, guild));
        }
        return prompts;
    }

    /**
     * Retrieves the channels for guild onboarding.
     *
     * @return The list of channels for guild onboarding.
     */
    public List<GuildChannel> getChannels() {
        if (channels == null) {
            channels = GuildOnboarding.getChannels(onboarding, "default_channel_ids");
        }
        return channels;
    }

    /**
     * Checks if guild onboarding is enabled.
     *
     * @return True if guild onboarding is enabled, false otherwise.
     */
    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = onboarding.get("enabled").asBoolean();
        }
        return isEnabled;
    }

    /**
     * Retrieves the onboarding mode for the guild.
     *
     * @return The onboarding mode for the guild.
     */
    public OnboardingMode getMode() {
        if (mode == null) {
            mode = EnumUtils.getEnumObject(onboarding, "mode", OnboardingMode.class);
        }
        return mode;
    }

    /**
     * Initiates a modification event for guild onboarding settings.
     *
     * @param handler The consumer function to handle the modification action.
     * @return The modification event for guild onboarding settings.
     */
    public ModifyEvent<GuildOnboarding> modify(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getOnboarding());
    }

    /**
     * Represents a prompt in guild onboarding.
     */
    public static class Prompt {
        private final JsonNode prompt;
        private final Guild guild;
        private String id;
        private String title;
        private PromptType type;
        private List<Option> options;
        private Boolean isSingleSelect;
        private Boolean isRequired;
        private Boolean inOnboarding;

        /**
         * Constructs a Prompt object.
         *
         * @param prompt The JSON node containing prompt information.
         * @param guild  The guild associated with the prompt.
         */
        public Prompt(JsonNode prompt, Guild guild) {
            this.prompt = prompt;
            this.guild = guild;
        }

        private Prompt(String id, String title, PromptType type, List<Option> options, boolean singleSelect, boolean required, boolean inOnboarding) {
            this.prompt = null;
            this.guild = null;
            this.id = id;
            this.title = title;
            this.type = type;
            this.options = options;
            this.isSingleSelect = singleSelect;
            this.isRequired = required;
            this.inOnboarding = inOnboarding;
        }

        /**
         * Retrieves the id of the prompt.
         *
         * @return The id of the prompt.
         */
        public String getId() {
            if (id == null) {
                assert prompt != null;
                return prompt.get("id").asText();
            }
            return id;
        }

        /**
         * Retrieves the id of the prompt.
         *
         * @return The id of the prompt.
         */
        public long getIdLong() {
            return Long.parseLong(getId());
        }

        /**
         * Retrieves the title of the prompt.
         *
         * @return The title of the prompt.
         */
        public String getTitle() {
            if (title == null) {
                assert prompt != null;
                return prompt.get("title").asText();
            }
            return title;
        }

        /**
         * Retrieves the type of the prompt.
         *
         * @return The type of the prompt.
         */
        public PromptType getType() {
            if (type == null) {
                assert prompt != null;
                return EnumUtils.getEnumObject(prompt, "type", PromptType.class);
            }
            return type;
        }

        /**
         * Retrieves the options associated with the prompt.
         *
         * @return The options associated with the prompt.
         */
        public List<Option> getOptions() {
            if (options == null) {
                assert prompt != null;
                return JsonUtils.getEntityList(prompt.get("options"), option -> new Option(option, guild));
            }
            return options;
        }

        /**
         * Checks if the prompt is single select.
         *
         * @return True if the prompt is single select, false otherwise.
         */
        public boolean isSingleSelect() {
            if (isSingleSelect == null) {
                assert prompt != null;
                isSingleSelect = prompt.get("single_select").asBoolean();
            }
            return isSingleSelect;
        }

        /**
         * Checks if the prompt is required.
         *
         * @return True if the prompt is required, false otherwise.
         */
        public boolean isRequired() {
            if (isRequired == null) {
                assert prompt != null;
                isRequired = prompt.get("required").asBoolean();
            }
            return isRequired;
        }

        /**
         * Checks if the prompt is part of onboarding.
         *
         * @return True if the prompt is part of onboarding, false otherwise.
         */
        public boolean inOnboarding() {
            if (inOnboarding == null) {
                assert prompt != null;
                inOnboarding = prompt.get("in_onboarding").asBoolean();
            }
            return inOnboarding;
        }

        /**
         * Creates a new Prompt object.
         *
         * @param id           The ID of the prompt.
         * @param title        The title of the prompt.
         * @param type         The type of the prompt.
         * @param options      The options associated with the prompt.
         * @param singleSelect True if the prompt is single select, false otherwise.
         * @param required     True if the prompt is required, false otherwise.
         * @param inOnboarding True if the prompt is part of onboarding, false otherwise.
         * @return A new Prompt object.
         */
        public static Prompt of(String id, String title, PromptType type, List<Option> options, boolean singleSelect, boolean required, boolean inOnboarding) {
            return new Prompt(id, title, type, options, singleSelect, required, inOnboarding);
        }

        /**
         * Represents an option associated with a prompt in guild onboarding.
         */
        public static class Option {
            private final JsonNode option;
            private final Guild guild;
            private String id;
            private String title;
            private String description;
            private List<GuildChannel> channels;
            private List<GuildRoleImpl> roles;
            private CustomEmoji emoji;

            /**
             * Constructs an Option object.
             *
             * @param option The JSON node containing option information.
             * @param guild  The guild associated with the option.
             */
            public Option(JsonNode option, Guild guild) {
                this.option = option;
                this.guild = guild;
            }

            private Option(String id, String title, String description, List<GuildChannel> channels, List<GuildRoleImpl> roles, CustomEmoji emoji) {
                this.option = null;
                this.guild = null;
                this.id = id;
                this.title = title;
                this.description = description;
                this.channels = channels;
                this.roles = roles;
                this.emoji = emoji;
            }

            /**
             * Retrieves the id of the option.
             *
             * @return The id of the option.
             */
            public String getId() {
                if (id == null) {
                    assert option != null;
                    return option.get("id").asText();
                }
                return id;
            }

            /**
             * Retrieves the id of the option.
             *
             * @return The id of the option.
             */
            public long getIdLong() {
                return Long.parseLong(getId());
            }

            /**
             * Retrieves the title of the option.
             *
             * @return The title of the option.
             */
            public String getTitle() {
                if (title == null) {
                    assert option != null;
                    return option.get("title").asText();
                }
                return title;
            }

            /**
             * Retrieves the description of the option.
             *
             * @return The description of the option.
             */
            public String getDescription() {
                if (description == null) {
                    assert option != null;
                    return option.get("description").asText();
                }
                return description;
            }

            /**
             * Retrieves the channels associated with the option.
             *
             * @return The channels associated with the option.
             */
            public List<GuildChannel> getChannels() {
                if (channels == null) {
                    assert option != null;
                    channels = GuildOnboarding.getChannels(option, "channel_ids");
                }
                return channels;
            }

            /**
             * Retrieves the roles associated with the option.
             *
             * @return The roles associated with the option.
             */
            public List<GuildRoleImpl> getRoles() {
                if (roles == null) {
                    assert option != null;
                    roles = GuildOnboarding.getRoles(option);
                }
                return roles;
            }

            /**
             * Retrieves the emoji associated with the option.
             *
             * @return The emoji associated with the option.
             */
            public CustomEmoji getEmoji() {
                if (emoji == null) {
                    assert option != null;
                    if (option.has("emoji")) {
                        assert guild != null;
                        emoji = guild.getEmojiById(option.get("emoji").get("id").asText());
                    } else {
                        emoji = null;
                    }
                }
                return emoji;
            }

            /**
             * Creates a new Option object.
             *
             * @param id          The ID of the option.
             * @param title       The title of the option.
             * @param description The description of the option.
             * @param channels    The channels associated with the option.
             * @param roles       The roles associated with the option.
             * @param emoji       The emoji associated with the option.
             * @return A new Option object.
             */
            public static Option of(String id, String title, String description, List<GuildChannel> channels, List<GuildRoleImpl> roles, CustomEmoji emoji) {
                return new Option(id, title, description, channels, roles, emoji);
            }
        }
    }

    private static List<GuildChannel> getChannels(JsonNode jsonObject, String key) {
        List<GuildChannel> channels = new ArrayList<>();
        for (JsonNode id : jsonObject.get(key)) {
            GuildChannel channel = guild.getChannelById(id.asText());
            if (channel != null) {
                channels.add(channel);
            }
        }
        return channels;
    }

    private static List<GuildRoleImpl> getRoles(JsonNode jsonObject) {
        List<GuildRoleImpl> roles = new ArrayList<>();
        for (JsonNode id : jsonObject.get("role_ids")) {
            roles.add(guild.getRoleById(id.asText()));
        }
        return roles;
    }
}
