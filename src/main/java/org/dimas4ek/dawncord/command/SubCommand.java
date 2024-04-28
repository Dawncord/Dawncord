package org.dimas4ek.dawncord.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.List;
import java.util.Map;

public class SubCommand implements Command {
    private final JsonNode subCommand;
    private String name;
    private String description;
    private List<Option> options;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    public SubCommand(JsonNode subCommand) {
        this.subCommand = subCommand;
    }

    public OptionType getType() {
        return OptionType.SUB_COMMAND;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = subCommand.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = subCommand.get("description").asText();
        }
        return description;
    }

    @Override
    public Map<Locale, String> getNameLocalizations() {
        if (nameLocalizations == null) {
            nameLocalizations = SlashCommandUtils.getLocaleStringMap(subCommand, "name_localizations");
        }
        return nameLocalizations;
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        if (descriptionLocalizations == null) {
            descriptionLocalizations = SlashCommandUtils.getLocaleStringMap(subCommand, "description_localizations");
        }
        return descriptionLocalizations;
    }

    public List<Option> getOptions() {
        if (options == null) {
            options = SlashCommandUtils.createOptions(subCommand);
        }
        return options;
    }
}
