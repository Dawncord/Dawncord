package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.command.SubCommand;
import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.OptionType;
import org.dimas4ek.wrapper.utils.SlashCommandUtils;

import java.util.*;

public class SubCommandCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);
    private final List<SubCommand> subCommandList;

    public SubCommandCreateAction(String name, String description, List<SubCommand> subCommandList) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
        this.subCommandList = subCommandList;
    }

    public SubCommandCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    public SubCommandCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public SubCommandCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    public SubCommandCreateAction addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    public SubCommandCreateAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    public SubCommandCreateAction addOption(Option option) {
        optionList.add(option);
        return this;
    }

    public SubCommandCreateAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    public SubCommandCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    public SubCommandCreateAction addNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    public SubCommandCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    public SubCommandCreateAction addDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    private void submit() {
        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        subCommandList.add(new SubCommand(jsonObject));
        //jsonObject.removeAll();
    }
}
