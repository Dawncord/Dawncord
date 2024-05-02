package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.List;
import java.util.Map;

/**
 * Represents a sub-command within a command.
 * SubCommand instances define additional commands within a parent command.
 *
 * @see Command
 */
public class SubCommand implements Command {
    private final JsonNode subCommand;
    private String name;
    private String description;
    private List<Option> options;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    /**
     * Constructs a new SubCommand instance.
     *
     * @param subCommand The JSON node representing the sub-command.
     */
    public SubCommand(JsonNode subCommand) {
        this.subCommand = subCommand;
    }

    /**
     * Retrieves the type of the sub-command.
     *
     * @return The type of the sub-command.
     */
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

    /**
     * Retrieves the options associated with the sub-command.
     *
     * @return The options associated with the sub-command.
     */
    public List<Option> getOptions() {
        if (options == null) {
            options = SlashCommandUtils.createOptions(subCommand);
        }
        return options;
    }
}
