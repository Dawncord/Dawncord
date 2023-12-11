package org.dimas4ek.wrapper.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.CommandType;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.OptionType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SlashCommand implements Command {
    private final JsonNode command;
    private String name;
    private String description;
    private String applicationId;
    private List<PermissionType> memberPermissions;
    private CommandType type;
    private Boolean nsfw;
    private String version;
    private List<Option> options = new ArrayList<>();
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    public SlashCommand(JsonNode command) {
        this.command = command;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = command.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = command.get("description").asText();
        }
        return description;
    }

    @Override
    public String getApplicationId() {
        if (applicationId == null) {
            applicationId = command.get("application_id").asText();
        }
        return applicationId;
    }

    @Override
    public long getApplicationIdLong() {
        return Long.parseLong(getApplicationId());
    }

    @Override
    public List<PermissionType> getMemberPermissions() {
        if (memberPermissions == null) {
            memberPermissions = EnumUtils.getEnumListFromLong(command, "default_member_permissions", PermissionType.class);
        }
        return memberPermissions;
    }

    @Override
    public CommandType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(command, "type", CommandType.class);
        }
        return type;
    }

    @Override
    public boolean isNsfw() {
        if (nsfw == null) {
            nsfw = command.get("nsfw").asBoolean();
        }
        return nsfw;
    }

    @Override
    public String getVersion() {
        if (version == null) {
            version = command.get("version").asText();
        }
        return version;
    }

    @Override
    public List<Option> getOptions() {
        if (options == null) {
            options = new ArrayList<>();
            if (command.has("options") && command.hasNonNull("options")) {
                for (JsonNode option : command.get("options")) {
                    Option optionData = new Option(
                            EnumUtils.getEnumObjectFromInt(option.get("type").asInt(), OptionType.class),
                            option.get("name").asText(),
                            option.get("description").asText()
                    );
                    if (option.has("required")) optionData.setRequired(option.get("required").asBoolean());
                    if (option.has("autocomplete")) optionData.setRequired(option.get("autocomplete").asBoolean());
                    if (option.has("choices")) {
                        for (JsonNode choice : option.get("choices")) {
                            Option.Choice choiceData = new Option.Choice(
                                    choice.get("name").asText(),
                                    choice.get("value").asText()
                            );
                            optionData.addChoice(choiceData);
                        }
                    }
                    options.add(optionData);
                }
            }
        }
        return options;
    }

    @Override
    public Map<Locale, String> getNameLocalizations() {
        if (nameLocalizations == null) {
            nameLocalizations = getLocaleStringMap(command, "name_localizations");
        }
        return nameLocalizations;
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        if (descriptionLocalizations == null) {
            descriptionLocalizations = getLocaleStringMap(command, "description_localizations");
        }
        return descriptionLocalizations;
    }

    @NotNull
    private Map<Locale, String> getLocaleStringMap(JsonNode command, String localizations) {
        if (command.has(localizations) && command.hasNonNull(localizations)) {
            Map<Locale, String> map = new EnumMap<>(Locale.class);
            for (JsonNode nameLocalizations : command.get(localizations)) {
                String key = nameLocalizations.fieldNames().next();
                String value = nameLocalizations.get(key).asText();
                map.put(Locale.valueOf(key), value);
            }
            return map;
        }
        return Collections.emptyMap();
    }
}
