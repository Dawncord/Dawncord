package org.dimas4ek.wrapper.entities.guild;

import org.dimas4ek.wrapper.action.GuildOnboardingModifyAction;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.channel.GuildChannel;
import org.dimas4ek.wrapper.entities.role.GuildRole;
import org.dimas4ek.wrapper.types.OnboardingMode;
import org.dimas4ek.wrapper.types.PromptType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuildOnboarding {
    private static Guild guild = null;
    private final JSONObject onboarding;

    public GuildOnboarding(Guild guild, JSONObject onboarding) {
        GuildOnboarding.guild = guild;
        this.onboarding = onboarding;
    }

    public List<Prompt> getPrompts() {
        return JsonUtils.getEntityList(onboarding.getJSONArray("prompts"), Prompt::new);
    }

    public List<GuildChannel> getChannels() {
        return getEntities(onboarding, "default_channel_ids", GuildChannel.class);
    }

    public boolean isEnabled() {
        return onboarding.getBoolean("enabled");
    }

    public OnboardingMode getMode() {
        return EnumUtils.getEnumObject(onboarding, "mode", OnboardingMode.class);
    }

    public void modify(Consumer<GuildOnboardingModifyAction> handler) {
        ActionExecutor.execute(handler, GuildOnboardingModifyAction.class, guild.getId());
    }

    public static class Prompt {
        private final JSONObject prompt;

        public Prompt(JSONObject prompt) {
            this.prompt = prompt;
        }

        public String getId() {
            return prompt.getString("id");
        }

        public Long getIdLong() {
            return Long.parseLong(getId());
        }

        public String getTitle() {
            return prompt.getString("title");
        }

        public PromptType getType() {
            return EnumUtils.getEnumObject(prompt, "type", PromptType.class);
        }

        public List<Option> getOptions() {
            return JsonUtils.getEntityList(prompt.getJSONArray("options"), Option::new);
        }

        public boolean isSingleSelect() {
            return prompt.getBoolean("single_select");
        }

        public boolean isRequired() {
            return prompt.getBoolean("required");
        }

        public boolean inOnboarding() {
            return prompt.getBoolean("in_onboarding");
        }

        public static Prompt of(String id, String title, PromptType type, List<Option> options, boolean singleSelect, boolean required, boolean inOnboarding) {
            JSONObject jsonObject = new JSONObject()
                    .put("id", id)
                    .put("title", title)
                    .put("type", type.getValue())
                    .put("options", setOptions(options))
                    .put("single_select", singleSelect)
                    .put("required", required)
                    .put("in_onboarding", inOnboarding);
            return new Prompt(jsonObject);
        }

        private static JSONArray setOptions(List<Option> options) {
            JSONArray optionsArray = new JSONArray();
            for (Option option : options) {
                JSONObject optionJson = new JSONObject()
                        .put("id", option.getId())
                        .put("title", option.getTitle())
                        .put("description", option.getDescription())
                        .put("channel_ids", option.getChannels().stream().map(GuildChannel::getId).toList())
                        .put("role_ids", option.getRoles().stream().map(GuildRole::getId).toList())
                        .put("emoji_id", option.getEmoji().getId())
                        .put("emoji_name", option.getEmoji().getName())
                        .put("emoji_animated", option.getEmoji().isAnimated());
                optionsArray.put(optionJson);
            }
            return optionsArray;
        }

        public static class Option {
            private final JSONObject option;

            public Option(JSONObject option) {
                this.option = option;
            }

            public String getId() {
                return option.getString("id");
            }

            public Long getIdLong() {
                return Long.parseLong(getId());
            }

            public String getTitle() {
                return option.getString("title");
            }

            public String getDescription() {
                return option.optString("description", null);
            }

            public List<GuildChannel> getChannels() {
                return getEntities(option, "channel_ids", GuildChannel.class);
            }

            public List<GuildRole> getRoles() {
                return getEntities(option, "role_ids", GuildRole.class);
            }

            public Emoji getEmoji() {
                JSONObject emoji = option.optJSONObject("emoji");
                return emoji != null ? guild.getEmojiById(emoji.getString("id")) : null;
            }

            public static Option of(String id, String title, String description, List<GuildChannel> channels, List<GuildRole> roles, Emoji emoji) {
                JSONObject jsonObject = new JSONObject()
                        .put("id", id)
                        .put("title", title)
                        .put("description", description)
                        .put("channel_ids", channels.stream().map(GuildChannel::getId).toList())
                        .put("role_ids", roles.stream().map(GuildRole::getId).toList())
                        .put("emoji_id", emoji.getId())
                        .put("emoji_name", emoji.getName())
                        .put("emoji_animated", emoji.isAnimated());
                return new Option(jsonObject);
            }
        }
    }

    @NotNull
    private static <T> List<T> getEntities(JSONObject jsonObject, String key, Class<T> clazz) {
        JSONArray ids = jsonObject.getJSONArray(key);
        List<T> entities = new ArrayList<>();
        for (int i = 0; i < ids.length(); i++) {
            T entity = null;
            if (clazz.equals(GuildChannel.class)) {
                entity = (T) guild.getChannelById(ids.getString(i));
            } else if (clazz.equals(GuildRole.class)) {
                entity = (T) guild.getRoleById(ids.getString(i));
            }
            if (entity != null) {
                entities.add(entity);
            }
        }
        return entities;
    }
}
