package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents an action for creating a subcommand group.
 *
 * @see SubCommandGroup
 */
public class SubCommandGroupCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final List<SubCommandGroup> subCommandGroupList;

    /**
     * Create a new {@link SubCommandGroupCreateAction}
     *
     * @param name                The name of the subcommand group.
     * @param description         The description of the subcommand group.
     * @param subCommandGroupList The list of subcommand groups.
     */
    public SubCommandGroupCreateAction(String name, String description, List<SubCommandGroup> subCommandGroupList) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
        this.subCommandGroupList = subCommandGroupList;
    }

    private SubCommandGroupCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    /**
     * Sets the name for the subcommand group.
     *
     * @param name the name to set
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the subcommand group.
     *
     * @param description the description to set
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Adds a localized name for the subcommand group.
     *
     * @param locale the locale for the localized name
     * @param name   the localized name to add
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    /**
     * Sets the localized names for different locales for the subcommand group.
     *
     * @param nameLocalizations a map of locale to localized names
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    /**
     * Adds a localized description for the subcommand group.
     *
     * @param locale      the locale of the description
     * @param description the description to add
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    /**
     * Sets the localized descriptions for different locales for the subcommand group.
     *
     * @param descriptionLocalizations a map of locale to localized descriptions
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    /**
     * Adds a sub command for the subcommand group.
     *
     * @param subCommand the sub command to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    /**
     * Adds multiple sub commands for the subcommand group.
     *
     * @param subCommands the sub commands to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    /**
     * Adds a list of subcommands for the subcommand group.
     *
     * @param subCommands the list of subcommands to be added
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    /**
     * Adds a new subcommand for the subcommand group.
     *
     * @param name        the name of the subcommand
     * @param description the description of the subcommand
     * @param handler     the handler function for the subcommand
     * @return the modified SubCommandGroupCreateAction object
     */
    public SubCommandGroupCreateAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    private void submit() {
        SlashCommandUtils.createLocalizedLists(jsonObject, localizedNameList, localizedDescriptionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
        }
        subCommandGroupList.add(new SubCommandGroup(jsonObject));
        //jsonObject.removeAll();
    }
}
