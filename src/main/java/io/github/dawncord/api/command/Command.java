package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.Map;

/**
 * Represents a base Discord application command.
 */
public abstract class Command implements ICommand {
    private final JsonNode command;
    private String name;
    private String description;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    public Command(JsonNode command) {
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
    public Map<Locale, String> getNameLocalizations() {
        if (nameLocalizations == null) {
            nameLocalizations = SlashCommandUtils.getLocaleStringMap(command, "name_localizations");
        }
        return nameLocalizations;
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        if (descriptionLocalizations == null) {
            descriptionLocalizations = SlashCommandUtils.getLocaleStringMap(command, "description_localizations");
        }
        return descriptionLocalizations;
    }
}
