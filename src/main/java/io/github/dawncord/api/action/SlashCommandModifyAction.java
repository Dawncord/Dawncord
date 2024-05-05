package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommand;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;

/**
 * Represents an action for updating a slash command.
 *
 * @see SlashCommand
 */
public class SlashCommandModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String commandId;
    private boolean hasChanges = false;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    /**
     * Create a new {@link SlashCommandModifyAction}
     *
     * @param commandId The ID of the slash command to modify.
     */
    public SlashCommandModifyAction(String commandId) {
        this.jsonObject = mapper.createObjectNode();
        this.commandId = commandId;
    }

    private SlashCommandModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    /**
     * Sets the name for the slash command.
     *
     * @param name the name to set
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the slash command.
     *
     * @param description the description to set
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOption(OptionType type, String name, String description) {
        addOption(type, name, description, false, false);
        return this;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @param isRequired  whether the option is required or not
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOption(OptionType type, String name, String description, boolean isRequired) {
        addOption(type, name, description, isRequired, false);
        return this;
    }

    /**
     * Adds a new option to the list of options for the slash command.
     *
     * @param type           the type of the option
     * @param name           the name of the option
     * @param description    the description of the option
     * @param isRequired     whether the option is required or not
     * @param isAutocomplete whether the option is an autocomplete option or not
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        hasChanges = true;
        return this;
    }

    /**
     * Adds an option to the list of options for the slash command.
     *
     * @param option the option to add
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOption(Option option) {
        optionList.add(option);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        hasChanges = true;
        return this;
    }

    /**
     * Adds the given options to the list of options for the slash command.
     *
     * @param options the options to add
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addOptions(List<Option> options) {
        optionList.addAll(options);
        hasChanges = true;
        return this;
    }

    /**
     * Adds a localized name for the slash command.
     *
     * @param locale the locale for the localized name
     * @param name   the localized name to add
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        hasChanges = true;
        return this;
    }

    /**
     * Sets the localized names for different locales for the slash command.
     *
     * @param nameLocalizations a map of locale to localized names
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        hasChanges = true;
        return this;
    }

    /**
     * Adds a localized description for the slash command.
     *
     * @param locale      the locale of the description
     * @param description the description to add
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        hasChanges = true;
        return this;
    }

    /**
     * Sets the localized descriptions for different locales for the slash command.
     *
     * @param descriptionLocalizations a map of locale to localized descriptions
     * @return the modified SlashCommandModifyAction object
     */
    public SlashCommandModifyAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        hasChanges = true;
        return this;
    }

    private void submit() {
        if (hasChanges) {
            if (!localizedNameList.isEmpty()) {
                jsonObject.set("name_localizations", mapper.valueToTree(localizedNameList));
            }
            if (!localizedDescriptionList.isEmpty()) {
                jsonObject.set("description_localizations", mapper.valueToTree(localizedDescriptionList));
            }
            if (!optionList.isEmpty()) {
                ArrayNode optionsArray = SlashCommandUtils.createOptionsArray(optionList);
                if (jsonObject.has("options")) {
                    ((ArrayNode) jsonObject.get("options")).addAll(optionsArray);
                } else {
                    jsonObject.set("options", optionsArray);
                }
            }

            ApiClient.patch(jsonObject, Routes.SlashCommand.Get(commandId));
            hasChanges = false;
        }
    }
}
