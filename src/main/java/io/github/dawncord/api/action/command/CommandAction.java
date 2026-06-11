package io.github.dawncord.api.action.command;

import io.github.dawncord.api.action.Action;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Provides common settings for application command create and modify actions.
 */
public abstract class CommandAction extends Action<CommandAction> {
    protected final List<Option> optionList = new ArrayList<>();
    protected final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    protected final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);
    protected List<SubCommand> subCommandList = new ArrayList<>();

    //todo
    // rewrite classes
    // - sub command groups will be in SlashCommandAction
    // - SubCommandCreateAction will extends SubCommandable
    // - SlashCommandAction will extend Optionable 
    // - Optionable will extend SubCommandable
    // - command creation will be like
    // - command.addSubCommand()[in SlashCommandAction].newSubCommand()[in SubCommandCreateAction]...

    protected CommandAction(String name, String description) {
        super();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
    }

    public CommandAction() {
        super();
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public List<SubCommand> getSubCommandList() {
        return subCommandList;
    }

    /**
     * Sets the name for the slash command.
     *
     * @param name the name to set
     * @return the modified CommandAction object
     */
    public CommandAction setName(String name) {
        return setProperty("name", name);
    }

    /**
     * Sets the description for the slash command.
     *
     * @param description the description to set
     * @return the modified CommandAction object
     */
    public CommandAction setDescription(String description) {
        return setProperty("description", description);
    }

    /**
     * Adds a localized name for the slash command.
     *
     * @param locale the locale for the localized name
     * @param name   the localized name to add
     * @return the modified CommandAction object
     */
    public CommandAction setLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        hasChanges = true;
        return this;
    }

    /**
     * Sets the localized names for different locales for the slash command.
     *
     * @param nameLocalizations a map of locale to localized names
     * @return the modified CommandAction object
     */
    public CommandAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        hasChanges = true;
        return this;
    }

    /**
     * Adds a localized description for the slash command.
     *
     * @param locale      the locale of the description
     * @param description the description to add
     * @return the modified CommandAction object
     */
    public CommandAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        hasChanges = true;
        return this;
    }

    /**
     * Sets the localized descriptions for different locales for the slash command.
     *
     * @param descriptionLocalizations a map of locale to localized descriptions
     * @return the modified CommandAction object
     */
    public CommandAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        hasChanges = true;
        return this;
    }
}
