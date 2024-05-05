package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;

/**
 * Represents an action for creating a subcommand.
 *
 * @see SubCommand
 */
public class SubCommandCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);
    private final List<SubCommand> subCommandList;

    /**
     * Create a new {@link SubCommandCreateAction}
     *
     * @param name           The name of the subcommand.
     * @param description    The description of the subcommand.
     * @param subCommandList The list of subcommands.
     */
    public SubCommandCreateAction(String name, String description, List<SubCommand> subCommandList) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
        this.subCommandList = subCommandList;
    }

    private SubCommandCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    /**
     * Sets the name for the subcommand.
     *
     * @param name the name to set
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the subcommand.
     *
     * @param description the description to set
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Adds a new option to the list of options for the subcommand.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    /**
     * Adds a new option to the list of options for the subcommand.
     *
     * @param type        the type of the option
     * @param name        the name of the option
     * @param description the description of the option
     * @param isRequired  whether the option is required or not
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    /**
     * Adds a new option to the list of options for the subcommand.
     *
     * @param type           the type of the option
     * @param name           the name of the option
     * @param description    the description of the option
     * @param isRequired     whether the option is required or not
     * @param isAutocomplete whether the option is an autocomplete option or not
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    /**
     * Adds an option to the list of options for the subcommand.
     *
     * @param option the option to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOption(Option option) {
        optionList.add(option);
        return this;
    }

    /**
     * Adds the given options to the list of options for the subcommand.
     *
     * @param options the options to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    /**
     * Adds the given options to the list of options for the subcommand.
     *
     * @param options the options to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addOptions(List<Option> options) {
        optionList.addAll(options);
        return this;
    }

    /**
     * Adds a localized name for the subcommand.
     *
     * @param locale the locale for the localized name
     * @param name   the localized name to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    /**
     * Sets the localized names for different locales for the subcommand.
     *
     * @param nameLocalizations a map of locale to localized names
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    /**
     * Adds a localized description for the subcommand.
     *
     * @param locale      the locale of the description
     * @param description the description to add
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    /**
     * Sets the localized descriptions for different locales for the subcommand.
     *
     * @param descriptionLocalizations a map of locale to localized descriptions
     * @return the modified SubCommandCreateAction object
     */
    public SubCommandCreateAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    private void submit() {
        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        subCommandList.add(new SubCommand(jsonObject));
    }
}
