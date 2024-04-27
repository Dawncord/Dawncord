package org.dimas4ek.dawncord.entities.guild;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.action.GuildOnboardingModifyAction;
import org.dimas4ek.dawncord.entities.CustomEmoji;
import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.channel.GuildChannel;
import org.dimas4ek.dawncord.entities.guild.role.GuildRoleImpl;
import org.dimas4ek.dawncord.event.ModifyEvent;
import org.dimas4ek.dawncord.types.OnboardingMode;
import org.dimas4ek.dawncord.types.PromptType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.EnumUtils;
import org.dimas4ek.dawncord.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuildOnboarding {
    private final JsonNode onboarding;
    private static Guild guild;
    private List<Prompt> prompts;
    private List<GuildChannel> channels;
    private Boolean isEnabled;
    private OnboardingMode mode;

    public GuildOnboarding(JsonNode onboarding, Guild guild) {
        this.onboarding = onboarding;
        GuildOnboarding.guild = guild;
    }

    public List<Prompt> getPrompts() {
        if (prompts == null) {
            prompts = JsonUtils.getEntityList(onboarding.get("prompts"), prompt -> new Prompt(prompt, guild));
        }
        return prompts;
    }

    public List<GuildChannel> getChannels() {
        if (channels == null) {
            channels = GuildOnboarding.getChannels(onboarding, "default_channel_ids");
        }
        return channels;
    }

    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = onboarding.get("enabled").asBoolean();
        }
        return isEnabled;
    }

    public OnboardingMode getMode() {
        if (mode == null) {
            mode = EnumUtils.getEnumObject(onboarding, "mode", OnboardingMode.class);
        }
        return mode;
    }

    public ModifyEvent<GuildOnboarding> modify(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, guild.getId());
        return new ModifyEvent<>(guild.getOnboarding());
    }

    public static class Prompt implements ISnowflake {
        private final JsonNode prompt;
        private final Guild guild;
        private String id;
        private String title;
        private PromptType type;
        private List<Option> options;
        private Boolean isSingleSelect;
        private Boolean isRequired;
        private Boolean inOnboarding;

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

        @Override
        public String getId() {
            if (id == null) {
                assert prompt != null;
                return prompt.get("id").asText();
            }
            return id;
        }

        @Override
        public long getIdLong() {
            return Long.parseLong(getId());
        }

        public String getTitle() {
            if (title == null) {
                assert prompt != null;
                return prompt.get("title").asText();
            }
            return title;
        }

        public PromptType getType() {
            if (type == null) {
                assert prompt != null;
                return EnumUtils.getEnumObject(prompt, "type", PromptType.class);
            }
            return type;
        }

        public List<Option> getOptions() {
            if (options == null) {
                assert prompt != null;
                return JsonUtils.getEntityList(prompt.get("options"), option -> new Option(option, guild));
            }
            return options;
        }

        public boolean isSingleSelect() {
            if (isSingleSelect == null) {
                assert prompt != null;
                isSingleSelect = prompt.get("single_select").asBoolean();
            }
            return isSingleSelect;
        }

        public boolean isRequired() {
            if (isRequired == null) {
                assert prompt != null;
                isRequired = prompt.get("required").asBoolean();
            }
            return isRequired;
        }

        public boolean inOnboarding() {
            if (inOnboarding == null) {
                assert prompt != null;
                inOnboarding = prompt.get("in_onboarding").asBoolean();
            }
            return inOnboarding;
        }

        public static Prompt of(String id, String title, PromptType type, List<Option> options, boolean singleSelect, boolean required, boolean inOnboarding) {
            return new Prompt(id, title, type, options, singleSelect, required, inOnboarding);
        }

        public static class Option implements ISnowflake {
            private final JsonNode option;
            private final Guild guild;
            private String id;
            private String title;
            private String description;
            private List<GuildChannel> channels;
            private List<GuildRoleImpl> roles;
            private CustomEmoji emoji;

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

            @Override
            public String getId() {
                if (id == null) {
                    assert option != null;
                    return option.get("id").asText();
                }
                return id;
            }

            @Override
            public long getIdLong() {
                return Long.parseLong(getId());
            }

            public String getTitle() {
                if (title == null) {
                    assert option != null;
                    return option.get("title").asText();
                }
                return title;
            }

            public String getDescription() {
                if (description == null) {
                    assert option != null;
                    return option.get("description").asText();
                }
                return description;
            }

            public List<GuildChannel> getChannels() {
                if (channels == null) {
                    assert option != null;
                    channels = GuildOnboarding.getChannels(option, "channel_ids");
                }
                return channels;
            }

            public List<GuildRoleImpl> getRoles() {
                if (roles == null) {
                    assert option != null;
                    roles = GuildOnboarding.getRoles(option);
                }
                return roles;
            }

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
