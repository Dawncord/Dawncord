package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a sub-command group.
 * SubCommandGroup encapsulates the details of a group of sub-commands within a slash command.
 *
 * @see SubCommand
 */
public class SubCommandGroup implements Command {
    private final JsonNode subCommandGroup;
    private String name;
    private String description;
    private List<SubCommand> subCommandList;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    /**
     * Constructs a new SubCommandGroup instance with the provided JSON node.
     *
     * @param subCommandGroup The JSON node representing the sub-command group.
     */
    public SubCommandGroup(JsonNode subCommandGroup) {
        this.subCommandGroup = subCommandGroup;
    }

    /**
     * Retrieves the type of the sub-command group.
     *
     * @return The type of the sub-command group.
     */
    public OptionType getType() {
        return OptionType.SUB_COMMAND_GROUP;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = subCommandGroup.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = subCommandGroup.get("description").asText();
        }
        return description;
    }

    @Override
    public Map<Locale, String> getNameLocalizations() {
        if (nameLocalizations == null) {
            nameLocalizations = SlashCommandUtils.getLocaleStringMap(subCommandGroup, "name_localizations");
        }
        return nameLocalizations;
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        if (descriptionLocalizations == null) {
            descriptionLocalizations = SlashCommandUtils.getLocaleStringMap(subCommandGroup, "description_localizations");
        }
        return descriptionLocalizations;
    }

    /**
     * Retrieves the list of sub-commands within the sub-command group.
     *
     * @return The list of sub-commands.
     */
    public List<SubCommand> getSubCommands() {
        if (subCommandList == null) {
            subCommandList = new ArrayList<>();
            if (subCommandGroup.has("options") && subCommandGroup.hasNonNull("options")) {
                for (JsonNode subCommand : subCommandGroup.get("options")) {
                    subCommandList.add(new SubCommand(subCommand));
                }
            }
        }
        return subCommandList;
    }
}
