package org.dimas4ek.dawncord.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubCommandGroup implements Command {
    private final JsonNode subCommandGroup;
    private String name;
    private String description;
    private List<SubCommand> subCommandList;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    public SubCommandGroup(JsonNode subCommandGroup) {
        this.subCommandGroup = subCommandGroup;
    }

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
